package com.example.demo.form;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@AllArgsConstructor
public class UserCreateForm {
    @NotEmpty
    @Size(min = 5, max = 50, message = "ユーザーIDは5~50文字で設定してください")
    private String username;
    @NotEmpty
    @Size(min = 5, max = 50, message = "パスワードは5~50文字で設定してください")
    private String password;
    @NotNull(message = "{dateFrom.notnull}")
    @DateTimeFormat(pattern="yyyy/MM/dd")
    private Date birthday;
}
