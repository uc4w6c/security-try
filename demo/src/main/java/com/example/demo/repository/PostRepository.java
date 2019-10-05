package com.example.demo.repository;

import com.example.demo.domain.Post;
import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.Result;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@ConfigAutowireable
@Dao
public interface PostRepository {
    @Select
    public List<Post> findAll();

    @Select
    public List<Post> findByBody(String queryBody);

    @Insert
    public Result<Post> save(Post post);
}
