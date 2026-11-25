public class Producer extends Thread {
    private Stack stack;

    public Producer(Stack stack) {
        this.stack = stack;
    }

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
