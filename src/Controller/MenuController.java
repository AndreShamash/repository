package Controller;

import Model.Menu;

import java.util.Map;

public class MenuController {
    private final Menu menu;

    public MenuController(Menu menu) {
        this.menu = menu;
    }

    public void listMenu() {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("        SISTEMA DE EVENTOS");
        System.out.println("=".repeat(40));

        for (Map.Entry<Integer, String> entry : menu.getOptions().entrySet()) {
            System.out.printf("%2d - %s%n", entry.getKey(), entry.getValue());
        }

        System.out.println("=".repeat(40));
    }
}