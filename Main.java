package org.example;

public class Main {
    public static void main(String[] args) {
        Stack stack = new Stack(1);

        for (int i = 1; i <= 10; i++) {
            new Thread(new Producer(stack), "Producer " + i).start();
            new Thread(new Consumer(stack), "Consumer " + i).start();
        }
    }
}
