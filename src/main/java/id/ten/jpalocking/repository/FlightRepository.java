package id.ten.jpalocking.repository;

import id.ten.jpalocking.models.Flight;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.Optional;

@Repository
public interface FlightRepository extends CrudRepository<Flight, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Flight> findWithLockingById(Long id);

}
