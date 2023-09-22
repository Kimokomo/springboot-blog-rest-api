package com.springboot.blog.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.service.CommentService;

@RestController
@RequestMapping("/api/")
public class CommentController {

  private CommentService commentService;

  public CommentController(CommentService commentService) {
    this.commentService = commentService;
  }

  // create new comment for post with id = postId
  @PostMapping("posts/{postId}/comments")
  public ResponseEntity<CommentDto> createComment(@PathVariable(value = "postId") long postId, @RequestBody CommentDto commentDto) {
    return new ResponseEntity<>(commentService.createComment(postId, commentDto), HttpStatus.CREATED);
  }

  // Get all comments which belongs to post with id = postId
  @GetMapping("/posts/{postId}/comments")
  public List<CommentDto> getCommentsByPostId(@PathVariable(value = "postId") Long postId) {
    return commentService.getCommentByPostId(postId);
  }

  // Get comment by id if it belongs to post with id = postId
  @GetMapping("/posts/{postId}/comments/{id}")
  public ResponseEntity<CommentDto> getCommentById(@PathVariable(value = "postId") Long postId, @PathVariable(value = "id") Long commentId) {
    CommentDto commentDto = commentService.getCommentById(postId, commentId);
    return new ResponseEntity<>(commentDto, HttpStatus.OK);
  }

  // Update comment by id if it belongs to post with id = postId
  @PutMapping("/posts/{postId}/comments/{id}")
  public ResponseEntity<CommentDto> updateComment(@PathVariable(value = "postId") Long postId, @PathVariable(value = "id") Long commentId, @RequestBody CommentDto commentDto) {
    CommentDto updatedComment = commentService.updateComment(postId, commentId, commentDto);
    return new ResponseEntity<>(updatedComment, HttpStatus.OK);
  }

  // Delete comment by id if it belongs to post with id = postId
  @DeleteMapping("/posts/{postId}/comments/{id}")
  public ResponseEntity<String> deleteComment(@PathVariable(value = "postId") Long postId, @PathVariable(value = "id") Long commentId) {
    commentService.deleteComment(postId, commentId);
    return new ResponseEntity<>("Comment deleted successfully", HttpStatus.OK);
  }

}
