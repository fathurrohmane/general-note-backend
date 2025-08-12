package com.elkusnandi.playground.service;


import com.elkusnandi.playground.request.ShortUrlRequest;
import com.elkusnandi.playground.response.ShortUrlResponse;

public interface ShortUrlService {

    String redirectToLongUrl(String shortId);

    ShortUrlResponse getShortIdDetail(String shortId);

    ShortUrlResponse createShortId(Long ownerId, ShortUrlRequest shortUrlRequest);

    void deleteShortId(String shortId);

}
