import Controller.*;
import Model.Menu;
import Service.EventService;
import Service.UserService;
import Util.FileManager;
import Util.InputHandler;

import java.util.Arrays;
import java.util.List;

public class App {
    private final MenuController menuController;
    private final InputHandler inputHandler;
    private final UserController userController;
    private final UsersController usersController;
    private final UserService userService;
    private final EventService eventService;
    private final EventController eventController;
    private final EventsController eventsController;
    private final Menu mainMenu;

    public App() {
        this.inputHandler = new InputHandler();
        this.eventService = new EventService();
        this.userService = new UserService();

        this.mainMenu = new Menu();
        this.menuController = new MenuController(mainMenu);
        this.userController = new UserController(userService, inputHandler);
        this.usersController = new UsersController(userService);
        this.eventController = new EventController(eventService, inputHandler);
        this.eventsController = new EventsController(eventService);
    }

    public void execute() {
        createRequiredFiles();
        eventsController.listEvents();

        int option;
        do {
            mainMenu.initializeMainOptions(); // Menu principal
            menuController.setMenu(mainMenu); // ESSENCIAL: redefine menu principal

            menuController.listMenu();
            option = inputHandler.requestOptionMenu();
            processOption(option);
        } while (option != 0);
    }

    private void createRequiredFiles() {
        List<String> arquivos = Arrays.asList("users.data", "events.data");
        FileManager fileManager = new FileManager(arquivos);
        fileManager.verifyOrCreateFiles();
    }

    private void processOption(int option) {
        switch (option) { // Essa sintaxe SWITCH é antiga e se refere ao Java de versões inferiores a 14
            case 1:
                submenuUsuarios();
                break;
            case 2:
                submenuEventos();
                break;
            case 0:
                System.out.println("Encerrando o sistema.");
                break;
            default:
                System.out.println("Opção inválida.");
        }
    }

    // === SUBMENU DE USUÁRIOS ===
    private void submenuUsuarios() {
        Menu menuUsuario = new Menu();
        int opcaoUsuario;

        do {
            menuUsuario.initializeUserOptions();
            menuController.setMenu(menuUsuario); // trocar menu do controller
            menuController.listMenu();
            opcaoUsuario = inputHandler.requestOptionMenu();

            switch (opcaoUsuario) { // Essa sintaxe SWITCH é mais recente e se refere ao Java de versões iguais ou superiores a 17
                case 1 -> userController.registerUser(inputHandler.requestUser());
                case 2 -> userController.handleEditUser();
                case 3 -> eventController.handleAddParticipant();
                case 4 -> eventController.handleRemoveParticipant();
                case 5 -> usersController.listUsers();
                case 6 -> userController.handleDeleteAllUsersConfirmation();
                case 0 -> System.out.println("Voltando ao menu principal...");
                default -> System.out.println("Opção inválida.");
            }
        } while (opcaoUsuario != 0);
    }

    // === SUBMENU DE EVENTOS ===
    private void submenuEventos() {
        Menu menuEvento = new Menu();
        int opcaoEvento;

        do {
            menuEvento.initializeEventOptions();
            menuController.setMenu(menuEvento);
            menuController.listMenu();
            opcaoEvento = inputHandler.requestOptionMenu();

            switch (opcaoEvento) {
                case 1 -> eventController.registerEvent(inputHandler.requestEvent());
                case 2 -> eventController.handleEditEvent();
                case 3 -> eventController.handleCancelOrRestoreEvent();
                case 4 -> eventsController.listEvents();
                case 5 -> eventController.handleDeleteAllEventsConfirmation();
                case 0 -> System.out.println("Voltando ao menu principal...");
                default -> System.out.println("Opção inválida.");
            }
        } while (opcaoEvento != 0);
    }
}