package com.example.app.scripttemplate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

@Controller
@RequestMapping("script-template")
public class ScriptTemplateController {

    private static final Logger logger = LoggerFactory
            .getLogger(ScriptTemplateController.class);

    @RequestMapping(method = RequestMethod.GET)
    public String home(Locale locale, Model model,HttpServletResponse response) {
        logger.info("Welcome home! The client locale is {}.", locale);

        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG,
                DateFormat.LONG, locale);

        model.addAttribute("title", "Sample title")
                .addAttribute("body", "Sample body");

        // Workaround for https://jira.spring.io/browse/SPR-13379
        response.setContentType("text/html");

        return "script-template/home";
    }

}
