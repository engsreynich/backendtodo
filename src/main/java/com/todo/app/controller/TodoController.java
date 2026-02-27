package com.todo.app.controller;

import com.todo.app.model.Todo;
import com.todo.app.model.Todo.Priority;
import com.todo.app.service.TodoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")   // tighten to your Netlify URL in production
public class TodoController {

    private final TodoService service;

    public TodoController(TodoService service) {
        this.service = service;
    }

    // ── GET /api/health ───────────────────────────────────────────────────
    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> health() {
        return ResponseEntity.ok(Map.of(
            "status",    "UP",
            "timestamp", Instant.now().toString(),
            "service",   "todo-backend"
        ));
    }

    // ── GET /api/todos ────────────────────────────────────────────────────
    @GetMapping("/todos")
    public ResponseEntity<List<Todo>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    // ── POST /api/todos ───────────────────────────────────────────────────
    @PostMapping("/todos")
    public ResponseEntity<Todo> create(@Valid @RequestBody CreateTodoRequest req) {
        Todo created = service.create(req.title(), req.description(), req.priority());
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // ── PATCH /api/todos/{id}/toggle ──────────────────────────────────────
    @PatchMapping("/todos/{id}/toggle")
    public ResponseEntity<Todo> toggle(@PathVariable Long id) {
        return ResponseEntity.ok(service.toggle(id));
    }

    // ── DELETE /api/todos/{id} ────────────────────────────────────────────
    @DeleteMapping("/todos/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    // ── Request record ────────────────────────────────────────────────────
    record CreateTodoRequest(
        @NotBlank String title,
        String description,
        Priority priority
    ) {}
}
