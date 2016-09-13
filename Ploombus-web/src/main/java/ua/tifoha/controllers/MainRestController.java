package ua.tifoha.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Vitaly on 11.09.2016..
 */
@RestController
@RequestMapping("api/v1/{format}/")
public class MainRestController {
    @RequestMapping(value = "ua/tifoha/search", method = RequestMethod.GET)
    public List<String> search() {
        return Arrays.asList("asdf sd f asdf a sdf sdf".split("\\W+"));
    }
}

