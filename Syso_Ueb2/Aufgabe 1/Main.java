import java.util.Scanner;

import static java.lang.Thread.sleep;

public class Main {
    public static void main(String[] args) {

        Stack stack = new Stack(1);
        Scanner sc = new Scanner(System.in);
        Consumer[] consumers = new Consumer[10];
        Producer[] producers = new Producer[10];


        for(int i = 0 ; i < 10; i++) {
            consumers[i] = new Consumer(stack);
            producers[i] = new Producer(stack);

            consumers[i].start();
            producers[i].start();
        }


        for(int i = 0; i < 10; i++){
            try {
                consumers[i].join();
                producers[i].join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
        System.out.println("Res.: "+stack.toString());

    }
}