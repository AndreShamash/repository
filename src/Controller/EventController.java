package Controller;

import Model.Event;
import Service.EventService;
import Util.InputHandler;

public class EventController {
    private final EventService eventService;
    private final InputHandler inputHandler;

    public EventController(EventService eventService, InputHandler inputHandler) {
        this.eventService = eventService;
        this.inputHandler = inputHandler;
    }

    public void registerEvent(Event event) {
        if (eventService.registerEvent(event)) {
            System.out.println("Evento cadastrado com sucesso!");
        } else {
            System.out.println("Erro ao cadastrar o evento.");
        }
    }

    public void handleEditEvent() {
        String id = inputHandler.requestEventId();
        Event existing = eventService.findById(id);

        if (existing != null) {
            Event updated = inputHandler.requestUpdatedEvent(existing);
            if (eventService.editEventById(id, updated)) {
                System.out.println("Evento atualizado com sucesso!");
            } else {
                System.out.println("Falha ao atualizar o evento.");
            }
        } else {
            System.out.println("Falha: evento não encontrado.");
        }
    }

    public void deleteAllEvents() {
        if (eventService.deleteAllEvents()) {
            System.out.println("Todos os eventos foram excluídos com sucesso!");
        } else {
            System.out.println("Falha ao tentar excluir todos os eventos.");
        }
    }

    public void handleAddParticipant() {
        String eventId = inputHandler.requestEventId();
        String userId = inputHandler.requestUserIdParticipar();
        if (eventService.addParticipantToEvent(eventId, userId)) {
            System.out.println("Participação confirmada com sucesso!");
        } else {
            System.out.println("Erro: não foi possível participar. O evento pode estar cancelado, já ter ocorrido ou não existir.");
        }
    }

    public void handleRemoveParticipant() {

        String eventId = inputHandler.requestEventId();
        String userId = inputHandler.requestUserIdParticipar();

        boolean sucesso = eventService.removeParticipantFromEvent(eventId, userId);
        if (sucesso) {
            System.out.println("Participação cancelada com sucesso!");
        }
    }

    public void handleCancelEvent() {
        String eventId = inputHandler.requestEventId();
        if (eventService.cancelEvent(eventId)) {
            System.out.println("Evento cancelado com sucesso!");
        } else {
            System.out.println("Erro: o evento pode não existir, já ter sido cancelado ou já ter ocorrido.");
        }
    }

    public void handleReturnCanceledEvent() {
        String eventId = inputHandler.requestEventId();
        if (eventService.returnEventCanceled(eventId)) {
            System.out.println("Evento restabelecido com sucesso!");
        } else {
            System.out.println("Erro: o evento pode não existir, estar concluído ou não estar cancelado.");
        }
    }
}
