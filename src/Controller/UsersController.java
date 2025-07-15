package Controller;

import Model.User;
import Model.Users;
import Service.UserService;

public class UsersController {
    private final UserService userService;

    public UsersController(UserService userService) {
        this.userService = userService;
    }

    public void listUsers() {
        Users users = userService.getAllUsers();

        if (users.isUsersEmpty()) {
            System.out.println("\nNenhum usuário foi cadastrado ainda.");
            return;
        }

        int quantidade = users.getUsers().size();
        String plural = quantidade > 1 ? "s" : "";

        System.out.printf("%nHá %d usuário%s cadastrados:%n%n", quantidade, plural);

        for (User user : users.getUsers()) {
            System.out.println(user);
            System.out.println("-".repeat(50));
        }
    }
}