public class Stack {

    private int[] data;   
    private int size = 0; 

    public Stack(int capacity) {
        data = new int[capacity];
    }

    public void push(int item) throws InterruptedException {
        synchronized (this) {
            while (size == data.length) { 
                wait();                   
            }
            data[size] = item;           
            size++;
            notifyAll();                 
        }
    }

    public int pop() throws InterruptedException {
        synchronized (this) {
            while (size == 0) {           
                wait();                   
            }
            size--;
            int item = data[size];       
            System.out.println("pop: " + item);
            notifyAll();                  
            return item;
        }
    }
}
