package com.example.demo.form;

import com.example.demo.validation.annotation.EqualsPropertyValues;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class PostCreateForm {
    @NotEmpty
    @Size(min = 1, max = 50)
    private final String body;
}
