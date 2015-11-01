package com.example.app.exception;

import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.*;
import org.terasoluna.gfw.common.exception.SystemException;

import javax.inject.Inject;
import java.util.Locale;

@RequestMapping("exception")
@RestController
public class ExceptionRestController {

    @Inject
    MessageSource messageSource;

    @RequestMapping(path = "message/{messageId}", method = RequestMethod.GET)
    public String get(@PathVariable("messageId") String messageId, @RequestParam(name = "args", required = false) Object[] args, Locale locale) {
        if ("sysError".equalsIgnoreCase(messageId)) {
            throw new SystemException(messageId, "invalid messageId.");
        }
        if ("notFound".equalsIgnoreCase(messageId)) {
            throw new CustomNotFoundException();
        }
        if ("nestedNotFound".equalsIgnoreCase(messageId)) {
            throw new RuntimeException(new CustomNotFoundException());
        }
        if ("error".equalsIgnoreCase(messageId)) {
            throw new Error("error!!");
        }
        if ("memoryError".equalsIgnoreCase(messageId)) {
            throw new OutOfMemoryError("error!!");
        }
        return messageSource.getMessage(messageId, args, messageId, locale);
    }

}
