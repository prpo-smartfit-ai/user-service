package com.smartfitai.models.dto;

import com.smartfitai.models.User;

public class AuthResponse {
    private UserDto user;
    private String token;
    private String message;

    public AuthResponse() {}

    public AuthResponse(UserDto user, String token) {
        this.user = user;
        this.token = token;
    }

    public static class UserDto {
        private String id;
        private String email;
        private String firstName;
        private String lastName;
        private String createdAt;

        public UserDto(User user) {
            this.id = String.valueOf(user.getId());
            this.email = user.getEmail();
            this.firstName = user.getFirstName();
            this.lastName = user.getLastName();
            this.createdAt = user.getCreatedAt() != null ? user.getCreatedAt().toString() : null;
        }

        public String getId() { return id; }
        public void setId(String id) { this.id = id; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getFirstName() { return firstName; }
        public void setFirstName(String firstName) { this.firstName = firstName; }
        public String getLastName() { return lastName; }
        public void setLastName(String lastName) { this.lastName = lastName; }
        public String getCreatedAt() { return createdAt; }
        public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
    }

    public UserDto getUser() { return user; }
    public void setUser(UserDto user) { this.user = user; }
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
