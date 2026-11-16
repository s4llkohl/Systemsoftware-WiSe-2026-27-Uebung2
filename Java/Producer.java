public class Producer extends Thread {

    private Stack stack;

    public Producer(Stack stack) {
        this.stack = stack;
    }

    @Override
    public void run() {

        for (int i = 0; i < 10; i++) {

            try {
                stack.push(i);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }
}
