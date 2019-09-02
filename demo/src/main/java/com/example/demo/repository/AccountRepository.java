package com.example.demo.repository;

import com.example.demo.domain.Account;
import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;

@ConfigAutowireable
@Dao
public interface AccountRepository {
    @Select
    public Account findByUsername(String username);
}
