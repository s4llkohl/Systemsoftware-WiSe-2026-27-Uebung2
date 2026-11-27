# Systemsoftware WiSe 2026/27 – Übung 2

**Name:** _bitte eintragen_

Aufgabe 1 ist in Java gelöst (`Java/Aufgabe1/`). Für Aufgabe 2 wurden beide Varianten (Java und Python) korrigiert.

---

## Aufgabe 1

### a) und b) Stack, Producer, Consumer

| Datei | Inhalt |
|---|---|
| `Java/Aufgabe1/Stack.java` | Beschränkter Stack auf Basis eines `int[]` mit `push()` und `pop()` |
| `Java/Aufgabe1/Producer.java` | Thread, der zehn Mal `push()` mit seinem Zählerstand 0–9 aufruft |
| `Java/Aufgabe1/Consumer.java` | Thread, der zehn Mal `pop()` aufruft |
| `Java/Aufgabe1/Main.java` | Startet je zehn Producer und Consumer auf demselben Stack |

- `push()` und `pop()` laufen jeweils in einem `synchronized (this)`-Block.
- Der Zustand (voll bzw. leer) wird in einer `while`-Schleife geprüft. Ist der Stack voll (bei `push`) oder leer (bei `pop`), legt sich der Thread mit `wait()` schlafen.
- Nach jeder Änderung wird mit `notify()` ein wartender Thread geweckt.
- Eingefügt und entnommen wird immer an vorderster Stelle (Index 0). Die übrigen Elemente werden mit `System.arraycopy` verschoben.

Ausführen:

```bash
cd Java/Aufgabe1
javac *.java
java Main                # Kapazität 1, notify()    -> Deadlock (Aufgabe 1c)
java Main 1 notifyAll    # Kapazität 1, notifyAll() -> korrigierte Version
```

`Main` wartet höchstens fünf Sekunden auf die Threads. Hängen danach noch Threads, meldet das Programm einen Deadlock und listet die Threads auf, die noch warten.

### c) Deadlock mit möglichst kleinem Stack

**Beobachtung:** Mit Kapazität 1 und `notify()` endeten in unseren Tests 20 von 20 Läufen im Deadlock. Zum Schluss stehen alle 20 Threads im Zustand `WAITING`:

```
DEADLOCK: folgende Threads schlafen und werden nie mehr geweckt:
  Producer-0 (WAITING)
  ...
  Consumer-9 (WAITING)
```

**Wie es dazu kommt:** Alle Producer und Consumer warten im *selben* Wait-Set (dem Monitor des Stack-Objekts). `notify()` weckt davon genau *einen beliebigen* Thread. Es kann also auch ein Thread der „falschen“ Sorte geweckt werden. Ein Beispielablauf mit Kapazität 1:

1. Der Stack ist voll. Mehrere Producer liegen in `wait()`.
2. Ein Consumer nimmt das Element heraus und ruft `notify()` auf. Geweckt wird aber nicht ein Producer, sondern ein anderer Consumer.
3. Dieser Consumer sieht in der `while`-Schleife, dass der Stack leer ist, und legt sich wieder schlafen, *ohne* jemanden zu wecken.
4. Das Wecksignal ist damit verbraucht, und kein Producer hat davon erfahren. Wiederholt sich das, schlafen irgendwann alle Threads. Keiner kann mehr einen anderen wecken, und das Programm steht.

**Begründung über die Coffman-Bedingungen** (alle vier müssen gleichzeitig gelten):

1. **Wechselseitiger Ausschluss (Mutual Exclusion):** Den Monitor des Stacks (`synchronized`) kann immer nur ein Thread halten. Auch der einzige Stackplatz kann nur von einem Element belegt sein: Entweder ist er frei (das brauchen die Producer) oder voll (das brauchen die Consumer).
2. **Halten und Warten (Hold and Wait):** Jeder Thread steckt mitten in seiner Operation. Er hält seinen Fortschritt (der Producer sein noch nicht abgelegtes Element, der Consumer seinen noch offenen `pop()`-Aufruf) und wartet auf ein Ereignis, das nur ein anderer Thread auslösen kann: einen freien Platz bzw. ein vorhandenes Element samt Wecksignal.
3. **Keine Verdrängung (No Preemption):** Ein schlafender Thread kann von außen nicht gezwungen werden, weiterzumachen oder aufzugeben. Weder das Laufzeitsystem noch ein anderer Thread nimmt ihm etwas weg. Aus `wait()` kommt er nur heraus, wenn ein anderer Thread ihn per `notify()` weckt.
4. **Zirkuläres Warten (Circular Wait):** Die Producer warten darauf, dass ein Consumer einen Platz freigibt und sie weckt. Die Consumer warten darauf, dass ein Producer ein Element ablegt und sie weckt. Weil jeweils der Thread geweckt werden müsste, der selbst schon schläft, entsteht der Kreis Producer → Consumer → Producer.

Mit Kapazität 1 tritt das besonders schnell auf, weil der Stack fast ständig voll oder leer ist und praktisch jeder Aufruf in `wait()` führt. Je größer der Stack, desto seltener kommt es dazu.

*Anmerkung:* Genau genommen hält im Endzustand niemand den Monitor, denn `wait()` gibt ihn frei. Die eigentliche Ursache ist ein **verlorenes Wecksignal** (Lost Wake-up). Das zyklische Warten entsteht hier also über die Bedingungsvariable und nicht über eine Sperre.

**Korrektur mit `notifyAll()`:** In `Stack.wakeUp()` wird statt `notify()` jetzt `notifyAll()` aufgerufen (`java Main 1 notifyAll`). Dann werden bei jeder Änderung *alle* wartenden Threads geweckt. Jeder prüft seine Bedingung in der `while`-Schleife erneut. Wer nicht weitermachen kann, legt sich wieder hin. Mindestens ein passender Thread (ein Producer, wenn Platz frei ist, oder ein Consumer, wenn ein Element da ist) kommt aber garantiert zum Zug, und ein Wecksignal kann nicht mehr verloren gehen. Damit ist die Bedingung „zirkuläres Warten“ gebrochen. **Ergebnis: 0 von 100 Testläufen mit Deadlock.** Jeder Lauf meldet „Alle 100 push()- und 100 pop()-Aufrufe erfolgreich beendet.“

Der Nachteil von `notifyAll()` ist, dass viele Threads unnötig aufwachen und gleich wieder schlafen gehen (Thundering Herd). Effizienter wären zwei getrennte Bedingungsvariablen, zum Beispiel `notFull` und `notEmpty` mit `ReentrantLock`/`Condition`. Dann könnte man mit `signal()` gezielt einen Thread der richtigen Sorte wecken.

---

## Aufgabe 2

### a) Data Race und Race Condition

**Data Race:** Mindestens zwei Threads greifen gleichzeitig auf dieselbe Speicherstelle zu, mindestens einer davon schreibt, und die Zugriffe sind nicht synchronisiert (keine Sperre, keine atomare Operation, keine Happens-before-Beziehung). Das Ergebnis eines solchen Zugriffs ist undefiniert bzw. nicht vorhersagbar. Ein Data Race ist eine Eigenschaft der *Speicherzugriffe*.

**Race Condition:** Das Ergebnis eines Programms hängt von der zeitlichen Reihenfolge (dem Interleaving) ab, in der die Threads ausgeführt werden. Je nach Scheduling ist das Ergebnis mal richtig und mal falsch. Eine Race Condition ist ein *logischer* Fehler im Programmablauf.

**Zusammenhang:** Data Races führen oft zu Race Conditions, wie hier beim Zähler. Die Begriffe sind aber nicht gleich. Eine Race Condition kann auch ohne Data Race entstehen, zum Beispiel bei „Check-then-Act“, wenn die Prüfung und die Aktion einzeln synchronisiert sind, aber nicht gemeinsam. Umgekehrt führt nicht jedes Data Race zu einem falschen Ergebnis.

### b) Was beim Besucherzähler schiefgeht

Beobachtete Ergebnisse des Originalprogramms (erwartet: 10000):

| Lauf | Java | Python |
|---|---|---|
| 1 | 3838 | 5451 |
| 2 | 1799 | 5535 |
| 3 | 4481 | 8380 |
| 4 | 4497 | 7709 |
| 5 | 5012 | 5359 |

Das Ergebnis ist immer zu klein und bei jedem Lauf anders.

**Ursache:** Das Hochzählen besteht aus drei getrennten Schritten, die nicht atomar ablaufen:

```
temp = counter        // 1. lesen
(evtl. sleep)         // 2. Zeitfenster
counter = temp + 1    // 3. schreiben
```

Die zehn Threads greifen ohne jede Synchronisation gleichzeitig auf die gemeinsame Variable `counter` zu: ein Data Race. Liest Thread A `counter = 100` und wird dann unterbrochen, können andere Threads in der Zwischenzeit weiterzählen, etwa auf 150. Wenn A weiterläuft, schreibt er seinen veralteten Wert `temp + 1 = 101` zurück und überschreibt damit alle 50 Zählungen der anderen. Diese Besuche sind verloren (**Lost Update**).

Der eingebaute `sleep` verschärft das Problem gezielt. Er schläft zwar nur selten (Wahrscheinlichkeit 0,1 %), aber genau *zwischen* Lesen und Schreiben. So wird das Zeitfenster vergrößert, in dem andere Threads dazwischenkommen. Ein Thread, der dort schläft, kann beim Aufwachen auf einen Schlag sehr viele Zählungen anderer Threads überschreiben. Deshalb fehlen nicht nur einzelne, sondern teils Tausende Besuche.

Weil das Scheduling bei jedem Lauf anders ist, ist auch das Ergebnis jedes Mal anders. Das ist eine Race Condition.

- *Java:* Ohne Synchronisation gibt es außerdem keine Sichtbarkeitsgarantie. Ein Thread kann einen veralteten Wert von `counter` aus Cache oder Register sehen.
- *Python:* Das GIL hilft hier nicht. Es schützt nur einzelne Bytecode-Instruktionen, und der Interpreter kann zwischen den Zeilen den Thread wechseln. `time.sleep()` gibt das GIL sogar ausdrücklich frei.

### c) Korrektur

Lesen, Sleep und Schreiben werden zu einem **kritischen Abschnitt** zusammengefasst, den durch gegenseitigen Ausschluss (Mutex/Monitor) immer nur ein Thread betreten darf. Der `sleep`-Timer bleibt unverändert und liegt jetzt *innerhalb* des geschützten Bereichs. Dadurch kann während des Schlafens kein anderer Thread den Zähler verändern.

**Java** (`Java/visitor_count_simulation.java`):

```java
static final Object lock = new Object();
...
synchronized (lock) {
    int temp = counter;
    if (random.nextDouble() < 0.001) {
        ... Thread.sleep(0, random.nextInt(10000)); ...   // unverändert
    }
    counter = temp + 1;
}
```

**Python** (`Python/visitor_count_simulation.py`):

```python
counter_lock = threading.Lock()
...
with counter_lock:
    temp = counter
    if random.random() < 0.001:
        time.sleep(random.uniform(0, 0.00001))   # unverändert
    counter = temp + 1
```

`synchronized` bzw. `with lock:` sorgt außerdem dafür, dass die Sperre auch bei einer Exception (bzw. beim `return` im `catch`) wieder freigegeben wird. In Java stellt die Sperre zusätzlich die Sichtbarkeit der Änderungen zwischen den Threads sicher (Happens-before).

**Ergebnis:** In je 20 Läufen lieferten Java und Python jedes Mal `Gezählte Besucherzahl: 10000`.
