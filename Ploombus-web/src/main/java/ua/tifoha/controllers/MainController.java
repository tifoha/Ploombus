package ua.tifoha.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.annotation.ApplicationScope;
import org.springframework.web.servlet.ModelAndView;
import ua.tifoha.core.SearchEngine;
import ua.tifoha.core.SearchResult;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Vitaly on 03.09.2016.
 */
@Controller
@RequestMapping("/")
public class MainController {
    @Autowired
    private SearchEngine searchEngine;

    @RequestMapping("index")
    public String indexGet() {
        return "index";
    }

    @RequestMapping(value = "index", method = RequestMethod.POST)
    public String indexPost(@RequestParam(value = "q") String url) {
//        searchEngine.index();
        return "index";
    }

    @RequestMapping
    public String searchGet() {
        return "search";
    }

    @RequestMapping(value = "search", method = RequestMethod.POST)
    public ModelAndView searchPost(@RequestParam(value = "q") String query) {
        ModelAndView modelAndView = new ModelAndView("searchResults");
        modelAndView.addObject("query", query);
        List<SearchResult> searchResults = searchEngine.find(query);
        modelAndView.addObject("searchResults", searchResults);

        return modelAndView;
    }

}
