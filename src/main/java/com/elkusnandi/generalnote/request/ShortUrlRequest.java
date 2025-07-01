package com.elkusnandi.generalnote.request;

import lombok.Data;

import java.time.Instant;

@Data
public class ShortUrlRequest {

    public String longUrl;

    public String shortId;

    public Instant expirationDate;

}
