Aufgabe 1c

Coffman-Bedingungen

Ein Deadlock tritt nur auf, wenn alle vier Bedingungen erfüllt sind:
1. Wechselseitiger Ausschluss
    Nur ein Thread darf gleichzeitig auf den Stack zugreifen, weil der Lock der Condition ihn schützt.

2. Hold and Wait
    Ein Thread wartet mit wait() darauf, dass der Stack nicht mehr voll (Producer) oder nicht mehr leer (Consumer) ist. Weiterarbeiten kann er erst, wenn er geweckt wird.

3. Keine Verdrängung
    Ein Thread kann nur durch notify() von einem anderen Thread geweckt werden.

4. Zyklisches Warten
    Producer warten auf Consumer und umgekehrt. Weil notify() auch einen Thread der eigenen Grupppe wecken kann, schlafe am Ende alle, und keiner kann mehr geweckt werden.

Aufgabe 2a

Data Race
Ein Data Race passiert, wenn mindestens zwei Threads gleichzeitig auf dieselbe Speicherstelle zugreifen. Mindestens einer davon schreibt und die Zugriffe sind nicht synchronisiert. 

Race Condition
Eine Race Condition kommt zu stande durch die zeitliche Reihenfolge des Programms, in der die Threads durchgeführt werden. Je nach Ablauf kommt mal das Richtige und mal das Falsche raus. 

Aufgabe 2b
Zehn Threads erhöhen gleichzeitig dieselbe Variable counter und das ohne Lock. Dadurch kann es passieren das Threads veraltete Werte einlesen, die eigentlich bereits erhöht wurden, dadurch geht dann eine Erhöhung verloren. 