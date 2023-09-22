package com.springboot.blog.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.service.PostService;
import com.springboot.blog.utils.AppConstants;

@RestController
@RequestMapping("/api/posts")
public class PostController {

  private PostService postService;

  //@Autowired
  public PostController(PostService postService) {
    this.postService = postService;
  }

  // create blog post rest api
  @PostMapping
  public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto) {
    return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
  }

  // get all posts rest api
  @GetMapping
  public PostResponse getAllPosts(
      @RequestParam(name = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,required = false) int pageNo,
      @RequestParam(name = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
      @RequestParam(name = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
      @RequestParam(name = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir){
    return postService.getAllPosts(pageNo,pageSize,sortBy,sortDir);
  }

  // get post by id rest api
  @GetMapping("/{id}")
  public ResponseEntity<PostDto> getPostById(@PathVariable(name = "id") long id) {
    return ResponseEntity.ok(postService.getPostById(id));
  }

  // update post by id rest api
  @PutMapping("/{id}")
  public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable(name = "id") long id) {
    PostDto postResponse = postService.updatePost(postDto, id);
    return new ResponseEntity<>(postResponse, HttpStatus.OK);
  }

  // delete post rest api
  @DeleteMapping("/{id}")
  public ResponseEntity<String> deletePost(@PathVariable(name = "id") long id) {
    postService.deletePostById(id);
    return new ResponseEntity<>("Post entity deleted succesfully.", HttpStatus.OK);
  }

}