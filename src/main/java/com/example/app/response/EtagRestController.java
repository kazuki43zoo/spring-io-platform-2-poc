package com.example.app.response;

import org.springframework.context.MessageSource;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

@RequestMapping("etag")
@RestController
public class EtagRestController {

    @Inject
    MessageSource messageSource;

    @RequestMapping(path = "message/{messageId}", method = RequestMethod.GET)
    public ResponseEntity<String> get(@PathVariable("messageId") String messageId, @RequestParam(name = "args", required = false) Object[] args, Locale locale) {
        String message = messageSource.getMessage(messageId, args, messageId, locale);
        return ResponseEntity
                .ok()
                .eTag(new StringBuffer().append("\"0").append(DigestUtils.md5DigestAsHex(message.getBytes(StandardCharsets.UTF_8))).append('"').toString())
                .body(message);
    }

}
