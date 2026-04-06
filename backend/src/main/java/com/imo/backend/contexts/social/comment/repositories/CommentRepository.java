package com.imo.backend.contexts.social.comment.repositories;

import com.imo.backend.contexts.social.comment.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CommentRepository
    extends MongoRepository<Comment, String>, CustomCommentRepository {}
