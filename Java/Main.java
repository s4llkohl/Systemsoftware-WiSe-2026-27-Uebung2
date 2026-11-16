public class Main {

    public static void main(String[] args) {

        Stack stack = new Stack(1);

        Producer[] producers = new Producer[10];
        Consumer[] consumers = new Consumer[10];

        for (int i = 0; i < 10; i++) {
            consumers[i] = new Consumer(stack);
            consumers[i].start();
        }

        for (int i = 0; i < 10; i++) {
            producers[i] = new Producer(stack);
            producers[i].start();
        }

        try {
            for (int i = 0; i < 10; i++) {
                producers[i].join();
                consumers[i].join();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Programm beendet.");
    }
}
