package com.example.tagorder.item.Controller;


import com.example.tagorder.dto.AccountDto;
import com.example.tagorder.dto.ItemDto;
import com.example.tagorder.item.entity.Cart;
import com.example.tagorder.item.entity.Item;
import com.example.tagorder.item.entity.member;
import com.example.tagorder.item.repository.memberRepository;
import com.example.tagorder.item.service.JwtService;
import com.example.tagorder.item.service.JwtServiceImpl;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

@RestController
public class AccountController {

    @Autowired
    memberRepository memberRepository;
    @Autowired
    JwtService jwtService;



    @PostMapping("/api/account/join")
    public ResponseEntity join(@RequestBody AccountDto dto
    ){
        member account = new member();

        account.setName(dto.getName());
        account.setPassword(dto.getPassword());
        account.setPhone(dto.getPhone());

        memberRepository.save(account);

        return new ResponseEntity<>(HttpStatus.OK);
    }



    @PostMapping("/api/account/login")
    public ResponseEntity login(@RequestBody Map<String,String> params, HttpServletRequest req, HttpServletResponse res) {
        member member = memberRepository.findByNameAndPassword(params.get("name"), params.get("password"));

        if (member != null) {
            int id = member.getId();
            String token = jwtService.getToken("id",id);
            //id로 jwt 토큰 생성

            Cookie cookie = new Cookie("token",token);
            //해당 토큰으로 새로운 쿠키 생성
            cookie.setHttpOnly(true);
            //http 로만 접근 가능
            cookie.setPath("/");
            cookie.setMaxAge(60*60*24*30);

            res.addCookie(cookie);
            //응답 값으로 해당 id 로 만든 토큰이 포함된 쿠키 전달

            return new ResponseEntity<>(id,HttpStatus.OK);
            //만약 로그인이 제대로 되었다면,
            // 응답값으로 토큰을 만들어 쿠키로 구워 응답값으로 준다.
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @PostMapping("api/account/nologin")
    public ResponseEntity nologin(
            HttpServletRequest req, HttpServletResponse res) {

            HttpSession session = req.getSession();
            Random random = new Random();
            int sessionID = random.nextInt(1000000);
            String token = jwtService.getToken("id",sessionID);
            //sessionID로 jwt 토큰 생성

            Cookie cookie = new Cookie("token",token);
            //해당 토큰으로 새로운 쿠키 생성
            cookie.setHttpOnly(true);
            //http 로만 접근 가능
            cookie.setPath("/");
            cookie.setMaxAge(60*60*24*30);

            res.addCookie(cookie);
            //응답 값으로 해당 id 로 만든 토큰이 포함된 쿠키 전달

            return new ResponseEntity<>(sessionID,HttpStatus.OK);
            //만약 로그인이 제대로 되었다면,
            // 응답값으로 토큰을 만들어 쿠키로 구워 응답값으로 준다.

    }

    @GetMapping("/api/account/check")
    public ResponseEntity check(@CookieValue(value = "token", required = false) String token){
        Claims claims = jwtService.getClaims(token) ;//토큰값 가져오기

        if(claims !=null) {
            int id = Integer.parseInt(claims.get("id").toString());
            return new ResponseEntity<>(id, HttpStatus.OK);
            //만약 claims 값이 null 이아니면 id값 반환
        }
        return new ResponseEntity<>(null,HttpStatus.OK);
        //아니면 null 값 반환

    }


    @GetMapping("/api/account/home/check")
    public ResponseEntity homeCheck(
            @CookieValue(value = "token", required = false) String token){
        Claims claims = jwtService.getClaims(token) ;//토큰값 가져오기

        if(claims !=null) {
            int id = Integer.parseInt(claims.get("id").toString());
            return new ResponseEntity<>(id, HttpStatus.OK);
            //만약 claims 값이 null 이아니면 id값 반환
        }
        return new ResponseEntity<>(null,HttpStatus.OK);
        //아니면 null 값 반환

    }

    //@PostMapping("/api/account/newCookie")
    //public ResponseEntity newBieMakeCookie(@RequestBody Map<String,String> params, HttpServletResponse res) {
        //member member = memberRepository.findByNameAndPassword(params.get("name"), params.get("password"))

     //{
        //if (member != null) {
            //int id = member.getId(); 세션 받아오기
            //String token = jwtService.getToken("id",id); 아이디가 아니라 세션으로
            //id로 jwt 토큰 생성

            //Cookie cookie = new Cookie("token",token);
              //세션으로 만든 토큰으로 쿠키생성
            //해당 토큰으로 새로운 쿠키 생성
            //cookie.setHttpOnly(true);
            //http 로만 접근 가능
            //cookie.setPath("/");

            //res.addCookie(cookie);
            //응답 값으로 해당 id 로 만든 토큰이 포함된 쿠키 전달

            //return new ResponseEntity<>(id,HttpStatus.OK);
            //만약 로그인이 제대로 되었다면,
            // 응답값으로 토큰을 만들어 쿠키로 구워 응답값으로 준다.
        //}
        //return new ResponseEntity<>(HttpStatus.OK);
    //}



    @PostMapping("/api/account/logout")
    public ResponseEntity logout(HttpServletResponse res) {
        Cookie cookie = new Cookie("token", null);
        cookie.setPath("/");
        cookie.setMaxAge(0);

        res.addCookie(cookie);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
