package com.elkusnandi.generalnote.repository;

import com.elkusnandi.generalnote.entity.ShuttleOutletLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ShuttleOutletLocationRepository extends JpaRepository<ShuttleOutletLocation, UUID> {
}
