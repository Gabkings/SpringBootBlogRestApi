package com.gabkings.blogApp.services;

import com.gabkings.blogApp.payload.PostDto;
import com.gabkings.blogApp.payload.PostResponse;

import java.util.List;

public interface PostService {

    PostDto createPost(PostDto postDto);
    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);
    PostDto getPostById(Long postId);
    PostDto updatePostById(Long postId, PostDto postDto);
    void deletePostById(Long postId);
}
