package Controller;

import Model.User;
import Service.UserService;
import Util.InputHandler;

public class UserController {
    private final UserService userService;
    private final InputHandler inputHandler;

    public UserController(UserService userService, InputHandler inputHandler) {
        this.userService = userService;
        this.inputHandler = inputHandler;
    }

    public void registerUser(User user) {
        if (userService.cpfExists(user.getCpf())) {
            System.out.println("Erro: este CPF já está cadastrado no sistema.");
            return;
        }

        if (userService.registerUser(user)) {
            System.out.println("Usuário cadastrado com sucesso!");
        } else {
            System.out.println("Erro ao cadastrar o usuário. Tente novamente.");
        }
    }

    public void handleEditUser() {
        String id = inputHandler.requestUserId().trim();
        User existing = userService.findUserById(id);

        if (existing == null) {
            System.out.println("Erro: usuário não encontrado.");
            return;
        }

        User updated = inputHandler.requestUpdatedUser(existing);
        if (userService.editUserById(id, updated)) {
            System.out.println("Usuário atualizado com sucesso!");
        } else {
            System.out.println("Erro ao atualizar o usuário. Verifique os dados e tente novamente.");
        }
    }

    public void deleteAllUsers() {
        if (userService.deleteAllUsers()) {
            System.out.println("Todos os usuários foram excluídos com sucesso!");
        } else {
            System.out.println("Falha ao tentar excluir todos os usuários.");
        }
    }

    public void handleDeleteAllUsersConfirmation() {
        if (inputHandler.confirmYesNo("Tem certeza que deseja excluir TODOS os usuários?")) {
            deleteAllUsers();
        } else {
            System.out.println("Operação cancelada.");
        }
    }
}