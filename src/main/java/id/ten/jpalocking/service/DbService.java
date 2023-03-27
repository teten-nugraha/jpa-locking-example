package id.ten.jpalocking.service;

import id.ten.jpalocking.exceptions.ExceededCapacityException;
import id.ten.jpalocking.models.Flight;
import id.ten.jpalocking.models.Ticket;
import id.ten.jpalocking.repository.FlightRepository;
import id.ten.jpalocking.repository.TicketRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class DbService {

    private final FlightRepository flightRepository;

    private final TicketRepository ticketRepository;

    public DbService(FlightRepository flightRepository, TicketRepository ticketRepository) {
        this.flightRepository = flightRepository;
        this.ticketRepository = ticketRepository;
    }

    private void saveNewTicket(String firstName, String lastName, Flight flight) throws Exception {
        if (flight.getCapacity() <= flight.getTickets().size()) {
            throw new ExceededCapacityException();
        }
        var ticket = new Ticket();
        ticket.setFirstName(firstName);
        ticket.setLastName(lastName);
        ticket.setFlight(flight);
//        flight.addTicket(ticket);
//        flightRepository.save(flight);
        ticketRepository.save(ticket);
    }

    @Transactional
    public void changeFlight1() throws Exception {
        var flight = flightRepository.findById(1L).get();
        saveNewTicket("Robert","Smith", flight);
        Thread.sleep(1_000);
    }

    @Transactional
    public void changeFlight2() throws Exception {
        var flight = flightRepository.findById(1L).get();
        saveNewTicket("Kate","Brown", flight);
        Thread.sleep(1_000);
    }

}
