package com.example.tagorder.item.Controller;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;

@Slf4j
@RestController
public class PGcontroller {

    private IamportClient api;

    public PGcontroller() {
        this.api = new IamportClient("3601585342577849", "ce9def754d5d0ed41b86293d1cbc0451cde2fc05de47fffa12414b379ac460f9aa52cb82fd81232f");
    }


    @ResponseBody
    @RequestMapping(value="/api/verify/{imp_uid}", method = RequestMethod.POST)
    public IamportResponse<Payment> paymentByImpUid(
            Model model
            , Locale locale
            , HttpSession session
            ,@PathVariable(value= "imp_uid") String imp_uid)
            throws IamportResponseException, IOException{

        log.info("paymentByImpUid 진입");

        return api.paymentByImpUid(imp_uid);
    }

}

