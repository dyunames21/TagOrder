package com.example.tagorder.SMS;


import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Random;

@Controller
public class sendSMS {

    @PostMapping("/api/memberPhone")
    public @ResponseBody void memberPhoneCheck(@RequestParam(value="to") String to) throws CoolsmsException {

        certificationService.certifiedPhoneNumber(to);
    }
}
