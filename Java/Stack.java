import java.util.ArrayList;
import java.util.List;


public class Stack {
    int[] stack;
    int top;
    static int pushes = 0;
    static int pops = 0;


    public Stack(int size) {
        stack = new int[size];
        top = -1;
    }



    public synchronized void push(int value) {
        while (top == stack.length - 1) {
            try {
                wait();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        stack[++top] = value;
        pushes++;
        notifyAll();
    }

    public synchronized int pop() {
        while (top == -1) {
            try {
                wait();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        int temp = stack[top--];
        pops++;
        notifyAll();
        return temp;
    }




    public static void main(String[] args) {
        Stack stack = new Stack(3  );
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Thread producerThread = new Thread(new Producer(stack));
            Thread consumerThread = new Thread(new Consumer(stack));
            threads.add(producerThread);
            threads.add(consumerThread);
        }
        

        try {
            for (Thread thread : threads) {
                thread.start();
            }

            for (Thread thread : threads) {
                thread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Total pushes: " + pushes);
        System.out.println("Total pops: " + pops);
    }


}

