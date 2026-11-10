
import java.util.Random;

public class visitor_count_simulation {

    static int counter = 0;
    static Random random = new Random();

    public static void registerVisits() {
        for (int i = 0; i < 1000; i++) {
            int temp = counter;

            if (random.nextDouble() < 0.001) {
                try {
                    Thread.sleep(0, random.nextInt(10000));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }

            counter = temp + 1;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread[] threads = new Thread[10];

        for (int i = 0; i < 10; i++) {
            threads[i] = new Thread(visitor_count_simulation::registerVisits);
            threads[i].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        System.out.println("Erwartete Besucherzahl: 10000");
        System.out.println("Gezählte Besucherzahl: " + counter);
    }
}
