import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int auswahl = -1;

        while (auswahl != 0 && auswahl != 1) {
            System.out.print("0 = notify(), 1 = notifyAll(): ");
            String eingabe = scanner.nextLine().trim();
            if (eingabe.equals("0")) {
                auswahl = 0;
            } else if (eingabe.equals("1")) {
                auswahl = 1;
            } else {
                System.out.println("Fehler: Bitte nur 0 oder 1 eingeben.");
            }
        }

        Stack stack = new Stack(1, auswahl == 1);

        for (int i = 0; i < 10; i++) {
            new Producer(stack).start();
            new Consumer(stack).start();
        }
    }
}