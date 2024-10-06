package com.imo.backend.models.user.repositories;

import com.imo.backend.exceptions.custom.NotFoundException;
import com.imo.backend.models.user.User;
import com.imo.backend.models.user.dtos.FieldsToUpdateUser;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.AbstractMap;
import java.util.stream.Stream;

@Repository
public class CustomUserRepositoryImpl implements CustomUserRepository {

    private final MongoTemplate mongoTemplate;
    private final PasswordEncoder passwordEncoder;

    CustomUserRepositoryImpl(MongoTemplate mongoTemplate, PasswordEncoder passwordEncoder) {
        this.mongoTemplate = mongoTemplate;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void updateUser(String id, FieldsToUpdateUser fieldsToUpdateUser) {
        Query query = new Query(Criteria.where("id").is(id));
        Update update = new Update();

        User existingUser = mongoTemplate.findOne(query, User.class);

        if (existingUser == null) {
            throw new NotFoundException("Usuário não encontrado");
        }

        Stream.of(
                new AbstractMap.SimpleEntry<>("username",
                        fieldsToUpdateUser.getUsername() != null && !fieldsToUpdateUser.getUsername().isEmpty()
                                ? fieldsToUpdateUser.getUsername()
                                : existingUser.getUsername()),
                new AbstractMap.SimpleEntry<>("password",
                        fieldsToUpdateUser.getPassword() != null && !fieldsToUpdateUser.getPassword().isEmpty()
                                ? passwordEncoder.encode(fieldsToUpdateUser.getPassword())
                                : existingUser.getPassword()),
                new AbstractMap.SimpleEntry<>("email",
                        fieldsToUpdateUser.getEmail() != null && !fieldsToUpdateUser.getEmail().isEmpty()
                                ? fieldsToUpdateUser.getEmail()
                                : existingUser.getEmail())
        ).forEach(entry -> update.set(entry.getKey(), entry.getValue()));

        mongoTemplate.updateFirst(query, update, User.class);
    }
}
