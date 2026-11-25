public class Main {

    public static void main(String[] args) throws InterruptedException {
        Stack stack = new Stack(1);

        Producer[] producerThreads = new Producer[10];
        Consumer[] consumerThreads = new Consumer[10];

        for(int i = 0; i < 10; i++){
            producerThreads[i] = new Producer(stack, "ProducerThread " + i);
            consumerThreads[i] = new Consumer(stack, "ConsumerThread " + i);
        }
        for(int i = 0; i < 10; i++){
            producerThreads[i].start();
            consumerThreads[i].start();
        }
        for(int i = 0; i < 10; i++){
            producerThreads[i].join();
            consumerThreads[i].join();
        }
        System.out.println("All threads finished");
    }
}
