package Util;

import Model.Event;
import Model.User;

import java.util.Scanner;

/**
 * Utilitário responsável por lidar com entradas de dados do usuário via console.
 */
public class InputHandler {
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Retorna o scanner interno (caso necessário).
     */
    public Scanner getScanner() {
        scanner.next();
        return scanner;
    }

    /**
     * Solicita a opção escolhida no menu principal.
     */
    public int requestOptionMenu() {
        System.out.print("\nEscolha uma opção do menu: ");
        while (!scanner.hasNextInt()) {
            System.out.print("Entrada inválida. Digite um número: ");
            scanner.next();
        }
        return scanner.nextInt();
    }

    /**
     * Solicita o ID de um usuário.
     */
    public String requestUserId() {
        return readLineWithFlush("Digite o ID do usuário: ");
    }

    public String requestUserIdParticipar() {
        return readLine("Digite o ID do usuário: ");
    }

    /**
     * Solicita o ID de um evento.
     */
    public String requestEventId() {
        //System.out.print("Digite o ID do evento: ");
        //return scanner.nextLine().trim();
        return readLineWithFlush("Digite o ID do evento: ");

    }

    /**
     * Solicita os dados para cadastrar um novo usuário.
     */
    public User requestUser() {
        scanner.nextLine(); // Limpa buffer
        System.out.print("CPF: ");
        String cpf = scanner.nextLine().trim();

        System.out.print("Nome: ");
        String name = scanner.nextLine().trim();

        System.out.print("Senha: ");
        String password = scanner.nextLine().trim();

        System.out.print("Cidade: ");
        String city = scanner.nextLine().trim();

        return new User(cpf, name, password, city);
    }

    /**
     * Solicita os dados atualizados de um usuário (exceto ID e eventos).
     */
    public User requestUpdatedUser(User existingUser) {
        System.out.println("\n--- Editar Usuário ---");

        System.out.printf("CPF atual: %s | Novo CPF: ", existingUser.getCpf());
        String cpf = scanner.nextLine().trim();
        if (cpf.isBlank()) cpf = existingUser.getCpf();

        System.out.printf("Nome atual: %s | Novo nome: ", existingUser.getName());
        String name = scanner.nextLine().trim();
        if (name.isBlank()) name = existingUser.getName();

        System.out.printf("Senha atual: %s | Nova senha: ", existingUser.getPassword());
        String password = scanner.nextLine().trim();
        if (password.isBlank()) password = existingUser.getPassword();

        System.out.printf("Cidade atual: %s | Nova cidade: ", existingUser.getCity());
        String city = scanner.nextLine().trim();
        if (city.isBlank()) city = existingUser.getCity();

        return new User(cpf, name, password, city);
    }

    /**
     * Solicita os dados para cadastrar um novo evento.
     */
    public Event requestEvent() {
        scanner.nextLine(); // Limpa buffer

        System.out.print("Data (yyyy-MM-dd): ");
        String date = scanner.nextLine().trim();

        System.out.print("Hora (HH:mm): ");
        String time = scanner.nextLine().trim();

        System.out.print("Nome do evento: ");
        String name = scanner.nextLine().trim();

        System.out.print("Endereço: ");
        String address = scanner.nextLine().trim();

        System.out.print("Categoria: ");
        String category = scanner.nextLine().trim();

        System.out.print("Descrição: ");
        String description = scanner.nextLine().trim();

        return new Event(date, time, name, address, category, description);
    }

    /**
     * Solicita os dados atualizados de um evento.
     */
    public Event requestUpdatedEvent(Event existing) {
        System.out.println("\n--- Editar Evento ---");

        System.out.printf("Data atual: %s | Nova data (yyyy-MM-dd): ", existing.getDate());
        String date = scanner.nextLine().trim();
        if (date.isBlank()) date = existing.getDate();

        System.out.printf("Hora atual: %s | Nova hora (HH:mm): ", existing.getTime());
        String time = scanner.nextLine().trim();
        if (time.isBlank()) time = existing.getTime();

        System.out.printf("Nome atual: %s | Novo nome: ", existing.getName());
        String name = scanner.nextLine().trim();
        if (name.isBlank()) name = existing.getName();

        System.out.printf("Endereço atual: %s | Novo endereço: ", existing.getAddress());
        String address = scanner.nextLine().trim();
        if (address.isBlank()) address = existing.getAddress();

        System.out.printf("Categoria atual: %s | Nova categoria: ", existing.getCategory());
        String category = scanner.nextLine().trim();
        if (category.isBlank()) category = existing.getCategory();

        System.out.printf("Descrição atual: %s | Nova descrição: ", existing.getDescription());
        String description = scanner.nextLine().trim();
        if (description.isBlank()) description = existing.getDescription();

        return new Event(date, time, name, address, category, description, existing.getIdEvent());
    }

    public String readLineFlushed() {
        if (scanner.hasNextLine()) scanner.nextLine(); // limpa o ENTER pendente
        return scanner.nextLine().trim();
    }

    public String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    public String readLineWithFlush(String prompt) {
        System.out.print(prompt);
        if (scanner.hasNextLine()) scanner.nextLine(); // limpa o ENTER pendente
        return scanner.nextLine().trim();
    }

    public boolean confirmYesNo(String prompt) {
        String resposta = readLineWithFlush(prompt + " (s/n): ").toLowerCase();
        return resposta.equals("s");
    }
}