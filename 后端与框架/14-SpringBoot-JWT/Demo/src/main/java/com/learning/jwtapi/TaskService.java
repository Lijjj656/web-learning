package com.learning.jwtapi;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class TaskService {
    private final TaskMapper mapper;
    private final AuthService authService;

    public TaskService(TaskMapper mapper, AuthService authService) {
        this.mapper = mapper;
        this.authService = authService;
    }

    public List<TaskResponse> list(String authorization) {
        Long userId = currentUserId(authorization);
        return mapper.findByUserId(userId).stream().map(TaskResponse::from).toList();
    }

    public TaskResponse create(TaskRequest request, String authorization) {
        Long userId = currentUserId(authorization);
        Task task = new Task();
        task.setUserId(userId);
        task.setTitle(request.title().trim());
        task.setCategory(request.category().trim());
        task.setPriority(request.priority().trim());
        task.setStatus(request.status().trim());
        mapper.insert(task);
        return TaskResponse.from(requireOwned(task.getId(), userId));
    }

    public TaskResponse update(Long id, TaskRequest request, String authorization) {
        Long userId = currentUserId(authorization);
        Task task = new Task();
        task.setId(id);
        task.setUserId(userId);
        task.setTitle(request.title().trim());
        task.setCategory(request.category().trim());
        task.setPriority(request.priority().trim());
        task.setStatus(request.status().trim());
        if (mapper.update(task) == 0) throw notFound();
        return TaskResponse.from(requireOwned(id, userId));
    }

    public void delete(Long id, String authorization) {
        Long userId = currentUserId(authorization);
        if (mapper.delete(id, userId) == 0) throw notFound();
    }

    public TaskStats stats(String authorization) {
        TaskStats stats = mapper.statsByUserId(currentUserId(authorization));
        if (stats == null) {
            TaskStats empty = new TaskStats();
            return empty;
        }
        return stats;
    }

    private Long currentUserId(String authorization) {
        return authService.currentUserFromAuthorization(authorization).getId();
    }

    private Task requireOwned(Long id, Long userId) {
        Task task = mapper.findByIdAndUserId(id, userId);
        if (task == null) throw notFound();
        return task;
    }

    private ResponseStatusException notFound() {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "任务不存在或无法访问，请刷新任务列表");
    }
}
