package Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Representa uma coleção de eventos cadastrados no sistema.
 * Fornece métodos para adicionar e consultar a lista de eventos.
 */
public class Events {
    private final List<Event> events;

    /**
     * Construtor padrão. Inicializa uma lista vazia de eventos.
     */
    public Events() {
        this.events = new ArrayList<>();
    }

    /**
     * Construtor que aceita uma lista de eventos existente.
     * @param events lista de eventos a ser encapsulada
     */
    public Events(List<Event> events) {
        this.events = new ArrayList<>(events); // cópia defensiva
    }

    /**
     * Retorna uma lista imutável de eventos.
     * @return lista não modificável de eventos
     */
    public List<Event> getEvents() {
        return Collections.unmodifiableList(events);
    }

    /**
     * Adiciona um evento à coleção.
     * @param event evento a ser adicionado
     */
    public void addEvent(Event event) {
        events.add(event);
    }

    /**
     * Verifica se não há nenhum evento cadastrado.
     * @return true se a lista estiver vazia
     */
    public boolean isEventEmpty() {
        return events.isEmpty();
    }
}