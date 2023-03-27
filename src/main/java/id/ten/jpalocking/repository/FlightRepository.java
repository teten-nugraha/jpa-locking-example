package id.ten.jpalocking.repository;

import id.ten.jpalocking.models.Flight;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightRepository extends CrudRepository<Flight, Long> {
}
