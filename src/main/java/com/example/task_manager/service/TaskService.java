package com.example.task_manager.service;

import com.example.task_manager.model.Note;
import com.example.task_manager.model.Task;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class TaskService {
    private final Map<Integer, Task> store = new ConcurrentHashMap<>();
    private final AtomicInteger taskIdGen = new AtomicInteger(1);
    private final AtomicInteger noteIdGen = new AtomicInteger(1);

    public List<Task> findAll(Boolean completed) {
        List<Task> all = new ArrayList<>(store.values());
        if (completed == null) return all;
        return all.stream()
                  .filter(t -> Boolean.valueOf(t.getCompleted()).equals(completed))
                  .collect(Collectors.toList());
    }

    public Task findById(int id) {
        return store.get(id);
    }

    public Task create(Task incoming) {
        int id = taskIdGen.getAndIncrement();
        incoming.setId(id);

        // assign ids to incoming notes
        if (incoming.getNotes() != null) {
            for (Note n : incoming.getNotes()) {
                n.setId(noteIdGen.getAndIncrement());
            }
        } else {
            incoming.setNotes(new ArrayList<>());
        }
        store.put(id, incoming);
        return incoming;
    }

    public Task updatePartial(int id, Map<String,Object> updates) {
        Task existing = store.get(id);
        if (existing == null) return null;

        if (updates.containsKey("title")) existing.setTitle((String) updates.get("title"));
        if (updates.containsKey("description")) existing.setDescription((String) updates.get("description"));
        if (updates.containsKey("deadline")) existing.setDeadline((String) updates.get("deadline"));
        if (updates.containsKey("completed")) existing.setCompleted(Boolean.valueOf(updates.get("completed").toString()));

        // allow replacing notes array if provided (each note will be assigned ids)
        if (updates.containsKey("notes")) {
            Object raw = updates.get("notes");
            if (raw instanceof List) {
                List<?> list = (List<?>) raw;
                List<Note> newNotes = new ArrayList<>();
                for (Object o : list) {
                    if (o instanceof Map) {
                        Map<?,?> m = (Map<?,?>) o;
                        String title = (String) m.get("title");
                        String body = (String) m.get("body");
                        Note note = new Note(noteIdGen.getAndIncrement(), title, body);
                        newNotes.add(note);
                    }
                }
                existing.setNotes(newNotes);
            }
        }

        return existing;
    }

    public boolean deleteTask(int id) {
        return store.remove(id) != null;
    }

    public Note addNote(int taskId, Note note) {
        Task t = store.get(taskId);
        if (t == null) return null;
        note.setId(noteIdGen.getAndIncrement());
        t.getNotes().add(note);
        return note;
    }

    public boolean deleteNote(int taskId, int noteId) {
        Task t = store.get(taskId);
        if (t == null) return false;
        return t.getNotes().removeIf(n -> n.getId() == noteId);
    }
}
