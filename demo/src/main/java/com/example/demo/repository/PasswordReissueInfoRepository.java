package com.example.demo.repository;

import com.example.demo.domain.Account;
import com.example.demo.domain.PasswordReissueInfo;
import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.Result;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@ConfigAutowireable
@Dao
public interface PasswordReissueInfoRepository {
    @Select
    public Optional<PasswordReissueInfo> findByEmail(String email);

    @Select
    public Optional<PasswordReissueInfo> findByToken(String token, LocalDateTime nowTime);

    @Update
    public Result<PasswordReissueInfo> update(PasswordReissueInfo passwordReissueInfo);

    @Insert
    public Result<PasswordReissueInfo> insert(PasswordReissueInfo passwordReissueInfo);
}
