package com.example.demo.form;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
@AllArgsConstructor
public class UserCreateForm {
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
}
