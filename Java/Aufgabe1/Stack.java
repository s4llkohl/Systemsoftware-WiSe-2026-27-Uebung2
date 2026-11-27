public class Stack {
    private final int[] items;
    private int size = 0;

    public Stack(int capacity) {
        items = new int[capacity];
    }

    public void push(int item) throws InterruptedException {
        synchronized (this) {
            while (size == items.length) {
                wait();
            }
            items[size++] = item;
            notifyAll(); // vorher notify() -> Deadlock
        }
    }

    public int pop() throws InterruptedException {
        synchronized (this) {
            while (size == 0) {
                wait();
            }
            int item = items[--size];
            System.out.println(item);
            notifyAll(); // vorher notify() -> Deadlock
            return item;
        }
    }
}
