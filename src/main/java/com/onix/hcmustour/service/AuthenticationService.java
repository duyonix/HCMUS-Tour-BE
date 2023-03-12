package com.onix.hcmustour.service;

import com.onix.hcmustour.controller.v1.request.AuthenticationRequest;
import com.onix.hcmustour.dto.mapper.UserMapper;
import com.onix.hcmustour.dto.model.AuthenticationDto;
import com.onix.hcmustour.dto.model.UserDto;
import com.onix.hcmustour.exception.ApplicationException;
import com.onix.hcmustour.exception.EntityType;
import com.onix.hcmustour.exception.ExceptionType;
import com.onix.hcmustour.model.Token;
import com.onix.hcmustour.model.TokenType;
import com.onix.hcmustour.model.User;
import com.onix.hcmustour.repository.TokenRepository;
import com.onix.hcmustour.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final UserRepository userRepository;

    private final TokenRepository tokenRepository;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final ModelMapper modelMapper;

    public UserDto register(UserDto userDto) {
        Optional<User> user = userRepository.findByEmail(userDto.getEmail());
        if (user.isEmpty()) {
            User newUser = new User()
                    .setEmail(userDto.getEmail())
                    .setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()))
                    .setFirstName(userDto.getFirstName())
                    .setLastName(userDto.getLastName())
                    .setMobileNumber(userDto.getMobileNumber())
                    .setRole(userDto.getRole());
            User savedUser = userRepository.save(newUser);
            String jwtToken = jwtService.generateToken(newUser);
            saveUserToken(newUser, jwtToken);
            return UserMapper.toUserDto(savedUser);
        }
        throw exception(EntityType.USER, ExceptionType.DUPLICATE_ENTITY, userDto.getEmail());
    }

    public AuthenticationDto authenticate(AuthenticationRequest request) {
        Optional<User> user = userRepository.findByEmail(request.getEmail());
        if (user.isPresent()) {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
            String jwtToken = jwtService.generateToken(user.get());
            saveUserToken(user.get(), jwtToken);
            return new AuthenticationDto()
                    .setToken(jwtToken)
                    .setUser(UserMapper.toUserDto(user.get()));
        }
        throw exception(EntityType.USER, ExceptionType.ENTITY_NOT_FOUND, request.getEmail());
    }

    public UserDto findUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            return modelMapper.map(user.get(), UserDto.class);
        }
        throw exception(EntityType.USER, ExceptionType.ENTITY_NOT_FOUND, email);
    }

    public UserDto updateProfile(UserDto userDto) {
        Optional<User> user = userRepository.findByEmail(userDto.getEmail());
        if (user.isPresent()) {
            User updatedUser = user.get()
                    .setFirstName(userDto.getFirstName())
                    .setLastName(userDto.getLastName())
                    .setMobileNumber(userDto.getMobileNumber())
                    .setModel(userDto.getModel());
            User savedUser = userRepository.save(updatedUser);
            return UserMapper.toUserDto(savedUser);
        }
        throw exception(EntityType.USER, ExceptionType.ENTITY_NOT_FOUND, userDto.getEmail());
    }

    public UserDto changePassword(UserDto userDto, String newPassword) {
        Optional<User> user = userRepository.findByEmail(userDto.getEmail());
        if (user.isPresent()) {
            User updatedUser = user.get()
                    .setPassword(bCryptPasswordEncoder.encode(newPassword));
            User savedUser = userRepository.save(updatedUser);
            return UserMapper.toUserDto(savedUser);
        }
        throw exception(EntityType.USER, ExceptionType.ENTITY_NOT_FOUND, userDto.getEmail());
    }

    private void saveUserToken(User user, String jwtToken) {
        Token token = new Token()
                .setUser(user)
                .setToken(jwtToken)
                .setTokenType(TokenType.BEARER)
                .setRevoked(false)
                .setExpired(false);
        tokenRepository.save(token);
    }

    private void revokeUserToken(User user) {
        List<Token> validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty()) return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    private RuntimeException exception(EntityType entityType, ExceptionType exceptionType, String... args) {
        return ApplicationException.throwException(entityType, exceptionType, args);
    }
}
