package com.elkusnandi.generalnote.response;

import lombok.Data;

import java.time.Instant;

@Data
public class ShortUrlResponse {
    public long id;
    public String longUrl;
    public String shortId;
    public long counter;
    public Instant dateCreated;
    public Instant expirationDate;

    public ShortUrlResponse(long id, String longUrl, String shortId, long counter, Instant dateCreated, Instant expirationDate) {
        this.id = id;
        this.longUrl = longUrl;
        this.shortId = shortId;
        this.counter = counter;
        this.dateCreated = dateCreated;
        this.expirationDate = expirationDate;
    }
}
