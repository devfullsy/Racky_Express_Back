package com.sen_system.repositories;

import com.sen_system.entities.RelayPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
@Repository
public interface RelayPointRepository extends JpaRepository<RelayPoint,Long> {

    Optional<RelayPoint> findRelayPointByName(String relayPointName);
    Optional<RelayPoint> findRelayPointByUsername(String username);
    Optional<RelayPoint> findRelayPointById(Long id);
    List<RelayPoint> findRelayPointsByCountry(String country);
    List<RelayPoint> findRelayPointsByCity(String city);
    List<RelayPoint> findRelayPointsByDistrict(String district);


}
