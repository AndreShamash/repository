package Factory;

import Controller.*;
import Model.Menu;
import Service.EventService;
import Service.UserService;
import Util.InputHandler;

public class AppFactory {
    private final InputHandler inputHandler;
    private final EventService eventService;
    private final UserService userService;
    private final Menu mainMenu;

    public AppFactory() {
        this.inputHandler = new InputHandler();
        this.eventService = new EventService();
        this.userService = new UserService();
        this.mainMenu = new Menu();
    }

    public InputHandler getInputHandler() {
        return inputHandler;
    }

    public Menu getMainMenu() {
        return mainMenu;
    }

    public MenuController createMenuController() {
        return new MenuController(mainMenu);
    }

    public UserController createUserController() {
        return new UserController(userService, inputHandler);
    }

    public UsersController createUsersController() {
        return new UsersController(userService);
    }

    public EventController createEventController() {
        return new EventController(eventService, inputHandler);
    }

    public EventsController createEventsController() {
        return new EventsController(eventService);
    }
}