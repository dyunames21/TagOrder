package com.example.tagorder.item.service;

import io.jsonwebtoken.Claims;

public interface JwtService {
    String getToken(String key, Object value);

    Claims getClaims(String token);

    boolean isValid(String token);
    //인자로 받은 토큰이 문제가없는지판단

    int getId(String token);
    //토큰으로부터 Id값 받아오기
}
