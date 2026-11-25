public class Producer implements Runnable {
    private Stack stack;

    public Producer(Stack stack) {
        this.stack = stack;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            stack.push(i);
        }
    }

}
