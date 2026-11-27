/** Aufgabe 1b: Ruft zehn Mal push() auf und legt dabei den eigenen Zählerstand ab. */
public class Producer extends Thread {

    private final Stack stack;

    public Producer(Stack stack, String name) {
        super(name);
        this.stack = stack;
    }

    @Override
    public void run() {
        try {
            for (int counter = 0; counter < 10; counter++) {
                stack.push(counter);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
