public class Main {

    public static void main(String[] args) throws InterruptedException {

        Stack stack = new Stack(1);

        Producer[] producers = new Producer[10];
        Consumer[] consumers = new Consumer[10];

        for (int i = 0; i < 10; i++) {
            producers[i] = new Producer(stack);
            consumers[i] = new Consumer(stack);
        }

        for (int i = 0; i < 10; i++) {
            producers[i].start();
            consumers[i].start();
        }

        for (int i = 0; i < 10; i++) {
            producers[i].join();
            consumers[i].join();
        }

        System.out.println("Programm beendet.");
    }
}