package com.smartfitai.api.v1.resources;

import com.smartfitai.config.Secured;
import com.smartfitai.models.User;
import com.smartfitai.models.dto.*;
import com.smartfitai.services.UserService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

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
@Tag(name = "Users", description = "Operations related to user management and profiles")
public class UserResource {

    @Inject
    private UserService userService;
    
    @Context
    private ContainerRequestContext requestContext;

    @GET
    @Path("/health")
    @Operation(summary = "Health Check", description = "Check if the user service is running.")
    @APIResponse(responseCode = "200", description = "Service is up")
    public Response healthCheck() {
        Map<String, String> health = new HashMap<>();
        health.put("status", "UP");
        health.put("service", "user-service");
        return Response.ok(health).build();
    }

    @POST
    @Path("/register")
    @Operation(summary = "Register User", description = "Register a new user in the system.")
    @APIResponses({
        @APIResponse(responseCode = "201", description = "User registered successfully", 
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))),
        @APIResponse(responseCode = "409", description = "Email already registered"),
        @APIResponse(responseCode = "500", description = "Internal server error")
    })
    public Response register(@RequestBody(description = "User registration details", required = true,
                                         content = @Content(schema = @Schema(implementation = RegisterRequest.class)))
                             RegisterRequest request) {
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
    @Operation(summary = "Login User", description = "Authenticate a user and return a JWT token.")
    @APIResponses({
        @APIResponse(responseCode = "200", description = "Login successful", 
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))),
        @APIResponse(responseCode = "401", description = "Invalid credentials"),
        @APIResponse(responseCode = "500", description = "Internal server error")
    })
    public Response login(@RequestBody(description = "Login credentials", required = true,
                                      content = @Content(schema = @Schema(implementation = LoginRequest.class)))
                          LoginRequest request) {
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
    @Operation(summary = "Get User Profile", description = "Retrieve current user's profile information.")
    @APIResponses({
        @APIResponse(responseCode = "200", description = "Profile retrieved successfully"),
        @APIResponse(responseCode = "401", description = "Unauthorized"),
        @APIResponse(responseCode = "404", description = "User not found"),
        @APIResponse(responseCode = "500", description = "Internal server error")
    })
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
    @Operation(summary = "Update User Profile", description = "Update current user's physical profile and goals.")
    @APIResponses({
        @APIResponse(responseCode = "200", description = "Profile updated successfully"),
        @APIResponse(responseCode = "401", description = "Unauthorized"),
        @APIResponse(responseCode = "404", description = "User not found"),
        @APIResponse(responseCode = "500", description = "Internal server error")
    })
    public Response updateProfile(@RequestBody(description = "Profile update data", required = true,
                                             content = @Content(schema = @Schema(implementation = ProfileUpdateRequest.class)))
                                  ProfileUpdateRequest request) {
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
            if (request.getFirstName() != null) user.setFirstName(request.getFirstName());
            if (request.getLastName() != null) user.setLastName(request.getLastName());
            if (request.getAge() != null) user.setAge(request.getAge());
            if (request.getHeight() != null) user.setHeight(request.getHeight());
            if (request.getCurrentWeight() != null) user.setCurrentWeight(request.getCurrentWeight());
            if (request.getGender() != null) user.setGender(User.Gender.valueOf(request.getGender()));
            if (request.getFitnessLevel() != null) user.setFitnessLevel(User.FitnessLevel.valueOf(request.getFitnessLevel()));
            if (request.getPrimaryGoal() != null) user.setPrimaryGoal(User.FitnessGoal.valueOf(request.getPrimaryGoal()));
            if (request.getPreferredDaysPerWeek() != null) user.setPreferredDaysPerWeek(request.getPreferredDaysPerWeek());
            if (request.getPreferredSessionDuration() != null) user.setPreferredSessionDuration(request.getPreferredSessionDuration());
            if (request.getTotalWorkouts() != null) user.setTotalWorkouts(request.getTotalWorkouts());
            if (request.getTotalHours() != null) user.setTotalHours(request.getTotalHours());
            
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
    @Operation(summary = "Get Current User", description = "Retrieve current authenticated user basic info.")
    @APIResponse(responseCode = "200", description = "User retrieved successfully")
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
