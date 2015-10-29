package com.example.app.cros;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.Locale;

@RequestMapping("cros/local")
@Controller
public class CrosLocalAjaxController {

    @Inject
    MessageSource messageSource;

    @CrossOrigin(origins = "http://spring.example.com:8080", maxAge = 3600, exposedHeaders = "b")
    @RequestMapping(path = "message", method = RequestMethod.GET)
    @ResponseBody
    public String get(@RequestParam("messageId") String messageId, @RequestParam(name = "args", required = false) Object[] args, Locale locale) {
        return messageSource.getMessage(messageId, args, messageId, locale);
    }

}
