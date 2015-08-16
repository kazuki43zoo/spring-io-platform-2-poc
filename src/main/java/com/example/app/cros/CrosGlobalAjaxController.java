package com.example.app.cros;

import org.springframework.context.MessageSource;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.Locale;

@RequestMapping("cros/global")
@Controller
public class CrosGlobalAjaxController {

    @Inject
    MessageSource messageSource;

    @RequestMapping(path = "message", method = RequestMethod.GET)
    @ResponseBody
    public String get(@RequestParam("messageId") String messageId, @RequestParam(name = "args", required = false) Object[] args, Locale locale) {
        return messageSource.getMessage(messageId, args, messageId, locale);
    }

}
