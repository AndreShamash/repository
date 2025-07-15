package Util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Utilitário responsável por garantir a existência de arquivos de dados necessários para o sistema.
 */
public class FileManager {
    private final List<String> filePaths;

    /**
     * Construtor.
     * @param filePaths lista de caminhos de arquivos que devem existir
     */
    public FileManager(List<String> filePaths) {
        this.filePaths = filePaths;
    }

    /**
     * Verifica se os arquivos existem e, se não existirem, cria cada um deles vazio.
     */
    public void verifyOrCreateFiles() {
        for (String filePath : filePaths) {
            Path path = Path.of(filePath);

            if (!Files.exists(path)) {
                try {
                    Files.createFile(path);
                    System.out.println("Arquivo criado: " + filePath);
                } catch (IOException e) {
                    System.err.println("Erro ao criar o arquivo '" + filePath + "': " + e.getMessage());
                }
            }
        }
    }
}