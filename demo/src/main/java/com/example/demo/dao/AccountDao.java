package com.example.demo.dao;

import com.example.demo.domain.Account;
import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;

@ConfigAutowireable
@Dao
public interface AccountDao {
    @Select
    Account findByUsername(String username);
}
