package service;

import data.repository.MovieRepository;
import data.repository.RoomRepository;
import data.repository.ShowtimeRepository;
import data.repository.TicketRepository;

public class ShowtimeService {
    private final ShowtimeRepository showtimeRepository;
    private final MovieRepository movieRepository;
    private final RoomRepository roomRepository;
    private final TicketRepository ticketRepository;

    public ShowtimeService(ShowtimeRepository showtimeRepository, MovieRepository movieRepository,
                           RoomRepository roomRepository, TicketRepository ticketRepository) {
        this.showtimeRepository = showtimeRepository;
        this.movieRepository = movieRepository;
        this.roomRepository = roomRepository;
        this.ticketRepository = ticketRepository;
    }


}
