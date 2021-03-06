package com.Gongdae9.user.dto;

import lombok.Data;

@Data
public class SignupRequestDto {
    private String name;
    private String phoneNum;
    private String nickName;
    private String accountId;
    private String password;

    public SignupRequestDto(String name, String phoneNum, String nickName,
        String accountId, String password) {
        this.name = name;
        this.phoneNum = phoneNum;
        this.nickName = nickName;
        this.accountId = accountId;
        this.password = password;
    }
}
