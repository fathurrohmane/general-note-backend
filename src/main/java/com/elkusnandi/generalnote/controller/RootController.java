package com.elkusnandi.generalnote.controller;

import com.elkusnandi.generalnote.common.base.BaseResponse;
import com.elkusnandi.generalnote.service.ShortUrlService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@Tag(name = "Short Url Redirect Controller", description = "Handle redirection of short id")
@RestController
@RequestMapping("/s")
public class RootController {
    private final ShortUrlService shortUrlService;

    public RootController(ShortUrlService shortUrlService) {
        this.shortUrlService = shortUrlService;
    }

    @Operation(
            summary = "Get long url given short id and redirect to it"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Redirect to long url", content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = BaseResponse.class),
            examples = @ExampleObject(
                    value = ""
            )
    ))
    @GetMapping("/{shortId}")
    public RedirectView redirectShortUrl(@PathVariable String shortId) {
        return new RedirectView(shortUrlService.redirectToLongUrl(shortId));
    }

}
