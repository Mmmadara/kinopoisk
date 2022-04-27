package com.example.demo.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UserDTO {
    @NotEmpty
    private Long id;
    @NotEmpty
    private String username;
    @NotEmpty
    private String name;
    @NotEmpty
    private String lastname;

}
