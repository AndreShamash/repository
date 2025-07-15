package Repository;

import Model.User;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Responsável por persistir e recuperar dados de usuários no arquivo "users.data".
 */
public class UserRepository {
    private final String filePath = "users.data";

    /**
     * Salva um usuário individualmente, adicionando ao final do arquivo.
     * @param user o usuário a ser salvo
     * @return true se salvo com sucesso
     */
    public boolean save(User user) {
        try (FileWriter writer = new FileWriter(filePath, true)) {
            writer.write(formatUser(user) + System.lineSeparator());
            return true;
        } catch (IOException e) {
            System.err.println("Erro ao salvar usuário: " + e.getMessage());
            return false;
        }
    }

    /**
     * Verifica se já existe um usuário com o CPF fornecido.
     * @param cpf o CPF a ser verificado
     * @return true se já existir
     */
    public boolean existsByCpf(String cpf) {
        return findAll().stream()
                .anyMatch(u -> u.getCpf().equals(cpf));
    }

    /**
     * Lê e retorna todos os usuários salvos no arquivo.
     * @return lista de usuários
     */
    public List<User> findAll() {
        List<User> users = new ArrayList<>();

        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));

            for (String line : lines) {
                User user = parseUser(line);
                if (user != null) {
                    users.add(user);
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler usuários: " + e.getMessage());
        }

        return users;
    }

    /**
     * Salva a lista completa de usuários sobrescrevendo o arquivo.
     * @param users lista de usuários a serem salvos
     * @return true se salvo com sucesso
     */
    public boolean saveAll(List<User> users) {
        try (FileWriter writer = new FileWriter(filePath, false)) {
            for (User user : users) {
                writer.write(formatUser(user) + System.lineSeparator());
            }
            return true;
        } catch (IOException e) {
            System.err.println("Erro ao salvar usuários: " + e.getMessage());
            return false;
        }
    }

    /**
     * Exclui todos os usuários do arquivo.
     * @return true se o arquivo foi limpo com sucesso
     */
    public boolean deleteAllUsers() {
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
     * Converte uma linha do arquivo em um objeto User.
     */
    private User parseUser(String line) {
        String[] parts = line.split(";");

        try {
            if (parts.length >= 5) {
                String cpf = parts[0];
                String name = parts[1];
                String password = parts[2];
                String city = parts[3];
                String idUser = parts[4];

                List<String> eventIds = new ArrayList<>();
                if (parts.length >= 6 && !parts[5].isBlank()) {
                    eventIds = Arrays.asList(parts[5].split(","));
                }

                return new User(cpf, name, password, city, idUser, eventIds);
            } else {
                System.err.println("Linha ignorada (formato inválido): " + line);
            }
        } catch (Exception e) {
            System.err.println("Erro ao converter linha para usuário: " + e.getMessage());
        }

        return null;
    }

    /**
     * Converte um objeto User em uma linha para o arquivo.
     */
    private String formatUser(User user) {
        String eventListStr = String.join(",", user.getParticipatingEventIds());
        return String.join(";", user.getCpf(), user.getName(), user.getPassword(),
                user.getCity(), user.getIdUser(), eventListStr);
    }
}