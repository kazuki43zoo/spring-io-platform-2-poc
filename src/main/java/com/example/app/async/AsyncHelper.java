package com.example.app.async;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.context.request.async.DeferredResult;
import org.terasoluna.gfw.common.exception.BusinessException;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

@Component
public class AsyncHelper {

    @Async
    public void deferred(long wait,DeferredResult<String> deferredResult, Model model, Locale locale) throws InterruptedException {
        Date date = new Date();
        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG,
                DateFormat.LONG, locale);

        String formattedDate = dateFormat.format(date);

        model.addAttribute("serverTime", formattedDate);

        if(wait == 999){
            deferredResult.setErrorResult(new NullPointerException("error."));
            return;
        }

        TimeUnit.SECONDS.sleep(wait);

        deferredResult.setErrorResult("welcome/home");

    }

}
