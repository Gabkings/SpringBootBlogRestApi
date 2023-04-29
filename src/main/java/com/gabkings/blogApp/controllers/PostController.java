package com.gabkings.blogApp.controllers;

import com.gabkings.blogApp.payload.PostDto;
import com.gabkings.blogApp.payload.PostResponse;
import com.gabkings.blogApp.services.PostService;
import com.gabkings.blogApp.utils.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/posts")
public class PostController {

    @Autowired
    PostService postService;

    @PostMapping
    public ResponseEntity<PostDto>  createPost(@RequestBody PostDto postDto
                                               ){
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED) ;
    }

    @GetMapping
    public ResponseEntity<PostResponse> getPosts(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ){
        return new ResponseEntity<>(postService.getAllPosts(pageNo, pageSize, sortBy, sortDir), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity getPostById(@PathVariable("id") Long id){
        return new ResponseEntity(postService.getPostById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity updatePostById(@PathVariable("id") Long id, @RequestBody PostDto post ){
        return new ResponseEntity(postService.updatePostById(id, post), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable("id") Long id){
        postService.deletePostById(id);
        return new ResponseEntity("Post Deleted successfully", HttpStatus.OK);
    }






}
