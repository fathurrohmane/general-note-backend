package com.elkusnandi.generalnote.service;

import com.elkusnandi.generalnote.request.ShortUrlRequest;
import com.elkusnandi.generalnote.response.ShortUrlResponse;

public interface ShortUrlService {

    String redirectToLongUrl(String shortId);

    ShortUrlResponse getShortIdDetail(String shortId);

    ShortUrlResponse createShortId(Long ownerId, ShortUrlRequest shortUrlRequest);

    void deleteShortId(String shortId);

}
