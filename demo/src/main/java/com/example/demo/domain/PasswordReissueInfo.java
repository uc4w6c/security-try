package com.example.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
import org.seasar.doma.jdbc.entity.NamingType;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@AllArgsConstructor
@Entity(immutable = true, naming = NamingType.SNAKE_UPPER_CASE)
@Table(name = "password_reissue_info")
public class PasswordReissueInfo {
    @Id
    private final String email;
    private final String token;
    private final LocalDateTime expiry_date;

    /**
     * token更新
     * @param token
     * @param expiry_date
     * @return PasswordReissueInfo
     */
    public PasswordReissueInfo tokenUpdate(String token, LocalDateTime expiry_date) {
        return new PasswordReissueInfo(this.email, token, expiry_date);
    }
}
