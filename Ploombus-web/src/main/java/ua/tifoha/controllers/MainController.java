package ua.tifoha.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;

/**
 * Created by Vitaly on 03.09.2016.
 */
@Controller
@RequestMapping("/")
public class MainController {
    @RequestMapping("index")
    public String indexGet() {
        return "index";
    }

    @RequestMapping(value = "index", method = RequestMethod.POST)
    public String indexPost(@RequestParam(value = "q") String url) {
        return "index";
    }

    @RequestMapping
    public String searchGet() {
        return "search";
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ModelAndView searchPost(@RequestParam(value = "q") String query) {
        ModelAndView modelAndView = new ModelAndView("searchResults");
        modelAndView.addObject("query", query);

        return modelAndView;
    }

}
