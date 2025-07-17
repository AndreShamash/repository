package Controller;

import Model.Menu;

import java.util.Map;

public class MenuController {
    private Menu menu;

    public MenuController(Menu menu) {
        this.menu = menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public void listMenu() {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("               MENU");
        System.out.println("=".repeat(40));

        for (Map.Entry<Integer, String> entry : menu.getOptions().entrySet()) {
            System.out.printf("%2d - %s%n", entry.getKey(), entry.getValue());
        }

        System.out.println("=".repeat(40));
    }
}