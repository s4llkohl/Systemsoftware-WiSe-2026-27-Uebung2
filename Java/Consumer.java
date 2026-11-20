public class Consumer extends Thread {

    private final Stack stack;

    public Consumer(Stack stack, String name) {
        super(name);
        this.stack = stack;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 10; i++) {stack.pop();}
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}
