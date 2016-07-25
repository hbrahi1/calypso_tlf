package com.calypso.web;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@ControllerAdvice
public class GeneralController {

    private static Log logger = LogFactory.getLog(GeneralController.class);

    @RequestMapping("/")
    public String index() {
        return "welcome";
    }

    @RequestMapping(value = "/router")
    public String accessDeniedRouter(@RequestParam("q") String resource) {
        logger.debug("In accessDeniedRouter resource = " + resource);

        return "redirect:/" + resource;
    }

    @RequestMapping(value = "/unauthorized")
    public ModelAndView accessDenied() {
        logger.debug("In accessDenied");

        ModelAndView mav = new ModelAndView();
        mav.addObject("timestamp", new Date());
        mav.setViewName("unauthorized");
        return mav;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(HttpServletRequest req, Exception e) {
        logger.debug("In handleException");

        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", e);
        mav.addObject("timestamp", new Date());
        mav.addObject("url", req.getRequestURL());
        mav.setViewName("exception");
        return mav;
    }

    @RequestMapping(value = "/oups", method = RequestMethod.GET)
    public String triggerException() {
        throw new RuntimeException("Expected: controller used to showcase what " +
                "happens when an exception is thrown");
    }
}
