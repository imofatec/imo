package com.imo.backend.modules.comment.repositories;

import com.imo.backend.modules.comment.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CommentRepository
    extends MongoRepository<Comment, String>, CustomCommentRepository {

}
