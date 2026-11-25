public class Consumer extends Thread{
    private Stack stack;

    public Consumer(Stack stack) {
        this.stack = stack;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 10; i++) {
                System.out.println(stack.pop());
            }
        } catch (InterruptedException e) {
            System.out.println(e.getStackTrace());
        }
    }
}
