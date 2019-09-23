package com.example.demo.repository;

import com.example.demo.domain.Account;
import com.example.demo.domain.PasswordReissueInfo;
import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.Result;

import java.util.Date;
import java.util.Optional;

@ConfigAutowireable
@Dao
public interface PasswordReissueInfoRepository {
    @Select
    public Optional<PasswordReissueInfo> findByEmail(String email);

    @Update
    public Result<PasswordReissueInfo> update(PasswordReissueInfo passwordReissueInfo);

    @Insert
    public Result<PasswordReissueInfo> insert(PasswordReissueInfo passwordReissueInfo);
}
