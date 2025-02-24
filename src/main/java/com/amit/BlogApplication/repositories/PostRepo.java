package com.amit.BlogApplication.repositories;

import com.amit.BlogApplication.entities.Category;
import com.amit.BlogApplication.entities.Posts;
import com.amit.BlogApplication.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepo extends JpaRepository<Posts,Integer> {

    List<Posts> findByUsers(Users users);
    List<Posts> findByCategory(Category category);
}
