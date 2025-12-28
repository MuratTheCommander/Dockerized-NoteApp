package com.example.demo.dto;

public class AuthDto {

        private ResponseUserDto responseUserDto;

        private String accessToken;

        public AuthDto(ResponseUserDto responseUserDto,String accessToken){
            this.responseUserDto = responseUserDto;
            this.accessToken = accessToken;
        }

    public ResponseUserDto getResponseUserDto() {
        return responseUserDto;
    }

    public void setResponseUserDto(ResponseUserDto responseUserDto) {
        this.responseUserDto = responseUserDto;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
