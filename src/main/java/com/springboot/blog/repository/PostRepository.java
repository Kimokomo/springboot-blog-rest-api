package com.springboot.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.springboot.blog.entity.Post;

// @Repository  we dont need this Annotation because the JPARepository interface has an implementation class called SimpleJpaRepository and it internally annotated with @Repository annotation
// @Transactional we dont need to add @Transactional annotation because SimpleJpaRepository has it by default
public interface PostRepository extends JpaRepository<Post, Long> {

  // we get the CRUD methods out of the box
}



