package com.onix.hcmustour.controller.v1.api;

import com.onix.hcmustour.controller.v1.request.AuthenticationRequest;
import com.onix.hcmustour.controller.v1.request.RegisterRequest;
import com.onix.hcmustour.dto.model.UserDto;
import com.onix.hcmustour.dto.response.Response;
import com.onix.hcmustour.model.Role;
import com.onix.hcmustour.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register")
    public Response<?> register(@RequestBody @Valid RegisterRequest request) {
        return Response.ok().setPayload(registerUser(request));
    }

    @PostMapping("/authenticate")
    public Response<?> authenticate(@RequestBody @Valid AuthenticationRequest request) {
        return Response.ok().setPayload(authenticationService.authenticate(request));
    }

    private UserDto registerUser(RegisterRequest request) {
        UserDto userDto = new UserDto()
                .setEmail(request.getEmail())
                .setPassword(request.getPassword())
                .setFirstName(request.getFirstName())
                .setLastName(request.getLastName())
                .setMobileNumber(request.getMobileNumber())
                .setRole(Role.USER);
        return authenticationService.register(userDto);
    }
}
