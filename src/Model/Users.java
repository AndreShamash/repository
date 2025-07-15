package Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Representa uma coleção de usuários cadastrados no sistema.
 */
public class Users {
    private final List<User> users;

    /**
     * Construtor padrão: inicializa a lista como vazia.
     */
    public Users() {
        this.users = new ArrayList<>();
    }

    /**
     * Construtor que aceita uma lista inicial de usuários.
     * @param users lista de usuários
     */
    public Users(List<User> users) {
        this.users = new ArrayList<>(users); // cópia defensiva
    }

    /**
     * Retorna uma lista imutável de usuários.
     * @return lista de usuários
     */
    public List<User> getUsers() {
        return Collections.unmodifiableList(users);
    }

    /**
     * Adiciona um novo usuário à coleção.
     * @param user o usuário a ser adicionado
     */
    public void addUsers(User user) {
        users.add(user);
    }

    /**
     * Verifica se a coleção está vazia.
     * @return true se não houver nenhum usuário cadastrado
     */
    public boolean isUsersEmpty() {
        return users.isEmpty();
    }
}