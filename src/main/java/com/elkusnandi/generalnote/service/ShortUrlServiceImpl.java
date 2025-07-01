package com.elkusnandi.generalnote.service;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.elkusnandi.generalnote.entity.ShortUrl;
import com.elkusnandi.generalnote.entity.Users;
import com.elkusnandi.generalnote.exception.UserFaultException;
import com.elkusnandi.generalnote.repository.ShortUrlRepository;
import com.elkusnandi.generalnote.repository.UserRepository;
import com.elkusnandi.generalnote.request.ShortUrlRequest;
import com.elkusnandi.generalnote.response.ShortUrlResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class ShortUrlServiceImpl implements ShortUrlService {

    private final ShortUrlRepository shortUrlRepository;

    private final UserRepository userRepository;

    @Autowired
    public ShortUrlServiceImpl(ShortUrlRepository shortUrlRepository, UserRepository userRepository) {
        this.shortUrlRepository = shortUrlRepository;
        this.userRepository = userRepository;
    }

    @Override
    public String redirectToLongUrl(String shortId) {
        ShortUrl shortUrl = shortUrlRepository.findByShortId(shortId);

        if (shortUrl == null) {
            throw new UserFaultException(HttpStatus.NOT_FOUND, "Not found");
        }

        return shortUrl.getLongUrl();
    }

    @Override
    public ShortUrlResponse createShortId(Long ownerId, ShortUrlRequest shortUrlRequest) {
        if (shortUrlRequest == null) {
            throw new UserFaultException(HttpStatus.BAD_REQUEST, "Request object cannot be null");
        }

        Optional<Users> currentUser = userRepository.findById(ownerId);

        if (currentUser.isEmpty()) {
            throw new UserFaultException(HttpStatus.NOT_FOUND, "Current user data not found");
        }

        String shortId;
        if (shortUrlRequest.shortId == null || shortUrlRequest.shortId.isBlank()) {
            shortId = NanoIdUtils.randomNanoId(NanoIdUtils.DEFAULT_NUMBER_GENERATOR, NanoIdUtils.DEFAULT_ALPHABET, 6);
        } else {
            ShortUrl exisingShortUrl = shortUrlRepository.findByShortId(shortUrlRequest.shortId);

            if (exisingShortUrl != null && Instant.now().isBefore(exisingShortUrl.getExpirationDate())) {
                throw new UserFaultException(HttpStatus.BAD_REQUEST, "Duplicate short id. Please use another short id.");
            }

            shortId = shortUrlRequest.shortId;
        }

        if (shortUrlRequest.longUrl.isBlank()) {
            throw new UserFaultException(HttpStatus.BAD_REQUEST, "Long url cannot be empty");
        }

        if (shortUrlRequest.expirationDate != null && Instant.now().isAfter(shortUrlRequest.expirationDate)) {
            throw new UserFaultException(HttpStatus.BAD_REQUEST, "Expiration date should be in the future");
        }

        ShortUrl newShortUrl = shortUrlRepository.save(
                new ShortUrl(shortUrlRequest.longUrl, shortId, 0, Instant.now(), shortUrlRequest.expirationDate, currentUser.get())
        );

        return new ShortUrlResponse(
                newShortUrl.getId(), newShortUrl.getLongUrl(), newShortUrl.getShortId(), newShortUrl.getCounter(), newShortUrl.getDateCreated(), newShortUrl.getExpirationDate()
        );
    }

    @Override
    public void deleteShortId(String shortId) {
        shortUrlRepository.deleteByShortId(shortId);
    }
}
