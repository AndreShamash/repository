
public class Main {
    public static void main(String[] args) {
        try {
            App app = new App();
            app.runApp();
        } catch (Exception e) {
            System.err.println("Erro inesperado na aplicação: " + e.getMessage());
            e.printStackTrace(); // útil em fase de testes
        }
    }
}