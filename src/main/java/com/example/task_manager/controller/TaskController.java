package com.example.task_manager.controller;

import com.example.task_manager.model.Note;
import com.example.task_manager.model.Task;
import com.example.task_manager.service.TaskService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class TaskController {

    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    // ---------------- UI (Thymeleaf) ----------------

    // Home page with create form and tasks list
    @GetMapping({"/", "/index"})
    public String index(@RequestParam(required = false) Boolean completed, Model model) {
        model.addAttribute("tasks", service.findAll(completed));
        model.addAttribute("newTask", new Task());
        return "index";
    }

    // Form submit (index form) - matches index.html action="/tasks"
    @PostMapping("/tasks")
    public String createFromForm(@RequestParam String title,
                                 @RequestParam(required = false) String description,
                                 @RequestParam(required = false) String deadline,
                                 @RequestParam(required = false) List<String> noteTitle,
                                 @RequestParam(required = false) List<String> noteBody,
                                 RedirectAttributes redirectAttributes) {
        Task t = new Task();
        t.setTitle(title);
        t.setDescription(description);
        t.setDeadline(deadline);

        List<Note> notes = new ArrayList<>();
        if (noteTitle != null && noteBody != null) {
            for (int i = 0; i < Math.min(noteTitle.size(), noteBody.size()); i++) {
                Note n = new Note(null, noteTitle.get(i), noteBody.get(i));
                notes.add(n);
            }
        }
        t.setNotes(notes);
        service.create(t);

        redirectAttributes.addFlashAttribute("msg", "Task created");
        return "redirect:/";
    }

    // View task page (matches index.html anchor "/tasks/{id}")
    // Note: Produces HTML so it doesn't conflict with the JSON API (/api/tasks/{id})
    @GetMapping(value = "/tasks/{id}", produces = MediaType.TEXT_HTML_VALUE)
    public String viewTask(@PathVariable int id, Model model) {
        Task t = service.findById(id);
        if (t == null) {
            return "redirect:/";
        }
        model.addAttribute("task", t);
        model.addAttribute("note", new Note());
        return "view";
    }

    // Add note from view page (form action in view.html posts to /tasks/{id}/notes)
    @PostMapping("/tasks/{id}/notes")
    public String addNoteFromForm(@PathVariable int id,
                                  @RequestParam String title,
                                  @RequestParam String body) {
        service.addNote(id, new Note(null, title, body));
        return "redirect:/tasks/" + id;
    }

    // Delete task (matches index.html form action "/tasks/delete/{id}")
    @PostMapping("/tasks/delete/{id}")
    public String deleteTaskFromForm(@PathVariable int id) {
        service.deleteTask(id);
        return "redirect:/";
    }

    // Delete note from view page (view.html posts to /tasks/{taskId}/notes/delete/{noteId})
    @PostMapping("/tasks/{taskId}/notes/delete/{noteId}")
    public String deleteNoteFromForm(@PathVariable int taskId, @PathVariable int noteId) {
        service.deleteNote(taskId, noteId);
        return "redirect:/tasks/" + taskId;
    }

    // Toggle complete button (matches index.html form action "/tasks/{id}/complete")
    @PostMapping("/tasks/{id}/complete")
    public String toggleComplete(@PathVariable int id) {
        Task t = service.findById(id);
        if (t != null) {
            t.setCompleted(!Boolean.TRUE.equals(t.getCompleted()));
        }
        return "redirect:/";
    }

    // ---------------- REST API (JSON) under /api/tasks ----------------
    // Keeping API separate avoids mapping conflicts with UI forms.

    @PostMapping(path = "/api/tasks", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Task> createJson(@RequestBody Task task) {
        Task created = service.create(task);
        return ResponseEntity.ok(created);
    }

    @GetMapping(path = "/api/tasks", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<Task>> allJson(@RequestParam(required = false) Boolean completed) {
        return ResponseEntity.ok(service.findAll(completed));
    }

    @GetMapping(path = "/api/tasks/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Task> getJson(@PathVariable int id) {
        Task t = service.findById(id);
        return t == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(t);
    }

    @PatchMapping(path = "/api/tasks/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Task> patchJson(@PathVariable int id, @RequestBody Map<String, Object> updates) {
        Task updated = service.updatePartial(id, updates);
        return updated == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(updated);
    }

    @DeleteMapping(path = "/api/tasks/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteJson(@PathVariable int id) {
        boolean ok = service.deleteTask(id);
        return ok ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping(path = "/api/tasks/{id}/notes", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<Note>> getNotesJson(@PathVariable int id) {
        Task t = service.findById(id);
        return t == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(t.getNotes());
    }

    @PostMapping(path = "/api/tasks/{id}/notes", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Note> addNoteJson(@PathVariable int id, @RequestBody Note note) {
        Note created = service.addNote(id, note);
        return created == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(created);
    }

    @DeleteMapping(path = "/api/tasks/{taskId}/notes/{noteId}")
    @ResponseBody
    public ResponseEntity<Void> deleteNoteJson(@PathVariable int taskId, @PathVariable int noteId) {
        boolean ok = service.deleteNote(taskId, noteId);
        return ok ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
