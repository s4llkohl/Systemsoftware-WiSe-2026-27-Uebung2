public class main {
    public static void main(String[] args) throws InterruptedException {
        int stackSize = 2;
        Stack stack = new Stack(stackSize);

        Thread[] producerThreads = new Thread[10];
        Thread[] consumerThreads = new Thread[10];

        for (int i = 0; i < 10; i++) {
            producerThreads[i] = new Producer(stack, i);
            consumerThreads[i] = new Consumer(stack, i);
            producerThreads[i].start();
            consumerThreads[i].start();
        }

        for (Thread thread : producerThreads) {
            thread.join();
        }
        for (Thread thread : consumerThreads) {
            thread.join();
        }

        System.out.println("Finished. Stack: " + stack);
    }
}
