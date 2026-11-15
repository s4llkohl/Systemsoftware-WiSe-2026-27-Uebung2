public class Main {
    public static void main(String[] args) throws InterruptedException {
        Stack stack = new Stack(1);
        Thread[] threads = new Thread[20];
        for (int i = 0; i < 10; i++) {
            threads[i] = new Producer(stack);
            threads[i].setName("Producer-" + i);
            threads[i + 10] = new Consumer(stack);
            threads[i + 10].setName("Consumer-" + i);
        }
        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        System.out.println("Alle Threads erfolgreich beendet: kein Deadlock.");
        for (Thread thread : threads) {
            System.out.println(thread.getName() + ": " + thread.getState());
        }
    }
}
