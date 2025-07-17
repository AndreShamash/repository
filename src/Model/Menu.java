package Model;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Representa o menu principal do sistema com as suas opções numéricas e descrições.
 */
public class Menu {
    private final Map<Integer, String> options;

    /**
     * Construtor que inicializa as opções do menu em ordem definida.
     */
    public Menu() {
        options = new LinkedHashMap<>();
        //initializeOptions();
    }

    /**
     * Inicializa o mapa de opções do menu principal.
     */
    public void initializeMainOptions() {
        options.clear();
        addOption(1, "Gerenciar os usuários");
        addOption(2, "Gerenciar os eventos");
        addOption(0, "Sair do sistema");
    }

    /**
     * Inicializa o submenu de usuário.
     */
    public void initializeUserOptions() {
        options.clear();
        addOption(1, "Cadastrar um usuário");
        addOption(2, "Editar um usuário cadastrado");
        addOption(3, "Participar de um evento");
        addOption(4, "Cancelar participação em evento");
        addOption(5, "Listar todos os usuários");
        addOption(6, "Excluir todos os usuários");
        addOption(0, "Voltar ao menu principal");
    }

    /**
     * Inicializa o submenu de eventos.
     */
    public void initializeEventOptions() {
        options.clear();
        addOption(1, "Cadastrar um evento");
        addOption(2, "Editar um evento");
        addOption(3, "Cancelar evento");
        addOption(4, "Consultar eventos da região");
        addOption(5, "Excluir todos os eventos");
        addOption(0, "Voltar ao menu principal");
    }

    /**
     * Adiciona uma nova opção ao menu.
     * @param code código numérico da opção
     * @param description descrição textual da opção
     */
    public void addOption(int code, String description) {
        options.put(code, description);
    }

    /**
     * Retorna o mapa imutável de opções do menu.
     * @return mapa com as opções disponíveis
     */
    public Map<Integer, String> getOptions() {
        return Collections.unmodifiableMap(options);
    }
}