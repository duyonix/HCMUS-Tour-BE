package com.onix.hcmustour.controller.v1.api;

import com.onix.hcmustour.controller.v1.request.AuthenticationRequest;
import com.onix.hcmustour.controller.v1.request.RegisterRequest;
import com.onix.hcmustour.dto.model.UserDto;
import com.onix.hcmustour.dto.response.Response;
import com.onix.hcmustour.model.Role;
import com.onix.hcmustour.service.AuthenticationService;
import com.onix.hcmustour.util.ValueMapper;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@Slf4j
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<Response> register(@RequestBody @Valid RegisterRequest request) {
        log.info("AuthenticationController::register request body {}", ValueMapper.jsonAsString(request));
        Response<Object> response = Response.ok().setPayload(authenticationService.register(request));
        log.info("AuthenticationController::register response {}", ValueMapper.jsonAsString(response));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<Response> authenticate(@RequestBody @Valid AuthenticationRequest request) {
        log.info("AuthenticationController::authenticate request body {}", ValueMapper.jsonAsString(request));
        Response<Object> response = Response.ok().setPayload(authenticationService.authenticate(request));
        log.info("AuthenticationController::authenticate response {}", ValueMapper.jsonAsString(response));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
