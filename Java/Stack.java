public class Stack {
    private int[] stack;
    private int top = 0;

    public Stack(int size) {
        stack = new int[size];
    }

    public void push(int item) throws InterruptedException {
        synchronized (this) {
            while (top == stack.length) {
                wait();
            }

            stack[top] = item;
            top++;

            notifyAll();
        }
    }

    public int pop() throws InterruptedException {
        synchronized (this) {
            while (top == 0) {
                wait();
            }

            top--;
            int item = stack[top];

            notifyAll();

            return item;
        }
    }
}
