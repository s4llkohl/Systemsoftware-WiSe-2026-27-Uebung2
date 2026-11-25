public class Consumer implements Runnable {
    private Stack stack;

    public Consumer(Stack stack) {
        this.stack = stack;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            stack.pop();
        }
    }

}
