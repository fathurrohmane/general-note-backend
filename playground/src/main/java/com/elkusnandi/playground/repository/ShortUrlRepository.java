package com.elkusnandi.playground.repository;

import com.elkusnandi.playground.entity.ShortUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShortUrlRepository extends JpaRepository<ShortUrl, Long> {

    ShortUrl findByShortId(String shortId);

    void deleteByShortId(String shortId);

}
