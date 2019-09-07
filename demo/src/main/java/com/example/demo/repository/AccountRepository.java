package com.example.demo.repository;

import com.example.demo.domain.Account;
import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.Result;

@ConfigAutowireable
@Dao
public interface AccountRepository {
    @Select
    public Account findByUsername(String username);

    @Insert
    public Result<Account> save(Account account);

    @Update(sqlFile = true)
    public int passwordChange(String username, String encryptPassword);
}
