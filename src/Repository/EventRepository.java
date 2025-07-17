package Repository;

import Model.Event;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Responsável por persistir e recuperar eventos no arquivo de dados.
 */
public class EventRepository {
    private final String filePath = "events.data";

    /**
     * Salva um único evento no final do arquivo.
     * @param event evento a ser salvo
     * @return true se salvo com sucesso
     */
    public boolean save(Event event) {
        try (FileWriter writer = new FileWriter(filePath, true)) {
            writer.write(formatEvent(event) + System.lineSeparator());
            return true;
        } catch (IOException e) {
            System.err.println("Erro ao salvar o evento: " + e.getMessage());
            return false;
        }
    }

    /**
     * Retorna todos os eventos lidos do arquivo.
     * @return lista de eventos
     */
    public List<Event> findAll() {
        List<Event> events = new ArrayList<>();

        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            for (String line : lines) {
                Event parsed = parseLineToEvent(line);
                if (parsed != null) {
                    events.add(parsed);
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler eventos: " + e.getMessage());
        }

        return events;
    }

    /**
     * Salva a lista inteira de eventos sobrescrevendo o arquivo.
     * @param events lista de eventos a serem persistidos
     * @return true se salvo com sucesso
     */
    public boolean saveAll(List<Event> events) {
        try (FileWriter writer = new FileWriter(filePath, false)) {
            for (Event e : events) {
                writer.write(formatEvent(e) + System.lineSeparator());
            }
            return true;
        } catch (IOException e) {
            System.err.println("Erro ao salvar todos os eventos: " + e.getMessage());
            return false;
        }
    }

    /**
     * Exclui todos os eventos do arquivo.
     * @return true se o arquivo foi limpo com sucesso
     */
    public boolean deleteAllEvents() {
        try {
            new FileWriter(filePath, false).close();
            return true;
        } catch (IOException e) {
            System.err.println("Erro ao excluir eventos: " + e.getMessage());
            return false;
        }
    }

    // =====================
    // MÉTODOS PRIVADOS
    // =====================

    /**
     * Converte uma linha do arquivo para um objeto Event.
     */
    private Event parseLineToEvent(String line) {
        String[] parts = line.split(";");
        try {
            if (parts.length == 10) {
                Event event = new Event(
                        parts[0], parts[1], parts[2], parts[3],
                        parts[4], parts[5], parts[6], parts[7],
                        Integer.parseInt(parts[8])
                );

                // Novidade: adiciona os participantes
                if (!parts[9].isBlank()) {
                    String[] ids = parts[9].split(",");
                    for (String id : ids) {
                        event.addParticipant(id);
                    }
                }

                return event;
            } else if (parts.length == 9) {
                // compatibilidade com eventos antigos
                return new Event(
                        parts[0], parts[1], parts[2], parts[3],
                        parts[4], parts[5], parts[6], parts[7],
                        Integer.parseInt(parts[8])
                );
            } else {
                System.err.println("Linha ignorada (formato inválido): " + line);
            }
        } catch (Exception e) {
            System.err.println("Erro ao processar evento: " + e.getMessage());
        }

        return null;
    }

    /**
     * Formata um evento para uma linha do arquivo.
     */
    private String formatEvent(Event e) {
        String participants = String.join(",", e.getParticipantUserIds());
        return String.join(";",
                e.getDate(),
                e.getTime(),
                e.getName(),
                e.getAddress(),
                e.getCategory(),
                e.getDescription(),
                e.getIdEvent(),
                e.getStatus().name(),
                String.valueOf(e.getNumberOfParticipants()),
                participants
        );
    }
}