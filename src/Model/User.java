package Model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Representa um usuário do sistema.
 */
public class User {
    private String cpf;
    private String name;
    private String password;
    private String city;
    private String idUser;
    private List<String> participatingEventIds = new ArrayList<>();

    /**
     * Construtor padrão. Gera um ID automaticamente.
     */
    public User() {
        this.idUser = generateIdUser();
    }

    /**
     * Construtor principal para novo cadastro.
     */
    public User(String cpf, String name, String password, String city) {
        this();
        this.cpf = cpf;
        this.name = name;
        this.password = password;
        this.city = city;
    }

    /**
     * Construtor utilizado para carregar usuário de arquivo.
     */
    public User(String cpf, String name, String password, String city, String idUser, List<String> eventIds) {
        this.cpf = cpf;
        this.name = name;
        this.password = password;
        this.city = city;
        this.idUser = idUser;
        this.participatingEventIds = new ArrayList<>(eventIds);
    }

    /**
     * Gera um ID único com base na data/hora atual.
     */
    private String generateIdUser() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmmss_SSS_ddMMyyyy");
        return now.format(formatter) + "_User";
    }

    // Participações

    public void addEventParticipation(String eventId) {
        if (!participatingEventIds.contains(eventId)) {
            participatingEventIds.add(eventId);
        }
    }

    public void removeEventParticipation(String eventId) {
        participatingEventIds.remove(eventId);
    }

    public List<String> getParticipatingEventIds() {
        return Collections.unmodifiableList(participatingEventIds);
    }

    // Getters

    public String getCpf()     { return cpf; }
    public String getName()    { return name; }
    public String getPassword(){ return password; }
    public String getCity()    { return city; }
    public String getIdUser()  { return idUser; }

    // Setters

    public void setCpf(String cpf)         { this.cpf = cpf; }
    public void setName(String name)       { this.name = name; }
    public void setPassword(String password) { this.password = password; }
    public void setCity(String city)       { this.city = city; }
    public void setIdUser(String idUser)   { this.idUser = idUser; }

    @Override
    public String toString() {
        String eventos = participatingEventIds.isEmpty()
                ? "Nenhum"
                : String.join(", ", participatingEventIds);

        return String.format("""
                Usuário: %s
                CPF: %s
                Cidade: %s
                ID: %s
                Eventos: %s
                """, name, cpf, city, idUser, eventos).strip();
    }
}