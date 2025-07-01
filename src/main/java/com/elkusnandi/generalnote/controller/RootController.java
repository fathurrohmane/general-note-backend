package com.elkusnandi.generalnote.controller;

import com.elkusnandi.generalnote.service.ShortUrlService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/s")
public class RootController {
    private final ShortUrlService shortUrlService;

    public RootController(ShortUrlService shortUrlService) {
        this.shortUrlService = shortUrlService;
    }

    @GetMapping("/{shortId}")
    public RedirectView redirectShortUrl(@PathVariable String shortId) {
        return new RedirectView(shortUrlService.redirectToLongUrl(shortId));
    }

}
