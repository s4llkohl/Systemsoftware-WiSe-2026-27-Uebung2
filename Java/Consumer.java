public class Consumer extends Thread {

    private Stack stack;

    public Consumer(Stack stack) {
        this.stack = stack;
    }

    @Override
    public void run() {

        for (int i = 0; i < 10; i++) {

            try {
                int item = stack.pop();

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }
}

