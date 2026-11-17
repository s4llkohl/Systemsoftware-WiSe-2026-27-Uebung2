package org.example;

public class Consumer implements Runnable {
    private Stack stack;

    public Consumer (Stack stack) {
        this.stack = stack;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 10 ; i++) {
            try {
                stack.pop();
                Thread.sleep(10);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
