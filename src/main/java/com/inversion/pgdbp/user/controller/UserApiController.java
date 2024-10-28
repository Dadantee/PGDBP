package com.inversion.pgdbp.user.controller;

import com.inversion.pgdbp.user.dto.request.CreateUserRequest;
import com.inversion.pgdbp.user.dto.response.UserResponse;
import com.inversion.pgdbp.user.entity.UserEntity;
import com.inversion.pgdbp.user.exceptions.UserNotFoundException;
import com.inversion.pgdbp.user.repository.UserRepository;
import com.inversion.pgdbp.user.routes.UserRoutes;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
public class UserApiController {
    private final UserRepository userRepository;


    @GetMapping(UserRoutes.GET)
    public UserResponse getUser(@PathVariable Long id) throws UserNotFoundException {
        return  UserResponse.of(userRepository.findById(id).orElseThrow(UserNotFoundException::new));
    }

    @PostMapping(UserRoutes.CREATE)
    public UserResponse create(@RequestBody CreateUserRequest request) {
        UserEntity user = UserEntity.builder()
                .lastName(request.getLastName())
                .firstName(request.getFirstName())
                .build();
        user = userRepository.save(user);
        return UserResponse.of(user);

    }
}
