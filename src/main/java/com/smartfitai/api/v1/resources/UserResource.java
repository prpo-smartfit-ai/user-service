package com.smartfitai.api.v1.resources;

import com.smartfitai.config.Secured;
import com.smartfitai.models.User;
import com.smartfitai.models.dto.*;
import com.smartfitai.services.UserService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
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
    
    @Context
    private ContainerRequestContext requestContext;

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

            // Generate JWT token
            String token = userService.generateToken(user);
            AuthResponse.UserDto userDto = new AuthResponse.UserDto(user);
            AuthResponse authResponse = new AuthResponse(userDto, token);

            return Response.status(Response.Status.CREATED)
                    .entity(new ApiResponse<>(authResponse, "User registered successfully"))
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
    
    @GET
    @Path("/profile")
    @Secured
    public Response getProfile() {
        try {
            // Get userId from the request context (set by JwtAuthenticationFilter)
            Long userId = (Long) requestContext.getProperty("userId");
            
            User user = userService.findById(userId);
            if (user == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(new ApiResponse<>(null, "User not found"))
                        .build();
            }
            
            UserProfileResponse profile = new UserProfileResponse(user);
            return Response.ok(new ApiResponse<>(profile, "Profile retrieved successfully")).build();
            
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ApiResponse<>(null, "Failed to retrieve profile: " + e.getMessage()))
                    .build();
        }
    }
    
    @PUT
    @Path("/profile")
    @Secured
    public Response updateProfile(ProfileUpdateRequest request) {
        try {
            // Get userId from the request context
            Long userId = (Long) requestContext.getProperty("userId");
            
            User user = userService.findById(userId);
            if (user == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(new ApiResponse<>(null, "User not found"))
                        .build();
            }
            
            // Update user profile fields
            if (request.getAge() != null) user.setAge(request.getAge());
            if (request.getHeight() != null) user.setHeight(request.getHeight());
            if (request.getCurrentWeight() != null) user.setCurrentWeight(request.getCurrentWeight());
            if (request.getGender() != null) user.setGender(User.Gender.valueOf(request.getGender()));
            if (request.getFitnessLevel() != null) user.setFitnessLevel(User.FitnessLevel.valueOf(request.getFitnessLevel()));
            if (request.getPrimaryGoal() != null) user.setPrimaryGoal(User.FitnessGoal.valueOf(request.getPrimaryGoal()));
            if (request.getPreferredDaysPerWeek() != null) user.setPreferredDaysPerWeek(request.getPreferredDaysPerWeek());
            if (request.getPreferredSessionDuration() != null) user.setPreferredSessionDuration(request.getPreferredSessionDuration());
            
            userService.updateUser(user);
            
            UserProfileResponse profile = new UserProfileResponse(user);
            return Response.ok(new ApiResponse<>(profile, "Profile updated successfully")).build();
            
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ApiResponse<>(null, "Failed to update profile: " + e.getMessage()))
                    .build();
        }
    }
    
    @GET
    @Path("/me")
    @Secured
    public Response getCurrentUser() {
        try {
            // Get userId from the request context (set by JwtAuthenticationFilter)
            Long userId = (Long) requestContext.getProperty("userId");
            
            User user = userService.findById(userId);
            if (user == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(new ApiResponse<>(null, "User not found"))
                        .build();
            }
            
            AuthResponse.UserDto userDto = new AuthResponse.UserDto(user);
            return Response.ok(new ApiResponse<>(userDto, "User retrieved successfully")).build();
            
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ApiResponse<>(null, "Failed to retrieve user: " + e.getMessage()))
                    .build();
        }
    }
}
