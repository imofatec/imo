package com.imo.backend.models.user.dtos;

import com.imo.backend.models.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class NoPasswordUser {
    private String id;

    private String username;

    private String email;

    public static NoPasswordUser fromUser(User user) {
        return new NoPasswordUser(
                user.getId(),
                user.getUsername(),
                user.getEmail()
        );
    }
}
