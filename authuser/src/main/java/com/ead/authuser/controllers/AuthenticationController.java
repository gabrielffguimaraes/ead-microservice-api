package com.ead.authuser.controllers;

import com.ead.authuser.configs.security.JwtProvider;
import com.ead.authuser.dtos.JwtDto;
import com.ead.authuser.dtos.LoginDto;
import com.ead.authuser.dtos.UserDto;
import com.ead.authuser.models.User;
import com.ead.authuser.services.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RestController
@CrossOrigin(origins = "*" , maxAge = 3600)
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    public UserService userService;

    @Autowired
    public ModelMapper modelMapper;

    @Autowired
    public JwtProvider jwtProvider;

    @PostMapping("/signup")
    @JsonView(UserDto.UserView.ResponsePost.class)
    public ResponseEntity<Object> signup(@RequestBody
                                             @JsonView(UserDto.UserView.RegistrationPost.class)
                                             @Validated(UserDto.UserView.RegistrationPost.class)
                                             UserDto userDto) {

        if(userService.existsByUsername(userDto.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(List.of("Error : Username is Already Taken !"));
        }
        if(userService.existsByEmail(userDto.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(List.of("Error : Email is Already Taken !"));
        };

        var savedUser = userService.saveUser(userDto);

        UserDto userResponse =  modelMapper.map(savedUser,UserDto.class);

        log.debug("POST registerUser userModel saved {}",savedUser.getUserId());
        log.debug("User saved successfully userId {}",savedUser.getUserId());

        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtDto> authenticateUser(@Valid @RequestBody LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(),loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateJwtToken(authentication);
        return ResponseEntity.ok(new JwtDto(jwt));
    }

    @PutMapping("/{userId}")
    @JsonView(UserDto.UserView.ResponsePost.class)
    public ResponseEntity<Object> updateUser(@PathVariable UUID userId,
                                             @RequestBody
                                             @JsonView(UserDto.UserView.UserPut.class)
                                             UserDto userDto) {
        Optional<User> userModel = userService.findById(userId);
        if(!userModel.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error : User not found .");
        }
        userDto.setUserId(userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.updateUser(userDto));
    }

    @PutMapping("/{userId}/password")
    public ResponseEntity<Object> updatePassword(@PathVariable UUID userId,
                                             @RequestBody
                                             @JsonView(UserDto.UserView.PasswordPut.class)
                                             @Validated(UserDto.UserView.PasswordPut.class)
                                             UserDto userDto) {
        Optional<User> userModel = userService.findById(userId);
        if(userModel.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error : User not found .");
        } else if(!userModel.get().getPassword().equals(userDto.getOldPassword())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(List.of("Error : Mismatched old password ."));
        } else {
            var user = userModel.get();
            user.setPassword(userDto.getPassword());
            user.setOldPassword(userDto.getOldPassword());
            user.setLastUpdateDate(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));
            userService.updatePassword(user);
            return ResponseEntity.status(HttpStatus.OK).body("Password updated successful .");
        }
    }

    @PutMapping("/{userId}/image")
    public ResponseEntity<Object> updateImage(@PathVariable UUID userId,
                                                 @RequestBody
                                                 @JsonView(UserDto.UserView.ImagePut.class)
                                                 @Validated(UserDto.UserView.ImagePut.class)
                                                 UserDto userDto) {
        Optional<User> userModel = userService.findById(userId);
        if(userModel.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error : User not found .");
        } else {
            var user = userModel.get();
            user.setImageUrl(userDto.getImageUrl());
            user.setLastUpdateDate(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));
            userService.updateUser(userDto);
            return ResponseEntity.status(HttpStatus.OK).body("Image updated successful .");
        }
    }
}
