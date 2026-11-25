public class Main {

    public static void main(String[] args) {
        Stack stack = new Stack();

        for (int i = 0; i < 10; i++) {
            Producer producer = new Producer(stack);
            Consumer consumer = new Consumer(stack);

            producer.start();
            consumer.start();
        }
    }
}