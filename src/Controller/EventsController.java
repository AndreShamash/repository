package Controller;

import Model.Event;
import Model.Events;
import Model.User;
import Service.EventService;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

public class EventsController {
    private final EventService eventService;

    public EventsController(EventService eventService) {
        this.eventService = eventService;
    }

    public void listEvents() {
        Events events = eventService.getAllEvents();

        if (events.isEventEmpty()) {
            System.out.println("\nNenhum evento foi cadastrado ainda.");
            return;
        }

        List<User> allUsers = eventService.getAllUsers();

        int quantidade = events.getEvents().size();
        String plural = (quantidade > 1) ? "s" : "";
        String verbo = (quantidade > 1) ? "disponíveis" : "";

        System.out.printf("%nHá %d evento%s %s na sua região:%n%n", quantidade, plural, verbo);

        // Ordena por data
        List<Event> eventosOrdenados = events.getEvents().stream()
                .sorted(Comparator.comparing(event -> LocalDate.parse(event.getDate())))
                .toList();

        for (Event event : eventosOrdenados) {
            System.out.println(event.toStringWithParticipants(allUsers));
            System.out.println("-".repeat(50));
        }
    }
}
