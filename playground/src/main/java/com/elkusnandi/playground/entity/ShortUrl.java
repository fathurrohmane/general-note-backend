package com.elkusnandi.playground.entity;

import com.elkusnandi.common.entity.Users;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Data
@Table(
        name = "short_url",
        indexes = {
                @Index(name = "short_url_short_id_index", columnList = "short_id")
        }
)
@NoArgsConstructor
public class ShortUrl {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "long_url", nullable = false)
    private String longUrl;
    @Column(name = "short_id", nullable = false, unique = true)
    private String shortId;
    @Column(name = "counter")
    private long counter;
    @Column(name = "date_created")
    private Instant dateCreated;
    @Column(name = "expiration_date")
    private Instant expirationDate;
    @ManyToOne()
    @JoinColumn(name = "created_by")
    private Users createdBy;

    public ShortUrl(
            String longUrl,
            String shortId,
            long counter,
            Instant dateCreated,
            Instant expirationDate,
            Users createdBy
    ) {
        this.longUrl = longUrl;
        this.shortId = shortId;
        this.counter = counter;
        this.dateCreated = dateCreated;
        this.expirationDate = expirationDate;
        this.createdBy = createdBy;
    }
}
