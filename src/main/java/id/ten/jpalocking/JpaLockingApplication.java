package id.ten.jpalocking;

import id.ten.jpalocking.models.Flight;
import id.ten.jpalocking.models.Ticket;
import id.ten.jpalocking.repository.FlightRepository;
import id.ten.jpalocking.repository.TicketRepository;
import id.ten.jpalocking.service.DbService;
import org.apache.commons.lang3.function.FailableRunnable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
public class JpaLockingApplication implements CommandLineRunner {

    @Resource
    private DbService dbService;

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private TicketRepository ticketRepository;

    public static void main(String[] args) {
        SpringApplication.run(JpaLockingApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        // init data
        initData();

        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.execute(safeRunnable(dbService::changeFlight1));
        executor.execute(safeRunnable(dbService::changeFlight2));
        executor.shutdown();
    }

    private void initData() {
        Flight dataFlight1 = new Flight();
        dataFlight1.setId(1L);
        dataFlight1.setNumber("FLT123");
        dataFlight1.setDepartureTime(LocalDateTime.now());
        dataFlight1.setCapacity(2);
        dataFlight1.setVersion(0L);

        Flight dataFlight2 = new Flight();
        dataFlight2.setId(2L);
        dataFlight2.setNumber("FLT345");
        dataFlight2.setDepartureTime(LocalDateTime.now());
        dataFlight2.setCapacity(10);
        dataFlight2.setVersion(0L);

        flightRepository.save(dataFlight1);
        flightRepository.save(dataFlight2);

        Ticket ticket = new Ticket();
        ticket.setId(1L);
        ticket.setFlight(dataFlight1);
        ticket.setFirstName("Paul");
        ticket.setLastName("Lee");
        ticketRepository.save(ticket);
    }

    private Runnable safeRunnable(FailableRunnable<Exception> runnable) {
        return () -> {
            try {
                runnable.run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }
}
