package com.example.demo.form;

import com.example.demo.validation.annotation.EqualsPropertyValues;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@AllArgsConstructor
@EqualsPropertyValues(property = "password", comparingProperty = "passwordConfirmation")
public class UserPasswordChangeForm {
    @NotEmpty
    @Size(min = 5, max = 50, message = "パスワードは5~50文字で設定してください")
    private String password;
    @NotEmpty
    @Size(min = 5, max = 50, message = "パスワードは5~50文字で設定してください")
    private String passwordConfirmation;
}
