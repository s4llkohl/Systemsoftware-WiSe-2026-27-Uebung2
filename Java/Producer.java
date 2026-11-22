public class Producer extends Thread {
    private final Stack stack;
    private final int producerId;

    public Producer(Stack stack, int producerId) {
        this.stack = stack;
        this.producerId = producerId;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            stack.push(producerId * 10 + i);
        }
    }
}
