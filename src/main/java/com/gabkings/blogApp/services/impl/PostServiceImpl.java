package com.gabkings.blogApp.services.impl;

import com.gabkings.blogApp.exceptions.ResourceNotFoundException;
import com.gabkings.blogApp.models.Post;
import com.gabkings.blogApp.payload.PostDto;
import com.gabkings.blogApp.payload.PostResponse;
import com.gabkings.blogApp.repositories.PostRepository;
import com.gabkings.blogApp.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;

    private ModelMapper mapper;


    public PostServiceImpl(PostRepository postRepository, ModelMapper mapper) {
        this.postRepository = postRepository;
        this.mapper = mapper;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        Post post = getPostEntity(postDto);
        postRepository.save(post);

        PostDto postResponse = getPostDto(post);

        return  postResponse;
    }

    private Post getPostEntity(PostDto postDto) {
        Post post = mapToEntity(postDto);
//        post.setTitle(postDto.getTitle());
//        post.setContent(postDto.getContent());
//        post.setDescription(postDto.getDescription());
//        post.setTitle(postDto.getTitle());
        return post;
    }

    private PostDto getPostDto(Post post) {
        PostDto postResponse = mapToDTO(post);


        return postResponse;
    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        PageRequest pageable =  PageRequest.of(pageNo, pageSize, sort);

        Page<Post> page = postRepository.findAll(pageable) ;
        List<Post> pageContent = page.getContent();

        PostResponse postResponse = new PostResponse();

        postResponse.setContent(pageContent.stream().map(this::getPostDto).collect(Collectors.toList()));
        postResponse.setPageNo(page.getNumber());
        postResponse.setPageSize(page.getSize());
        postResponse.setTotalElements(page.getTotalElements());
        postResponse.setLast(page.isLast());

        return postResponse;

    }

    @Override
    public PostDto getPostById(Long postId) {
        return postRepository.findById(postId).map(post -> getPostDto(post)).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
    }

    @Override
    public PostDto updatePostById(Long postId, PostDto postDto) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        post.setDescription(postDto.getDescription());
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());

        Post updatedPost = postRepository.save(post);

        return getPostDto(updatedPost);
    }

    @Override
    public void deletePostById(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        postRepository.delete(post);
    }

    // convert Entity into DTO
    private PostDto mapToDTO(Post post){
        PostDto postDto = mapper.map(post, PostDto.class);
//        PostDto postDto = new PostDto();
//        postDto.setId(post.getId());
//        postDto.setTitle(post.getTitle());
//        postDto.setDescription(post.getDescription());
//        postDto.setContent(post.getContent());
        return postDto;
    }

    // convert DTO to entity
    private Post mapToEntity(PostDto postDto){
        Post post = mapper.map(postDto, Post.class);
//        Post post = new Post();
//        post.setTitle(postDto.getTitle());
//        post.setDescription(postDto.getDescription());
//        post.setContent(postDto.getContent());
        return post;
    }


}
