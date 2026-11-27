public class Producer extends Thread {

    private final Stack stack;

    public Producer(Stack stack) {
        this.stack = stack;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 10; i++) {
                stack.push(i);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}