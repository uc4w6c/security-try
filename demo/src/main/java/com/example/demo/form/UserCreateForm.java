package com.example.demo.form;

import com.example.demo.validation.annotation.EqualsPropertyValues;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@ToString(exclude={"password", "passwordConfirmation"})
@EqualsPropertyValues(property = "password", comparingProperty = "passwordConfirmation")
public class UserCreateForm {
    @NotEmpty
    @Size(min = 1, max = 50)
    @Email // RFC2822準拠 これよりもゆるい場合は個別実装の必要あり。
    private final String email;
    @NotEmpty
    @Size(min = 5, max = 50)
    private final String password;
    @NotEmpty
    @Size(min = 5, max = 50)
    private final String passwordConfirmation;
    @NotNull(message = "{dateFrom.notnull}")
    @DateTimeFormat(pattern="yyyy/MM/dd")
    private final Date birthday;
}
