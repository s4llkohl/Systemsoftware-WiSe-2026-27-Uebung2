public class Stack {
    private final Integer[] elements;
    private int size = 0;

    public Stack(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Kapazitaet muss positiv sein");
        }
        elements = new Integer[capacity];
    }

    public void push(Integer value) throws InterruptedException {
        synchronized (this) {
            while (size == elements.length) {
                wait();
            }
            elements[size++] = value;
            notifyAll();
        }
    }

    public Integer pop() throws InterruptedException {
        synchronized (this) {
            while (size == 0) {
                wait();
            }
            Integer value = elements[--size];
            elements[size] = null;
            notifyAll();
            return value;
        }
    }
}
