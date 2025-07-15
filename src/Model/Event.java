package Model;

import Service.EventService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class Event {
    private List<String> participantUserIds = new ArrayList<>();
    private String date;
    private String time;
    private String name;
    private String address;
    private String category;
    private String description;
    private String idEvent;
    private EventStatus status;
    private int numberOfParticipants;

    public enum EventStatus {
        PENDING,
        COMPLETED,
        CANCELED
    }

    public Event() {
        this.idEvent = generateIdEvent();
        this.status = EventStatus.PENDING;
        this.numberOfParticipants = 0;
    }

    public Event(String date, String time, String name, String address, String category, String description) {
        this(date, time, name, address, category, description, generateStaticId(), "PENDING", 0);
    }

    public Event(String date, String time, String name, String address, String category, String description, String idEvent) {
        this(date, time, name, address, category, description, idEvent, "PENDING", 0);
    }

    public Event(String date, String time, String name, String address, String category, String description, String idEvent, String statusStr, int numberOfParticipants) {
        this.date = date;
        this.time = time;
        this.name = name;
        this.address = address;
        this.category = category;
        this.description = description;
        this.idEvent = idEvent;
        this.status = parseStatus(statusStr);
        this.numberOfParticipants = numberOfParticipants;
    }

    private static String generateStaticId() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmmss_SSS_ddMMyyyy");
        return now.format(formatter) + "_Event";
    }

    private String generateIdEvent() {
        return generateStaticId();
    }

    private EventStatus parseStatus(String statusStr) {
        try {
            return EventStatus.valueOf(statusStr);
        } catch (IllegalArgumentException e) {
            return EventStatus.PENDING;
        }
    }

    // Getters e Setters
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getIdEvent() { return idEvent; }
    public void setIdEvent(String idEvent) { this.idEvent = idEvent; }

    public int getNumberOfParticipants() {
        return numberOfParticipants;
    }

    public void setNumberOfParticipants(int numberOfParticipants) {
        this.numberOfParticipants = numberOfParticipants;
    }

    public EventStatus getStatus() {
        if (this.status == EventStatus.CANCELED) {
            return EventStatus.CANCELED;
        }

        LocalDateTime eventDateTime;
        try {
            eventDateTime = LocalDateTime.parse(date + " " + time, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        } catch (Exception e) {
            return EventStatus.PENDING;
        }

        return LocalDateTime.now().isAfter(eventDateTime) ? EventStatus.COMPLETED : EventStatus.PENDING;
    }

    public void setStatus(EventStatus status) {
        this.status = status;
    }

    public void addParticipant(String userId) {
        if (!participantUserIds.contains(userId)) {
            participantUserIds.add(userId);
            numberOfParticipants++;
        }
    }

    public void removeParticipant(String userId) {
        if (participantUserIds.remove(userId)) {
            numberOfParticipants--;
        }
    }

    public List<String> getParticipantUserIds() {
        return Collections.unmodifiableList(participantUserIds);
    }

    private String getTimeRemainingMessage() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime eventDateTime;

        try {
            eventDateTime = LocalDateTime.parse(date + " " + time, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        } catch (Exception e) {
            return "Data/hora inválida";
        }

        if (now.isAfter(eventDateTime)) {
            return "Evento já realizado";
        }

        long minutes = ChronoUnit.MINUTES.between(now, eventDateTime);
        long hours = minutes / 60;
        long remainingMinutes = minutes % 60;

        if (minutes <= 0) {
            return String.format("Evento %s está começando agora!", name);
        }

        return String.format("Faltam %d hora%s e %d minuto%s para começar o evento %s",
                hours, hours != 1 ? "s" : "",
                remainingMinutes, remainingMinutes != 1 ? "s" : "",
                name);
    }

    @Override
    public String toString() {
        String statusMessage;

        switch (getStatus()) {
            case PENDING:
                statusMessage = getTimeRemainingMessage();
                break;
            case COMPLETED:
                statusMessage = "Evento já realizado";
                break;
            case CANCELED:
                statusMessage = "Evento cancelado";
                break;
            default:
                statusMessage = "Status desconhecido";
        }

        String participantsMessage = (numberOfParticipants == 0) ? "Nenhum participante" :
                (numberOfParticipants == 1 ? "1 participante" : numberOfParticipants + " participantes");

        return String.format("""
                Evento: %s
                Data: %s às %s
                Endereço: %s
                Categoria: %s
                Descrição: %s
                ID do Evento: %s
                Status: %s
                Participantes: %s
                """,
                name, date, time, address, category, description,
                idEvent, statusMessage, participantsMessage
        ).strip();
    }

    public String toStringWithParticipants(List<User> allUsers) {
        String statusMessage;

        switch (getStatus()) {
            case PENDING -> statusMessage = getTimeRemainingMessage();
            case COMPLETED -> statusMessage = "Evento já realizado";
            case CANCELED -> statusMessage = "Evento cancelado";
            default -> statusMessage = "Status desconhecido";
        }

        List<String> names = new ArrayList<>();
        for (String id : participantUserIds) {
            for (User user : allUsers) {
                if (user.getIdUser().equals(id)) {
                    names.add(user.getName());
                    break;
                }
            }
        }

        String participantsMessage = names.isEmpty()
                ? "Nenhum participante"
                : String.format("%d participante%s: %s",
                names.size(), names.size() > 1 ? "s" : "", String.join(", ", names));

        return String.format("""
        Evento: %s
        Data: %s às %s
        Endereço: %s
        Categoria: %s
        Descrição: %s
        ID do Evento: %s
        Status: %s
        Participantes: %s
        """,
                name, date, time, address, category, description,
                idEvent, statusMessage, participantsMessage
        ).strip();
    }


}