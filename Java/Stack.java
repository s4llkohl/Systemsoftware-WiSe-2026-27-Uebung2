public class Stack {

    private final int[] elements;
    private int top;

    public Stack(int size) {
        this.elements = new int[size];
        this.top = 0;
    }

    public void push(int element) throws InterruptedException {
        synchronized (this) {
            while (this.top == this.elements.length) {
                wait();
            }
            this.elements[this.top++] = element;
            System.out.println(Thread.currentThread().getName() + " push(" + element + ")");
            //Aufgabe 1.b.) -> notify() statt notifyAll();
            notifyAll();
        }
    }

    public void pop() throws InterruptedException {
        synchronized (this) {
            while (this.top == 0) {
                wait();
            }
            int removedElement = this.elements[--this.top];
            System.out.println(Thread.currentThread().getName() + " pop() -> " + removedElement);
            //Aufgabe 1.b.) -> notify() statt notifyAll();
            notifyAll();
        }
    }



}
