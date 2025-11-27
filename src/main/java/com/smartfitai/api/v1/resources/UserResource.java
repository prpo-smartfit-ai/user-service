package com.smartfitai.api.v1.resources;

import com.smartfitai.models.User;
import com.smartfitai.models.dto.*;
import com.smartfitai.services.UserService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

@RequestScoped
@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    @Inject
    private UserService userService;

    @GET
    @Path("/health")
    public Response healthCheck() {
        Map<String, String> health = new HashMap<>();
        health.put("status", "UP");
        health.put("service", "user-service");
        return Response.ok(health).build();
    }

    @POST
    @Path("/register")
    public Response register(RegisterRequest request) {
        try {
            // Check if user already exists
            User existingUser = userService.findByEmail(request.getEmail());
            if (existingUser != null) {
                return Response.status(Response.Status.CONFLICT)
                        .entity(new ApiResponse<>(null, "Email already registered"))
                        .build();
            }

            // Create new user
            User user = userService.createUser(
                    request.getEmail(),
                    request.getPassword(),
                    request.getFirstName(),
                    request.getLastName());

            AuthResponse.UserDto userDto = new AuthResponse.UserDto(user);
            return Response.status(Response.Status.CREATED)
                    .entity(new ApiResponse<>(userDto, "User registered successfully"))
                    .build();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ApiResponse<>(null, "Registration failed: " + e.getMessage()))
                    .build();
        }
    }

    @POST
    @Path("/login")
    public Response login(LoginRequest request) {
        try {
            User user = userService.findByEmail(request.getEmail());

            if (user == null || !userService.verifyPassword(user, request.getPassword())) {
                return Response.status(Response.Status.UNAUTHORIZED)
                        .entity(new ApiResponse<>(null, "Invalid email or password"))
                        .build();
            }

            String token = userService.generateToken(user);
            AuthResponse.UserDto userDto = new AuthResponse.UserDto(user);
            AuthResponse authResponse = new AuthResponse(userDto, token);

            return Response.ok(new ApiResponse<>(authResponse, "Login successful")).build();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ApiResponse<>(null, "Login failed: " + e.getMessage()))
                    .build();
        }
    }
}
