/**
 * Aufgabe 1a: Beschränkter Stack auf Basis eines int-Arrays.
 *
 * push() legt einen Wert an vorderster Stelle (Index 0) ab, pop() entnimmt den
 * Wert an vorderster Stelle. Ist der Stack voll bzw. leer, legt sich der
 * aufrufende Thread mit wait() schlafen. Der Zustand wird in einer
 * while-Schleife geprüft, damit ein aufgeweckter Thread die Bedingung erneut
 * testet (Schutz vor Spurious Wakeups und vor "falschen" Weckrufen).
 *
 * Über useNotifyAll lässt sich zwischen notify() (Aufgabe 1b/c, Deadlock
 * möglich) und notifyAll() (Korrektur aus Aufgabe 1c) umschalten.
 */
public class Stack {

    private final int[] items;
    private int size = 0;
    private final boolean useNotifyAll;

    public Stack(int capacity, boolean useNotifyAll) {
        if (capacity < 1) {
            throw new IllegalArgumentException("Kapazität muss >= 1 sein");
        }
        this.items = new int[capacity];
        this.useNotifyAll = useNotifyAll;
    }

    public void push(int item) throws InterruptedException {
        synchronized (this) {
            while (size == items.length) {      // Stack voll -> schlafen legen
                wait();
            }
            // Alle vorhandenen Elemente um eins nach hinten schieben und das
            // neue Element an vorderster Stelle einfügen.
            System.arraycopy(items, 0, items, 1, size);
            items[0] = item;
            size++;
            System.out.println(Thread.currentThread().getName()
                    + " push(" + item + ")  -> Füllstand " + size + "/" + items.length);
            wakeUp();
        }
    }

    public int pop() throws InterruptedException {
        synchronized (this) {
            while (size == 0) {                 // Stack leer -> schlafen legen
                wait();
            }
            // Element an vorderster Stelle entnehmen, Rest nach vorne schieben.
            int item = items[0];
            System.arraycopy(items, 1, items, 0, size - 1);
            size--;
            System.out.println(Thread.currentThread().getName()
                    + " pop()  = " + item + " -> Füllstand " + size + "/" + items.length);
            wakeUp();
            return item;
        }
    }

    /** Weckt einen (notify) bzw. alle (notifyAll) wartenden Threads. */
    private void wakeUp() {
        if (useNotifyAll) {
            notifyAll();
        } else {
            notify();
        }
    }
}
