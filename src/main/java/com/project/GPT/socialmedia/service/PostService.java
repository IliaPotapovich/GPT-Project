package com.project.GPT.socialmedia.service;

import com.project.GPT.socialmedia.model.Post;
import com.project.GPT.socialmedia.model.User;
import com.project.GPT.socialmedia.repository.PostRepository;
import com.project.GPT.socialmedia.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public Post savePost(Post post) {
        return postRepository.save(post);
    }

    public Post getPostById(Long postId) {
        return postRepository.findById(postId).orElse(null);
    }

    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }
}
