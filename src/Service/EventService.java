package Service;

import Model.Event;
import Model.Events;
import Model.User;
import Repository.EventRepository;
import Repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Serviço responsável pelas operações de negócio relacionadas a eventos.
 */
public class EventService {
    private final EventRepository repository = new EventRepository();
    private final UserRepository userRepository = new UserRepository();

    /**
     * Registra um novo evento no sistema.
     * @param event o evento a ser cadastrado
     * @return true se o evento foi salvo com sucesso
     */
    public boolean registerEvent(Event event) {
        return repository.save(event);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Retorna todos os eventos cadastrados.
     * @return objeto Events contendo a lista de eventos
     */
    public Events getAllEvents() {
        List<Event> eventList = repository.findAll();
        return new Events(eventList);
    }

    /**
     * Edita um evento com base em seu ID.
     * @param id ID do evento
     * @param updatedEvent novo evento a substituir
     * @return true se a edição foi bem-sucedida
     */
    public boolean editEventById(String id, Event updatedEvent) {
        List<Event> allEvents = repository.findAll();

        for (int i = 0; i < allEvents.size(); i++) {
            Event current = allEvents.get(i);
            if (current.getIdEvent().equals(id)) {
                updatedEvent.setIdEvent(id); // Garante que o ID original permaneça
                updatedEvent.setNumberOfParticipants(current.getNumberOfParticipants());
                for (String uid : current.getParticipantUserIds()) {
                    updatedEvent.addParticipant(uid);
                }
                allEvents.set(i, updatedEvent);
                return repository.saveAll(allEvents);
            }
        }

        return false; // Evento não encontrado
    }

    /**
     * Busca um evento pelo ID.
     * @param id ID do evento
     * @return o evento correspondente ou null
     */
    public Event findById(String id) {
        for (Event event : repository.findAll()) {
            if (event.getIdEvent().equals(id)) {
                return event;
            }
        }
        return null;
    }

    /**
     * Exclui todos os eventos cadastrados.
     * @return true se os dados foram apagados com sucesso
     */
    public boolean deleteAllEvents() {
        return repository.deleteAllEvents();
    }

    /**
     * Adiciona um usuário a um evento como participante.
     * Atualiza o evento e o usuário simultaneamente.
     * @param eventId ID do evento
     * @param userId ID do usuário
     * @return true se a operação foi bem-sucedida
     */
    public boolean addParticipantToEvent(String eventId, String userId) {
        System.out.println("[DEBUG] Tentando adicionar usuário ao evento...");

        List<Event> allEvents = repository.findAll();
        List<User> allUsers = userRepository.findAll();

        Event targetEvent = null;
        User targetUser = null;

        for (Event event : allEvents) {
            if (event.getIdEvent().equals(eventId)) {
                System.out.println("[DEBUG] Evento encontrado.");
                if (event.getStatus() != Event.EventStatus.PENDING) return false;
                targetEvent = event;
                break;
            }
        }

        for (User user : allUsers) {
            if (user.getIdUser().equals(userId)) {
                System.out.println("[DEBUG] Usuário encontrado.");
                targetUser = user;
                break;
            }
        }

        if (targetEvent == null || targetUser == null) {
            System.out.println("[DEBUG] Evento ou usuário não encontrado.");
            return false;
        }

        targetEvent.addParticipant(userId);
        targetUser.addEventParticipation(eventId);

        boolean success = repository.saveAll(allEvents) && userRepository.saveAll(allUsers);
        System.out.println("[DEBUG] Participação salva? " + success);
        return success;
    }


    /**
     * Remove a participação de um usuário em um evento.
     * Atualiza o evento e o usuário simultaneamente.
     * @param eventId ID do evento
     * @param userId ID do usuário
     * @return true se a operação foi bem-sucedida
     */
    public boolean removeParticipantFromEvent(String eventId, String userId) {

        List<Event> allEvents = repository.findAll();
        List<User> allUsers = userRepository.findAll();

        Event targetEvent = null;
        User targetUser = null;

        for (Event event : allEvents) {
            if (event.getIdEvent().equals(eventId)) {
                targetEvent = event;
                break;
            }
        }

        for (User user : allUsers) {
            if (user.getIdUser().equals(userId)) {
                targetUser = user;
                break;
            }
        }

        if (targetEvent == null) {
            System.out.println("Evento não encontrado." + targetEvent);
            return false;
        }
        if (targetUser == null) {
            System.out.println("Usuário não encontrado." + targetUser);
            return false;
        }
        if (!targetEvent.getParticipantUserIds().contains(userId)) {
            System.out.println("O usuário não está inscrito neste evento.");
            return false;
        }

        // Descadastrando...
        targetEvent.removeParticipant(userId);
        targetUser.removeEventParticipation(eventId);
        boolean success = repository.saveAll(allEvents) && userRepository.saveAll(allUsers);

        if (!success) {
            System.out.println("Erro ao salvar alterações nos arquivos.");
        } else {
            System.out.println("Participação cancelada com sucesso e alterações salvas.");
        }
        return success;

    }

    /**
     * Cancela um evento (altera seu status para CANCELED).
     * @param eventId ID do evento
     * @return true se o cancelamento foi realizado com sucesso
     */
    public boolean cancelEvent(String eventId) {
        List<Event> allEvents = repository.findAll();

        for (Event event : allEvents) {
            if (event.getIdEvent().equals(eventId)) {
                if (event.getStatus() == Event.EventStatus.COMPLETED) return false;
                event.setStatus(Event.EventStatus.CANCELED);
                return repository.saveAll(allEvents);
            }
        }

        return false;
    }

    /**
     * Restaura um evento que foi cancelado, alterando seu status para PENDING.
     * @param eventId ID do evento
     * @return true se a restauração foi realizada com sucesso
     */
    public boolean returnEventCanceled(String eventId) {
        List<Event> allEvents = repository.findAll();

        for (Event event : allEvents) {
            if (event.getIdEvent().equals(eventId)) {
                if (event.getStatus() == Event.EventStatus.COMPLETED) return false;
                event.setStatus(Event.EventStatus.PENDING);
                return repository.saveAll(allEvents);
            }
        }

        return false;
    }

    public String getParticipantNames(List<String> ids) {
        if (ids == null || ids.isEmpty()) return "Nenhum";
        List<User> allUsers = userRepository.findAll();
        List<String> names = new ArrayList<>();

        for (String id : ids) {
            for (User user : allUsers) {
                if (user.getIdUser().equals(id)) {
                    names.add(user.getName());
                    break;
                }
            }
        }

        return names.isEmpty() ? "Nenhum" : String.join(", ", names);
    }
}