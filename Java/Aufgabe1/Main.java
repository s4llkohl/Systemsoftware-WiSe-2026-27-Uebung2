public class Main {
    public static void main(String[] args) {
        Stack stack = new Stack(1);
        for (int i = 0; i < 10; i++) {
            new Producer(stack).start();
            new Consumer(stack).start();
        }
    }
}
