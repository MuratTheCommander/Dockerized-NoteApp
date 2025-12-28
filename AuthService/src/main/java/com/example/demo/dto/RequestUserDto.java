package com.example.demo.dto;

public class RequestUserDto
{
    private String userName;

    private String password;

    public RequestUserDto(String userName,String password){
        this.userName = userName;
        this.password = password;
    }

    public RequestUserDto() {
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}
