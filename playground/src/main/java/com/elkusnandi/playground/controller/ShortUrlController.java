package com.elkusnandi.playground.controller;

import com.elkusnandi.common.base.BaseResponse;
import com.elkusnandi.playground.request.ShortUrlRequest;
import com.elkusnandi.playground.response.ShortUrlResponse;
import com.elkusnandi.playground.service.ShortUrlService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Short Url Controller", description = "Handle all endpoint for long to short url converter.")
@RestController
@RequestMapping("/api/short")
public class ShortUrlController {

    private final ShortUrlService shortUrlService;

    @Autowired
    public ShortUrlController(ShortUrlService shortUrlService) {
        this.shortUrlService = shortUrlService;
    }

    @Operation(
            summary = "Create short url given long url",
            description = "Return short url that can be accessed using https://api.malubertanya.com/s/{shortId}"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Successful create short id")
    @PostMapping
    public BaseResponse<ShortUrlResponse> createShortId(@Valid @AuthenticationPrincipal UserDetails userDetails, @RequestBody ShortUrlRequest shortUrlRequest) {
        return new BaseResponse<>(shortUrlService.createShortId(Long.parseLong(userDetails.getUsername()), shortUrlRequest), HttpStatus.CREATED, true, "");
    }

    @Operation(
            summary = "Get short id detail",
            description = "Return short url detail"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Return short id detail")
    @GetMapping("/{shortId}")
    public BaseResponse<ShortUrlResponse> getShortIdDetail(@Valid @AuthenticationPrincipal UserDetails userDetails, @PathVariable String shortId) {
        return new BaseResponse<>(shortUrlService.getShortIdDetail(shortId), HttpStatus.OK, true, "");
    }

}

