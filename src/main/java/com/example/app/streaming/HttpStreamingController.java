package com.example.app.streaming;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.inject.Inject;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

@RequestMapping("streaming")
@Controller
public class HttpStreamingController {

    @Inject
    HttpStreamingHelper httpStreamingHelper;

    @RequestMapping(path = "bodyEmitter", method = RequestMethod.GET)
    public ResponseBodyEmitter body(@RequestParam(name = "wait", defaultValue = "0") long wait, Model model, Locale locale) throws IOException, InterruptedException {
        ResponseBodyEmitter emitter = new ResponseBodyEmitter();
        emitter.onTimeout(() -> {
            try {
                emitter.send("Timeout!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        httpStreamingHelper.body(wait, emitter);
        return emitter;
    }

    @RequestMapping(path = "sseEmitter", method = RequestMethod.GET)
    public SseEmitter sse(@RequestParam(name = "wait", defaultValue = "0") long wait, Model model, Locale locale) throws IOException, InterruptedException {
        SseEmitter emitter = new SseEmitter();
        emitter.onTimeout(() -> {
            try {
                emitter.send("Timeout!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        httpStreamingHelper.sse(wait, emitter);
        return emitter;
    }

    @RequestMapping(path = "directBody", method = RequestMethod.GET)
    public StreamingResponseBody directBody(@RequestParam(name = "wait", defaultValue = "0") long wait, Model model, Locale locale) throws IOException, InterruptedException {
        return new StreamingResponseBody() {
            @Override
            public void writeTo(OutputStream outputStream) throws IOException {
                outputStream.write("Hello!!\r\n".getBytes(StandardCharsets.UTF_8));
                outputStream.write("Hello again!!\r\n".getBytes(StandardCharsets.UTF_8));
                if(wait == 999){
                    throw new NullPointerException("error.");
                }
                try {
                    TimeUnit.SECONDS.sleep(wait);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

    }

}
