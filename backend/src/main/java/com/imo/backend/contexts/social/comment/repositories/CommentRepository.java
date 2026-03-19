package com.imo.backend.contexts.social.comment.repositories;

import com.imo.backend.contexts.social.comment.Comment;
import com.imo.backend.contexts.common.exceptions.custom.NotFoundException;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CommentRepository
    extends MongoRepository<Comment, String>, CustomCommentRepository {

    default Comment findByIdOrThrow(String id) {
        return this.findById(id)
            .orElseThrow(() -> new NotFoundException("Comentário não encontrado"));
    }
}
