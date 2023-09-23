package com.springboot.blog.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;

@Service // while component scan this tells spring that that is a service class
public class PostServiceImpl implements PostService {

  private PostRepository postRepository;
  private ModelMapper mapper;

  //@Autowired  // From Spring 4.3 onward, if a class is configured as a spring bean and it has only one constructor then we dont need @Autowired annotation
  public PostServiceImpl(PostRepository postRepository, ModelMapper mapper) {
    this.postRepository = postRepository;
    this.mapper = mapper;
  }

  @Override
  public PostDto createPost(PostDto postDto) {

    // convert DTO to entity
    Post post = mapToEntity(postDto);

    // save to Database
    Post newPost = postRepository.save(post);

    // convert entity to DTO
    PostDto postResponse = mapToDTO(newPost);

    return postResponse;
  }

  @Override
  public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {

    Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

    // create Pageable instance
    Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

    Page<Post> posts = postRepository.findAll(pageable);

    // get the content of the page from Page object and save it into a list
    List<Post> listOfPosts = posts.getContent();

    // we need to convert this List of Posts into a list of Post dto's
    List<PostDto> content = listOfPosts.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());

    PostResponse postResponse = new PostResponse();
    postResponse.setContent(content);
    postResponse.setPageNo(posts.getNumber());
    postResponse.setPageSize(posts.getSize());
    postResponse.setTotalElements(posts.getTotalElements());
    postResponse.setTotalPages(posts.getTotalPages());
    postResponse.setLast(posts.isLast());
    return postResponse;
  }

  @Override
  public PostDto getPostById(long id) {
    Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
    return mapToDTO(post);
  }

  @Override
  public PostDto updatePost(PostDto postDto, long id) {
    // get post by id from the database
    Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

    // update post Entity
    post.setTitle(postDto.getTitle());
    post.setDescription(postDto.getDescription());
    post.setContent(postDto.getContent());

    // save into database
    Post updatedPost = postRepository.save(post);

    // return the updated Post to the Controller Layer
    return mapToDTO(updatedPost);

  }

  @Override
  public void deletePostById(long id) {
    // get post by id from the database
    Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
    postRepository.delete(post);
  }

  // private method to convert Entity into DTO
  private PostDto mapToDTO(Post post) {

    PostDto postDto = mapper.map(post,PostDto.class);

 /*   PostDto postDto = new PostDto();
    postDto.setId(post.getId());
    postDto.setTitle(post.getTitle());
    postDto.setDescription(post.getDescription());
    postDto.setContent(post.getContent());*/
    return postDto;
  }

  // private method to convert DTO to entity
  private Post mapToEntity(PostDto postDto) {
    Post post = mapper.map(postDto,Post.class);
//    Post post = new Post();
//    post.setTitle(postDto.getTitle());
//    post.setDescription(postDto.getDescription());
//    post.setContent(postDto.getContent());
    return post;
  }
}
