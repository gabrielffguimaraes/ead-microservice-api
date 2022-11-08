package com.ead.authuser.controllers;

import com.ead.authuser.controllers.filters.UserFilter;
import com.ead.authuser.dtos.UserDto;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.services.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin(origins = "*",maxAge = 3600)
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    ModelMapper modelMapper;
    @GetMapping
    @JsonView(UserDto.UserView.ResponsePost.class)
    public ResponseEntity<Page<UserDto>> getAllUsers(UserFilter userFilter,
                                                     @PageableDefault(page=0 ,size=20 , sort = "userId" , direction = Sort.Direction.DESC)
                                                     Pageable pageable) {

        var list = userService.findAll(userFilter,pageable);
        List<UserDto> listDto = Arrays.asList(modelMapper.map(list.getContent(),UserDto[].class));
        return ResponseEntity.ok(new PageImpl<>(listDto,pageable,list.getTotalElements()));
    }
    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable(value = "id") UUID userId) {
        Optional<UserModel> userModelOptional = userService.findById(userId);
        if(userModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(userModelOptional.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable(value = "id") UUID userId) {
        Optional<UserModel> user = userService.findById(userId);
        if(user.isPresent()) {
            userService.deleteById(user.get().getUserId());
            return ResponseEntity.status(HttpStatus.OK).body("User deleted succesful");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }
}
