package com.jiac.user.request;

import lombok.Data;

/**
 * FileName: FindPasswordRequest
 * Author: Jiac
 * Date: 2022/3/27 10:59
 */
@Data
public class FindPasswordRequest {
    private String username;
    private String email;
    private String code;

    public static FindPasswordRequest of(String username, String email, String code) {
        FindPasswordRequest request = new FindPasswordRequest();
        request.setUsername(username);
        request.setEmail(email);
        request.setCode(code);
        return request;
    }
}
