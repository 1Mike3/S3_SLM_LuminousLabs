import game.App;

/**
 * The `Main` class serves as the entry point for the command-line interface (CLI) program.
 * It initializes the `App` and starts the program.
 */
public class Main {

    /**
     * The main method of the program.
     * It creates an instance of the `App` class and initiates the program by calling its `start` method.
     *
     * @param args The command-line arguments (not used in this program).
     */
    public static void main(String[] args) {
        App app = new App();
        app.start();
    }
}
