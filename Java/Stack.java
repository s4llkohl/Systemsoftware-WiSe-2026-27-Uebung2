
public class Stack {

    private final int[] items;
    private int top = 0;

    public Stack(int capacity) {
        items = new int[capacity];
    }

    public void push(int item) throws InterruptedException {
        synchronized (this) {
            // während Stack voll -> Thread muss warten
            while (top == items.length) {
                System.out.println(Thread.currentThread().getName() + " wartet (Stack voll)");
                wait();
            }
            // Item auf den Stack legen
            items[top++] = item;

            System.out.println(Thread.currentThread().getName() + " push: " + item + " (Stackgröße = " + top + ")");
            notifyAll();
            // notify();
        }
    }

    public int pop() throws InterruptedException {
        synchronized (this) {
            // während Stack leer -> Thread muss warten
            while (top == 0) {
                System.out.println(Thread.currentThread().getName() + " wartet (Stack leer)");
                wait();
            }
            // Item von Stack entfernen
            int item = items[--top];
            items[top] = 0;

            System.out.println(Thread.currentThread().getName() + " pop:  " + item + " (Stackgröße = " + top + ")");
            notifyAll();
            //notify();
            return item;
        }
    }
}

