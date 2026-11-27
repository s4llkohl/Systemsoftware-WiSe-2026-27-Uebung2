

public class Stack {

    private int index;
    private final int size;
    private final int[] stack;

    public Stack(int size) {
        this.size = size;
        this.stack = new int[size];
        this.index = 0;
    }


    public void push(int value) throws InterruptedException {
        synchronized (this) {
            // Prüfen ob Stack voll
            while (index == size){
                wait();
            }

            stack[index] = value;
            index++;
            notifyAll();
        }
    }

    public int pop() throws InterruptedException {
        synchronized (this) {
            // Prüfen ob Stack empty
            while (index == 0){
                wait();
            }

            index--;
            notifyAll();
            return stack[index];
        }
    }
}
