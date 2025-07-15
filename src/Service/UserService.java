package Service;

import Model.User;
import Model.Users;
import Repository.UserRepository;

import java.util.List;

/**
 * Serviço responsável por gerenciar operações relacionadas a usuários.
 */
public class UserService {
    private final UserRepository repository = new UserRepository();

    /**
     * Registra um novo usuário no sistema.
     * @param user objeto User a ser cadastrado
     * @return true se o cadastro foi bem-sucedido
     */
    public boolean registerUser(User user) {
        return repository.save(user);
    }

    /**
     * Retorna todos os usuários cadastrados.
     * @return objeto Users contendo a lista atual
     */
    public Users getAllUsers() {
        return new Users(repository.findAll());
    }

    /**
     * Edita os dados de um usuário mantendo o ID e eventos vinculados.
     * @param id ID do usuário a ser editado
     * @param updatedUser objeto com os dados atualizados
     * @return true se editado e salvo com sucesso
     */
    public boolean editUserById(String id, User updatedUser) {
        List<User> allUsers = repository.findAll();

        for (int i = 0; i < allUsers.size(); i++) {
            User current = allUsers.get(i);
            if (current.getIdUser().equals(id)) {
                // Preserva o ID e as participações já registradas
                updatedUser.setIdUser(current.getIdUser());
                for (String eventId : current.getParticipatingEventIds()) {
                    updatedUser.addEventParticipation(eventId);
                }

                allUsers.set(i, updatedUser);
                return repository.saveAll(allUsers);
            }
        }

        return false; // Usuário não encontrado
    }

    /**
     * Verifica se um CPF já está cadastrado no sistema.
     * @param cpf CPF a ser verificado
     * @return true se já existir
     */
    public boolean cpfExists(String cpf) {
        return repository.existsByCpf(cpf);
    }

    /**
     * Busca um usuário pelo seu ID.
     * @param id ID do usuário
     * @return o usuário correspondente ou null
     */
    public User findUserById(String id) {
        for (User user : repository.findAll()) {
            if (user.getIdUser().equals(id)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Exclui todos os usuários cadastrados.
     * @return true se os dados foram apagados com sucesso
     */
    public boolean deleteAllUsers() {
        return repository.deleteAllUsers();
    }
}