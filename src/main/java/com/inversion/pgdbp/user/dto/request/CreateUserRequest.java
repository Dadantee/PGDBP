package com.inversion.pgdbp.user.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreateUserRequest {

    private String firstName;
    private String lastName;
}
