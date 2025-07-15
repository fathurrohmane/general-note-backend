package com.elkusnandi.generalnote.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "travel")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Travel {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "visible")
    private Boolean visible;

    @OneToMany(mappedBy = "travel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TravelRoute> routes;

    public Travel(UUID id, String name, Boolean visible) {
        this.id = id;
        this.name = name;
        this.visible = visible;
    }
}
