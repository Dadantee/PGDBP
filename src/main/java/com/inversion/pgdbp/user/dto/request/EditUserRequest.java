package com.inversion.pgdbp.user.dto.request;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditUserRequest {
    private Long id;
    private String firstName;
    private String lastName;
}
