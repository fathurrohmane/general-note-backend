package com.elkusnandi.generalnote.repository;

import com.elkusnandi.generalnote.entity.ShortUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShortUrlRepository extends JpaRepository<ShortUrl, Long> {

    ShortUrl findByShortId(String shortId);

    void deleteByShortId(String shortId);

}
