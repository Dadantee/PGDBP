package com.inversion.pgdbp.user.dto.response;

import com.inversion.pgdbp.user.entity.UserEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class UserResponse extends UserEntity {
    public static UserResponse of(UserEntity user) {
        return UserResponse.builder()
                .id(user.getId())
                .lastName(user.getLastName())
                .firstName(user.getFirstName())
                .build();
    }



}
