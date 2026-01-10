package com.smartfitai;

import com.kumuluz.ee.EeApplication;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.servers.Server;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/v1")
@OpenAPIDefinition(
    info = @Info(
        title = "User Service API",
        version = "1.0.0",
        description = "API for user management, authentication, and profile tracking in SmartFit AI application."
    ),
    servers = {
        @Server(url = "http://4.232.72.237/user-service/v1", description = "Staging Server"),
        @Server(url = "http://localhost:8081/v1", description = "Local development server")
    }
)
public class UserApplication extends Application {
    
    public static void main(String[] args) {
        EeApplication.main(args);
    }
}
