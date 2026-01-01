package com.example.demo.dto;

import com.example.demo.entity.Role;

import java.time.OffsetDateTime;
import java.util.Set;
import java.util.stream.Collectors;

public class ResponseUserDto {

    private final String username;
    private OffsetDateTime createDate;

    public long getUserId() {
        return userId;
    }

    private final Set<String> userRoles;
    private final long userId;

    public ResponseUserDto(
            long userId,
                   String username,
                   OffsetDateTime createDate,
                   Set<Role> userRoles) {

        this.userId = userId;
        this.username = username;
        this.createDate = createDate;
        this.userRoles = userRoles.stream().map(Role::getRoleName).collect(Collectors.toSet());
    }

    public ResponseUserDto(
            long userId,
            String username,
            Set<Role> userRoles

    ){
        this.username = username;
        this.userRoles = userRoles.stream().map(Role::getRoleName).collect(Collectors.toSet());
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public OffsetDateTime getCreateDate() {
        return createDate;
    }

    public Set<String> getUserRoles() {
        return userRoles;
    }
}
