package com.learning.jwtapi;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService service;

    public TaskController(TaskService service) { this.service = service; }

    @GetMapping
    public List<TaskResponse> list(@RequestHeader(value = "Authorization", required = false) String authorization) {
        return service.list(authorization);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskResponse create(@Valid @RequestBody TaskRequest request, @RequestHeader(value = "Authorization", required = false) String authorization) {
        return service.create(request, authorization);
    }

    @PutMapping("/{id}")
    public TaskResponse update(@PathVariable Long id, @Valid @RequestBody TaskRequest request, @RequestHeader(value = "Authorization", required = false) String authorization) {
        return service.update(id, request, authorization);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id, @RequestHeader(value = "Authorization", required = false) String authorization) {
        service.delete(id, authorization);
    }

    @GetMapping("/stats")
    public TaskStats stats(@RequestHeader(value = "Authorization", required = false) String authorization) {
        return service.stats(authorization);
    }
}
