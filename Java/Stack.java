public class Stack {

    private int[] items;
    private int top = 0;
    private boolean useNotifyAll;

    public Stack(int size, boolean useNotifyAll) {
        items = new int[size];
        this.useNotifyAll = useNotifyAll;
    }

    public synchronized void push(int item) throws InterruptedException {
        while (top == items.length) {
            wait();
        }
        items[top] = item;
        top++;
        wecken();
    }

    public synchronized int pop() throws InterruptedException {
        while (top == 0) {
            wait();
        }
        top--;
        int item = items[top];
        System.out.println("Geholt: " + item);
        wecken();
        return item;
    }

    private void wecken() {
        if (useNotifyAll) {
            notifyAll();
        } else {
            notify();
        }
    }
}