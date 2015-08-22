package com.example.app.async;

import org.springframework.core.task.AsyncListenableTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.async.DeferredResult;

import javax.inject.Inject;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@RequestMapping("async")
@Controller
public class AsyncController {

    @Inject
    AsyncHelper asyncHelper;

    @Inject
    AsyncListenableTaskExecutor taskExecutor;

    @RequestMapping(path = "callable", method = RequestMethod.GET)
    public Callable<String> callable(@RequestParam(name = "wait", defaultValue = "0") long wait, Model model, Locale locale) {
        return () -> {

            if (wait == 999) {
                throw new NullPointerException("error.");
            }

            TimeUnit.SECONDS.sleep(wait);

            Date date = new Date();
            DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG,
                    DateFormat.LONG, locale);

            String formattedDate = dateFormat.format(date);

            model.addAttribute("serverTime", formattedDate);

            return "welcome/home";
        };
    }

    @RequestMapping(path = "deferred", method = RequestMethod.GET)
    public DeferredResult<String> deferred(@RequestParam(name = "wait", defaultValue = "0") long wait, Model model, Locale locale) throws InterruptedException {

        DeferredResult<String> deferredResult = new DeferredResult<>(null, "common/error/timeoutError");
        asyncHelper.deferred(wait, deferredResult, model, locale);
        return deferredResult;
    }


    @RequestMapping(path = "listenableFuture", method = RequestMethod.GET)
    public ListenableFuture<String> listenableFuture(@RequestParam(name = "wait", defaultValue = "0") long wait, Model model, Locale locale) throws InterruptedException {
        return taskExecutor.submitListenable(() -> {

            if (wait == 999) {
                throw new NullPointerException("error.");
            }

            TimeUnit.SECONDS.sleep(wait);


            Date date = new Date();
            DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG,
                    DateFormat.LONG, locale);

            String formattedDate = dateFormat.format(date);

            model.addAttribute("serverTime", formattedDate);

            return "welcome/home";
        });
    }

    @RequestMapping(path = "completableFuture", method = RequestMethod.GET)
    public CompletableFuture<String> completableFuture(@RequestParam(name = "wait", defaultValue = "0") long wait, Model model, Locale locale) throws InterruptedException {
        return CompletableFuture.supplyAsync(() -> {
            Date date = new Date();
            DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG,
                    DateFormat.LONG, locale);

            String formattedDate = dateFormat.format(date);

            model.addAttribute("serverTime", formattedDate);

            if (wait == 999) {
                throw new NullPointerException("error.");
            }

            try {
                TimeUnit.SECONDS.sleep(wait);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return "welcome/home";
        });
    }

}
