package com.example.app.json;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.inject.Inject;
import java.util.Collections;
import java.util.Locale;
import java.util.Map;

@RequestMapping("json")
@Controller
public class JsonRestController {

    @Inject
    MessageSource messageSource;

    @RequestMapping(path = "message/{messageId}", method = RequestMethod.GET)
    public View get(@PathVariable("messageId") String messageId, @RequestParam(name = "args", required = false) Object[] args, Locale locale, Model model) {
        MappingJackson2JsonView view = new MappingJackson2JsonView();
        view.setPrefixJson(true);
        view.setModelKey("jsonModel");
        view.setExtractValueFromSingleKeyModel(true);
        model.addAttribute("jsonModel",Collections.singletonMap("message", messageSource.getMessage(messageId, args, messageId, locale)));
        return view;
    }

}
