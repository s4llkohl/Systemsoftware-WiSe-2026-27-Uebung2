public class Stack {

    private int[] stack;
    private int size;
    private int capacity;

    public Stack(int capacity) {
        this.capacity = capacity;
        this.stack = new int[capacity];
        this.size = 0;
    }

    public void push(int item) throws InterruptedException {
        synchronized (this) {

            while (size == capacity) {
                wait();
            }

            for (int i = size; i > 0; i--) {
                stack[i] = stack[i - 1];
            }

            stack[0] = item;
            size++;

            System.out.println("Push: " + item);

            //notify();
            notifyAll();
        }
    }

    public int pop() throws InterruptedException {
        synchronized (this) {

            while (size == 0) {
                wait();
            }

            int item = stack[0];

            for (int i = 0; i < size - 1; i++) {
                stack[i] = stack[i + 1];
            }

            size--;

            System.out.println("Pop: " + item);

            //notify();
            notifyAll();

            return item;
        }
    }
}
