# a)
Ein Datarace entsteht wenn mehrere Threads auf den gleichen Speicher zugreifen und mindestens einer davon ein Schreibzugriff ist. Bei fehlerhafter Sychronisation kann es passieren, dass zum Beispiel mehrere Threads zeitgleich eine Variable inkrementieren wollen und dabei mehrere den Gleichen Zählerschritt machen, statt aufeinander folgende Schritte.  
Ein Beispiel hierzu wäre der Datarace beim hochzählen mit mehreren Threads ohne Sychronisation aus der Vorlesung.  

Eine Racecondition beschreibt wenn ein Thread von den Ergebnissen eines anderen abhängt. Angenommen wir haben einen Thread der hochzählt und einen zweiten, der ausließt. 
Abhängig davon, ob unser Thread der ausließt jetzt vor oder nach dem Hochzählen dran kommt, erhalten wir bei dem ausführen unterschiedliche Ergebnisse, auch wenn wir gleiche erwarten würden.

# b)
Das Beispiel ist ein klassischer Fall eines Datarace. Mehrere Threads starten, nehmen sich jeweils den aktuellen wert und erhöhen diesen, bevor sie ihn wieder eintragen.  
In jedem Durchgang wird dabei temp = count verwendet um den globalen Wert von Count auszulesen.  
Anschließend wird per Zufall entschieden ob ein kurzes sleep aufgerufen wird.  
Zuletzt wird count mit temp + 1 überschrieben.  
Jeder Thread der dabei den Wert von count ließt, während unser erster Thread gerade im sleep ist (oder einfach zwischen *temp = count* und *count = temp + 1* pausiert wird) übernimmt den selben Wert wie unser erster Thread. Beide führen also den selben Fall aus (oder überschreiben im worst case sogar count mit einem niedrigeren Wert, falls die anderen Threads es schaffen sogar 2 Mal oder öfter durch die Schleife zu laufen, bevor unser Thread zum Eintragen kommt.)

