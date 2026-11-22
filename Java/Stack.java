public class Stack {
    private final int maxSize;
    private final int[] stackArray;
    private int top;

    public Stack(int size) {
        if (size < 1) {
            throw new IllegalArgumentException("Stack size must be at least 1");
        }
        this.maxSize = size;
        this.stackArray = new int[maxSize];
        this.top = -1;
    }

    public Stack() {
        this(10);
    }

    public synchronized void push(int value) {
        while (top == maxSize - 1) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }

        stackArray[++top] = value;
        notifyAll();
    }

    public synchronized int pop() {
        while (top == -1) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return -1;
            }
        }

        int value = stackArray[top--];
        notifyAll();
        return value;
    }

    public synchronized String toString() {
        int[] visible = java.util.Arrays.copyOf(stackArray, top + 1);
        return java.util.Arrays.toString(visible);
    }
}
