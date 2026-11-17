package org.example;

public class Producer implements Runnable {
    private Stack stack;
    
    public Producer(Stack stack) {
        this.stack = stack;
    }
    
    @Override
    public void run() {
        for (int i = 1; i <= 10 ; i++) {
            try {
                stack.push(i);
                Thread.sleep(10);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

