public class Consumer extends Thread {

    private final Stack stack;

    public Consumer(Stack stack, String threadName) {
        super(threadName);
        this.stack = stack;
    }

    @Override
    public void run() {
        try {
            for(int counter = 0; counter < 10; counter++){
               stack.pop();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
