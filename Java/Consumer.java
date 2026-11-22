public class Consumer extends Thread {
    private final Stack stack;
    private final int consumerId;

    public Consumer(Stack stack, int consumerId) {
        this.stack = stack;
        this.consumerId = consumerId;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            int value = stack.pop();
            if (value != -1) {
                System.out.println("Consumer " + consumerId + " popped " + value);
            }
        }
    }
}
