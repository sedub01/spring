package com.sedub01.blog.repos;

import com.sedub01.blog.models.Post;

import org.springframework.data.repository.CrudRepository;

public interface PostRepositiory extends CrudRepository<Post, Long>{
    
}
