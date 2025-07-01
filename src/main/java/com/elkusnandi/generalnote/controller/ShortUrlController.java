package com.elkusnandi.generalnote.controller;

import com.elkusnandi.generalnote.common.base.BaseResponse;
import com.elkusnandi.generalnote.request.ShortUrlRequest;
import com.elkusnandi.generalnote.response.ShortUrlResponse;
import com.elkusnandi.generalnote.service.ShortUrlService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/short")
public class ShortUrlController {

    private final ShortUrlService shortUrlService;

    @Autowired
    public ShortUrlController(ShortUrlService shortUrlService) {
        this.shortUrlService = shortUrlService;
    }

    @PostMapping
    public BaseResponse<ShortUrlResponse> createShortId(@Valid @AuthenticationPrincipal UserDetails userDetails, @RequestBody ShortUrlRequest shortUrlRequest) {
        return new BaseResponse<>(shortUrlService.createShortId(Long.parseLong(userDetails.getUsername()), shortUrlRequest), HttpStatus.CREATED, true, "");
    }

    @GetMapping("/{shortId}")
    public BaseResponse<ShortUrlResponse> getShortIdDetail(@Valid @AuthenticationPrincipal UserDetails userDetails, @PathVariable String shortId) {
        return new BaseResponse<>(shortUrlService.getShortIdDetail(shortId), HttpStatus.OK, true, "");
    }

}

