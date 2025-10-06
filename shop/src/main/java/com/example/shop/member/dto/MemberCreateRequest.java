package com.example.shop.member.dto;

// loginId, password, phoneNumber, address

import lombok.Getter;

@Getter // 클래스의 모든 필드에 대해 getter 메서드 자동 생성
public class MemberCreateRequest {

    private String loginId;
    private String password;
    private String phoneNumber;
    private String address;

    public MemberCreateRequest(String loginId, String password, String phoneNumber, String address) {
        this.loginId = loginId;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }
}
