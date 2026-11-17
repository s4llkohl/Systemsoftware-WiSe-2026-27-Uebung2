package org.example;

public class Stack {
    private int[] s;
    private int top;
    private int capacity;

    public Stack(int capacity) {
        this.capacity = capacity;
        this.s = new int[capacity];
        this.top = top;
    }

    public synchronized void push(int item) throws InterruptedException {
        //ist thread voll?
        while (top == capacity) {
            wait();
        }
        s[top++] = item;
        System.out.println(Thread.currentThread().getName() + " gepusht: " + item);
        //von notify() zu notifyAll() geändert, um alle wartenden Threads aufzuwecken und so Deadlock zu verhindern
        notifyAll();
    }

    public synchronized int pop() throws InterruptedException {
        //ist stack leer?
        while (top == 0) {
            wait();
        }
        int item = s[--top];
        System.out.println(Thread.currentThread().getName() + " gepoppt: " + item);
        //von notify() zu notifyAll() geändert, um alle wartenden Threads aufzuwecken und so Deadlock zu verhindern
        notifyAll();
        return item;
    }
}

