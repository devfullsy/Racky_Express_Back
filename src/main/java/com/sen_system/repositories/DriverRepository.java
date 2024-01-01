package com.sen_system.repositories;

import com.sen_system.entities.Drivers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DriverRepository extends JpaRepository<Drivers,Long> {
    Optional<Drivers> findDriversByUsername(String username);
    Optional<Drivers> findDriversByReference(String reference);
}
