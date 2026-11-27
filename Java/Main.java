/**
 * Aufgabe 1b/c: Zehn Producer und zehn Consumer arbeiten auf demselben Stack.
 *
 * Aufruf:
 *   javac *.java
 *   java Main                 Kapazität 1, notify()     -> Deadlock wahrscheinlich
 *   java Main 1 notifyAll     Kapazität 1, notifyAll()  -> korrekt
 *
 * Damit das Programm bei einem Deadlock nicht ewig hängt, wartet main
 * höchstens fünf Sekunden auf die Threads und meldet den Deadlock dann.
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {
        int capacity = args.length > 0 ? Integer.parseInt(args[0]) : 1;
        boolean useNotifyAll = args.length > 1 && args[1].equalsIgnoreCase("notifyAll");

        System.out.println("Stack-Kapazität: " + capacity
                + ", Wecken mit: " + (useNotifyAll ? "notifyAll()" : "notify()"));

        Stack stack = new Stack(capacity, useNotifyAll);
        Thread[] threads = new Thread[20];
        for (int i = 0; i < 10; i++) {
            threads[i] = new Producer(stack, "Producer-" + i);
            threads[10 + i] = new Consumer(stack, "Consumer-" + i);
        }
        for (Thread t : threads) {
            t.setDaemon(true);   // JVM darf trotz hängender Threads beenden
            t.start();
        }

        long deadline = System.currentTimeMillis() + 5000;
        for (Thread t : threads) {
            t.join(Math.max(1, deadline - System.currentTimeMillis()));
        }

        StringBuilder waiting = new StringBuilder();
        for (Thread t : threads) {
            if (t.isAlive()) {
                waiting.append("  ").append(t.getName()).append(" (").append(t.getState()).append(")\n");
            }
        }
        if (waiting.length() > 0) {
            System.out.println("\nDEADLOCK: folgende Threads schlafen und werden nie mehr geweckt:");
            System.out.print(waiting);
            System.exit(1);
        }
        System.out.println("\nAlle 100 push()- und 100 pop()-Aufrufe erfolgreich beendet.");
    }
}
