package com.ssafy.miro.comment.application;

import com.ssafy.miro.comment.domain.Comment;
import com.ssafy.miro.comment.domain.repository.CommentRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CommentServiceTest {
    @Autowired
    private CommentRepository repository;


    @Test
    void addTest() {
        Comment comment = new Comment();

        System.out.println(repository.findAll());
    }
}