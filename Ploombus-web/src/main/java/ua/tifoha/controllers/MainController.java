package ua.tifoha.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ua.tifoha.search.indexer.BasicCrawlRequest;
import ua.tifoha.search.crawler.CrawlResponse;
import ua.tifoha.search.searcher.BasicSearchQuery;
import ua.tifoha.search.searcher.SearchResult;
import ua.tifoha.service.CrawlerService;
import ua.tifoha.service.SearchService;

import java.util.List;

/**
 * Created by Vitaly on 11.09.2016..
 */
@Controller
@RequestMapping("/")
public class MainController {
    @Autowired
    private SearchService searchService;
    @Autowired
    private CrawlerService crawlerService;

    @RequestMapping("index")
    public ModelAndView indexGet() {
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("crawlRequest", new BasicCrawlRequest());
        List<CrawlResponse> crawlResponseList = crawlerService.getAllIndexTasks();
        modelAndView.addObject("crawlResponseList", crawlResponseList);

        return modelAndView;
    }

    @RequestMapping(value = "index", method = RequestMethod.POST)
    public ModelAndView indexPost(@ModelAttribute BasicCrawlRequest crawlRequest) {

        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("crawlRequest", new BasicCrawlRequest());
        crawlerService.index(crawlRequest);
        List<CrawlResponse> crawlResponseList = crawlerService.getAllIndexTasks();
        modelAndView.addObject("crawlResponseList", crawlResponseList);

        return modelAndView;
    }

    @RequestMapping(value = "index", method = RequestMethod.GET, params = {"stop"})
    public ModelAndView indexStop(@ModelAttribute BasicCrawlRequest crawlRequest) {

        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("crawlRequest", new BasicCrawlRequest());
        crawlerService.shutdown(crawlRequest);
        List<CrawlResponse> crawlResponseList = crawlerService.getAllIndexTasks();
        modelAndView.addObject("crawlResponseList", crawlResponseList);

        return modelAndView;
    }

    @RequestMapping
    public String searchGet() {
        return "search";
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ModelAndView searchPost(@ModelAttribute BasicSearchQuery searchQuery) {
        ModelAndView modelAndView = new ModelAndView("searchResults");
        modelAndView.addObject("queryString", searchQuery.getQueryString());

        if (searchQuery != null && searchQuery.getQueryString()!=null && !searchQuery.getQueryString().isEmpty()) {
            SearchResult searchResults = searchService.find(searchQuery);
            modelAndView.addObject("searchResult", searchResults);
        }

        return modelAndView;
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ModelAndView searchGet(@ModelAttribute BasicSearchQuery searchQuery) {
        ModelAndView modelAndView = new ModelAndView("searchResults");
        modelAndView.addObject("queryString", searchQuery.getQueryString());

        if (searchQuery != null && searchQuery.getQueryString()!=null && !searchQuery.getQueryString().isEmpty()) {
            SearchResult searchResult = searchService.find(searchQuery);
            modelAndView.addObject("searchResult", searchResult);
        }

        return modelAndView;
    }



}
