package ch.zli.m223.punchclock.controller;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashSet;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.jwt.Claims;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import ch.zli.m223.punchclock.ViewModel.LoginResultViewModel;
import ch.zli.m223.punchclock.ViewModel.LoginViewModel;
import io.smallrye.jwt.build.Jwt;

/*
* Do not use in productive environments!
*/

@Tag(name = "Authorization", description = "Sample to manage Authorization")
@Path("/auth")
public class AuthentificationController {
    
    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public LoginResultViewModel login(LoginViewModel loginViewModel){
        if(loginViewModel.getUsername().equals("user") && loginViewModel.getPassword().equals("secure")){
            String token =
            Jwt.issuer("https://zli.ch/issuer") 
              .upn("user@zli.ch") 
              .groups(new HashSet<>(Arrays.asList("User", "Admin"))) 
              .claim(Claims.birthdate.name(), "2001-07-13")
              .expiresIn(Duration.ofHours(1)) 
            .sign();
            return new LoginResultViewModel(token);
        }
        throw new NotAuthorizedException("User ["+loginViewModel.getUsername()+"] not known");
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsername(LoginViewModel loginViewModel){
        return Response.ok(loginViewModel.getUsername()).build();
    }
}



