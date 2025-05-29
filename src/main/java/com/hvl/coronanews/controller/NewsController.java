package com.hvl.coronanews.controller;

import com.hvl.coronanews.dto.NewsRequestDTO;
import com.hvl.coronanews.model.News;
import com.hvl.coronanews.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/corona/news")
public class NewsController {

    @Autowired
    private NewsService newsService;

    // Create News
    @PostMapping
    public News createNews(@RequestBody NewsRequestDTO requestDTO) {
        return newsService.createNews(requestDTO.getContent());
    }

    // Get All News
    @GetMapping
    public List<News> getAllNews() {
        return newsService.getAllNews();
    }

    @GetMapping("/ping")
    public void checkService() {
        System.out.println("Pong");
    }
}
