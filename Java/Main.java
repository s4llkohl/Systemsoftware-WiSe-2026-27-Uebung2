public class Main {

    public static void main(String[] args) throws InterruptedException {
        Stack stack = new Stack(1); // kleine Kapazität, damit Warten auftritt

        Thread[] threads = new Thread[20];
        for (int i = 0; i < 10; i++) {
            threads[i]      = new Producer(stack, "Producer-" + i);
            threads[10 + i] = new Consumer(stack, "Consumer-" + i);
        }

        for (Thread t : threads) t.start();
        for (Thread t : threads) t.join();

        System.out.println("Fertig.");
    }
}
