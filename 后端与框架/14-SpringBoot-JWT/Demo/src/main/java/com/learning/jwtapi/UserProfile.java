package com.learning.jwtapi;

public record UserProfile(Long id, String name, String email) {
    public static UserProfile from(User user) {
        return new UserProfile(user.getId(), user.getName(), user.getEmail());
    }
}
