package com.fazlyev.webblog.repository;

import com.fazlyev.webblog.models.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post, Long> {
}
