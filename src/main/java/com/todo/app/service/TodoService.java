package com.todo.app.service;

import com.todo.app.model.Todo;
import com.todo.app.model.Todo.Priority;
import com.todo.app.model.TodoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TodoService {

    private final TodoRepository repo;

    public TodoService(TodoRepository repo) {
        this.repo = repo;
    }

    public List<Todo> getAll() {
        return repo.findAllByOrderByCreatedAtDesc();
    }

    @Transactional
    public Todo create(String title, String description, Priority priority) {
        Todo todo = new Todo();
        todo.setTitle(title);
        todo.setDescription(description);
        todo.setPriority(priority != null ? priority : Priority.MEDIUM);
        return repo.save(todo);
    }

    @Transactional
    public Todo toggle(Long id) {
        Todo todo = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found: " + id));
        todo.setCompleted(!todo.isCompleted());
        return repo.save(todo);
    }

    @Transactional
    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new RuntimeException("Todo not found: " + id);
        }
        repo.deleteById(id);
    }
}
