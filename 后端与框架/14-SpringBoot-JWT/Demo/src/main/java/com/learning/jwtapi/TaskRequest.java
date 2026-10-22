package com.learning.jwtapi;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record TaskRequest(
        @NotBlank @Size(max = 200) String title,
        @NotBlank @Pattern(regexp = "Web|Java|AI") String category,
        @NotBlank @Pattern(regexp = "普通|高") String priority,
        @NotBlank @Pattern(regexp = "未开始|进行中|已完成") String status
) {}
