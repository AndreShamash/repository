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

    public App() {
        this.inputHandler = new InputHandler();
        this.eventService = new EventService();
        this.userService = new UserService();

        this.menuController = new MenuController(new Menu());
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
        switch (option) {
            case 1:
                userController.registerUser(inputHandler.requestUser());
                break;
            case 2:
                userController.handleEditUser();
                break;
            case 3:
                eventController.registerEvent(inputHandler.requestEvent());
                break;
            case 4:
                eventController.handleEditEvent();
                break;
            case 5:
                eventsController.listEvents();

                break;
            case 6:
                eventController.handleAddParticipant();
                break;
            case 7:
                handleEditParticipationOrCancelEvent();
                break;
            case 8:
                handleDeleteAllEventsConfirmation();
                break;
            case 9:
                usersController.listUsers();
                break;
            case 10:
                handleDeleteAllUsersConfirmation();
                break;
            case 0:
                System.out.println("Encerrando o sistema.");
                break;
            default:
                System.out.println("Opção inválida.");
        }
    }

    private void handleEditParticipationOrCancelEvent() {
        System.out.println("\n--- Editar Participação/Cancelar Evento ---");
        System.out.println("1 - Cancelar minha participação em um evento");
        System.out.println("2 - Cancelar um evento (apenas para administradores)");
        System.out.println("3 - Reativar um evento cancelado (apenas para administradores)");
        int subOption = inputHandler.requestOptionMenu();

        if (subOption == 1) {
            eventController.handleRemoveParticipant();
        } else if (subOption == 2) {
            eventController.handleCancelEvent();
        } else if (subOption == 3) {
            eventController.handleReturnCanceledEvent();
        } else {
            System.out.println("Opção inválida.");
        }
    }

    private void handleDeleteAllEventsConfirmation() {
        if (inputHandler.confirmYesNo("Tem certeza que deseja excluir TODOS os eventos?")) {
            eventController.deleteAllEvents();
        } else {
            System.out.println("Operação cancelada.");
        }
    }

    private void handleDeleteAllUsersConfirmation() {
        if (inputHandler.confirmYesNo("Tem certeza que deseja excluir TODOS os usuários?")) {
            userController.deleteAllUsers();
        } else {
            System.out.println("Operação cancelada.");
        }
    }
}