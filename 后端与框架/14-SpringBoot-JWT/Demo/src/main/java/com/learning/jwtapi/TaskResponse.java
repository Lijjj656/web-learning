package com.learning.jwtapi;

import java.time.LocalDateTime;

public record TaskResponse(Long id, String title, String category, String priority, String status, LocalDateTime createdAt) {
    public static TaskResponse from(Task task) {
        return new TaskResponse(task.getId(), task.getTitle(), task.getCategory(), task.getPriority(), task.getStatus(), task.getCreatedAt());
    }
}
