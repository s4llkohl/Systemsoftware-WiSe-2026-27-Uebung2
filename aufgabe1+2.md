## Aufgabe 1c

Die vier Coffman-Bedingungen für einen Deadlock sind:

- Mutual Exclusion: Eine Ressource kann nur von einem Thread gleichzeitig genutzt werden.
- Hold and Wait: Ein Thread hält eine Ressource und wartet auf eine weitere.
- No Preemption: Ressourcen können nicht zwangsweise entzogen werden.
- Circular Wait: Threads warten in einem Kreislauf aufeinander.

Bei einem Stack der Größe 1 kann ein Deadlock entstehen, da notify()
auch einen Thread aufwecken kann, der aufgrund des Stackzustands
nicht weiterarbeiten kann. Dadurch können alle Threads dauerhaft warten.

Die Coffman-Bedingungen sind hier nicht vollständig erfüllt,
da wait() die gehaltene Sperre freigibt.

Durch notifyAll() werden alle wartenden Threads aufgeweckt,
sodass auch ein Thread weiterarbeiten kann, für den die
entsprechende Bedingung erfüllt ist.

## Aufgabe 2a

- **Data Race:** Mehrere Threads greifen ohne Synchronisation
  auf dieselbe Variable zu, wobei mindestens einer schreibt.
  Dadurch können Änderungen am Wert verloren gehen.

- **Race Condition:** Der endgültige Wert hängt von der
  Reihenfolge ab, in der die Threads ihre Operationen
  ausführen. Dadurch können unterschiedliche oder falsche
  Ergebnisse entstehen.

## Aufgabe 2b

Bei fünf Ausführungen wurden folgende Besucherzahlen ermittelt:
641, 2225, 3148, 1230 und 1890.

Erwartet werden jedoch 10000 Besucher.

Mehrere Threads können gleichzeitig denselben Wert von counter
in temp einlesen. Wenn sie anschließend den Wert um eins erhöhen
und zurückschreiben, überschreiben sie gegenseitig ihre Änderungen.


