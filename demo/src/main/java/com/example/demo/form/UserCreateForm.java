package com.example.demo.form;

import com.example.demo.validation.annotation.EqualsPropertyValues;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsPropertyValues(property = "password", comparingProperty = "passwordConfirmation")
public class UserCreateForm {
    @NotEmpty
    @Size(min = 1, max = 50)
    @Email // RFC2822準拠 これよりもゆるい場合は個別実装の必要あり。
    private String email;
    @NotEmpty
    @Size(min = 5, max = 50, message = "パスワードは5~50文字で設定してください")
    private String password;
    @NotEmpty
    @Size(min = 5, max = 50, message = "パスワードは5~50文字で設定してください")
    private String passwordConfirmation;
    @NotNull(message = "{dateFrom.notnull}")
    @DateTimeFormat(pattern="yyyy/MM/dd")
    private Date birthday;
}
