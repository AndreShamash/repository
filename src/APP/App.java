import Controller.*;
import Model.Menu;
import Service.EventService;
import Service.UserService;
import Util.FileManager;
import Util.InputHandler;
import Factory.AppFactory;

import java.util.Arrays;
import java.util.List;

public class App {
    private final MenuController menuController;
    private final InputHandler inputHandler;
    private final UserController userController;
    private final UsersController usersController;
    private final EventController eventController;
    private final EventsController eventsController;
    private final Menu mainMenu;

    public App() {
        AppFactory factory = new AppFactory();

        this.inputHandler = factory.getInputHandler();
        this.mainMenu = factory.getMainMenu();
        this.menuController = factory.createMenuController();
        this.userController = factory.createUserController();
        this.usersController = factory.createUsersController();
        this.eventController = factory.createEventController();
        this.eventsController = factory.createEventsController();
    }

    public void runApp() {
        createRequiredFiles();
        eventsController.listEvents();

        int option;
        do {
            mainMenu.initializeMainOptions();
            menuController.setMenu(mainMenu);
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
            menuController.setMenu(menuUsuario);
            menuController.listMenu();
            opcaoUsuario = inputHandler.requestOptionMenu();

            switch (opcaoUsuario) {
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