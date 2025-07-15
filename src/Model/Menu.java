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
        initializeOptions();
    }

    /**
     * Inicializa o mapa de opções do menu principal.
     */
    private void initializeOptions() {
        addOption(1, "Cadastrar um usuário");
        addOption(2, "Editar um usuário cadastrado");
        addOption(3, "Cadastrar um evento");
        addOption(4, "Editar um evento");
        addOption(5, "Consultar os eventos de sua região");
        addOption(6, "Participar de um evento");
        addOption(7, "Editar a participação em um evento / Cancelar evento");
        addOption(8, "Excluir todos os eventos");
        addOption(9, "Listar todos os usuários");
        addOption(10, "Excluir todos os usuários");
        addOption(0, "Sair do sistema");
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