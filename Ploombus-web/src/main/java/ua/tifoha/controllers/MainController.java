package ua.tifoha.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ua.tifoha.search.indexer.BasicCrawlRequest;
import ua.tifoha.search.indexer.crawler.CrawlResponse;
import ua.tifoha.search.searcher.SearchResultRow;
import ua.tifoha.service.IndexService;
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
    private IndexService indexService;

    @RequestMapping("index")
    public ModelAndView indexGet() {
        ModelAndView modelAndView = new ModelAndView("index");
        List<CrawlResponse> taskList = indexService.getAllIndexTasks();
        modelAndView.addObject("taskList", taskList);

        return modelAndView;
    }

    @RequestMapping(value = "index", method = RequestMethod.POST)
    public ModelAndView indexPostDefault(@RequestParam(value = "q") String url) {
        ModelAndView modelAndView = new ModelAndView("index");
        indexService.index(url);
        List<CrawlResponse> taskList = indexService.getAllIndexTasks();
        modelAndView.addObject("taskList", taskList);

        return modelAndView;
    }

    @RequestMapping(value = "index", method = RequestMethod.POST, params = {"q", "maxDepthOfCrawling"})
    public ModelAndView indexPost(@RequestParam(value = "q") String url,
                                  @RequestParam(value = "maxDepthOfCrawling") Integer maxDepthOfCrawling,
                                  @RequestParam(value = "maxOutgoingLinksToFollow", required = false)Integer maxOutgoingLinksToFollow,
                                  @RequestParam(value = "maxPagesToFetch", required = false)Integer maxPagesToFetch) {

        ModelAndView modelAndView = new ModelAndView("index");
        BasicCrawlRequest crawlRequest = new BasicCrawlRequest(url);

        if (maxDepthOfCrawling != null)
            crawlRequest.setMaxDepthOfCrawling(maxDepthOfCrawling);
        if (maxOutgoingLinksToFollow != null)
            crawlRequest.setMaxOutgoingLinksToFollow(maxOutgoingLinksToFollow);
        if (maxPagesToFetch != null)
            crawlRequest.setMaxPagesToFetch(maxPagesToFetch);

        indexService.index(crawlRequest);
        List<CrawlResponse> taskList = indexService.getAllIndexTasks();
        modelAndView.addObject("taskList", taskList);

        return modelAndView;
    }

    @RequestMapping
    public String searchGet() {
        return "search";
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ModelAndView searchPost(@RequestParam(value = "q") String query) {
        ModelAndView modelAndView = new ModelAndView("searchResults");
        modelAndView.addObject("query", query);

        if (query!= null && !query.isEmpty()) {
            List<SearchResultRow> searchResults = searchService.find(query);
            modelAndView.addObject("searchResults", searchResults);
        }

        return modelAndView;
    }

}
