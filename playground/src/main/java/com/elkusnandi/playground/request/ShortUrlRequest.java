package com.elkusnandi.playground.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.Instant;

@Data
public class ShortUrlRequest {

    @NotBlank
    @Pattern(regexp = "https?://(www\\.)?[a-zA-Z0-9\\-]+\\.[a-zA-Z]{2,}(\\S*)?\n", message = "Not valid url")
    public String longUrl;

    @Size(min = 6, max = 100)
    @Pattern(regexp = "^\\S*$", message = "Whitespace is not allowed")
    public String shortId;

    public Instant expirationDate;

}

