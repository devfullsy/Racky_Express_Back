package com.sen_system.repositories;


import com.sen_system.entities.Clients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Clients,Long> {

    Optional<Clients> findByUsername(String username);
    Optional<Clients> findByEmail(String email);

}
