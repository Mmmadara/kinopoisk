package com.example.demo.facade;

import com.example.demo.dto.CommentDTO;
import com.example.demo.entities.Comment;
import org.springframework.stereotype.Component;

@Component
public class CommentFacade {

    public CommentDTO commentDTO(Comment comment){
        CommentDTO commentDTO = new CommentDTO();

        commentDTO.setId(commentDTO.getId());
        commentDTO.setUsername(commentDTO.getUsername());
        commentDTO.setMessage(commentDTO.getMessage());
        return commentDTO;
    }
}
