
import threading

# a) Stack-Klasse
class Stack:
    def __init__(self, size):
        self.stack = [0] * size
        self.size = size
        self.count = 0
        self.condition = threading.Condition()

    def push(self, item):
        with self.condition:
            while self.count == self.size:
                self.condition.wait()

            # Elemente nach rechts verschieben
            for i in range(self.count, 0, -1):
                self.stack[i] = self.stack[i - 1]

            # Neues Element vorne einfügen
            self.stack[0] = item
            self.count += 1

            self.condition.notify_all()

    def pop(self):
        with self.condition:
            while self.count == 0:
                self.condition.wait()

            # Oberstes Element entfernen
            item = self.stack[0]

            # Übrige Elemente nach links verschieben
            for i in range(self.count - 1):
                self.stack[i] = self.stack[i + 1]

            self.count -= 1
            self.stack[self.count] = 0

            self.condition.notify_all()
            return item


# b) Producer
class Producer(threading.Thread):
    def __init__(self, stack, producer_id):
        super().__init__()
        self.stack = stack
        self.producer_id = producer_id

    def run(self):
        for i in range(10):
            item = i
            print(f"Producer {self.producer_id} fügt ein: {item}")
            self.stack.push(item)


# b) Consumer
class Consumer(threading.Thread):
    def __init__(self, stack, consumer_id):
        super().__init__()
        self.stack = stack
        self.consumer_id = consumer_id

    def run(self):
        for i in range(10):
            item = self.stack.pop()
            print(f"Consumer {self.consumer_id} entnimmt: {item}")


# Gemeinsamer Stack mit kleiner Kapazität
stack = Stack(2)

producers = []
consumers = []

# Zehn Producer erstellen
for i in range(10):
    producers.append(Producer(stack, i))

# Zehn Consumer erstellen
for i in range(10):
    consumers.append(Consumer(stack, i))

# Producer starten
for producer in producers:
    producer.start()

# Consumer starten
for consumer in consumers:
    consumer.start()

# Auf alle Threads warten
for producer in producers:
    producer.join()

for consumer in consumers:
    consumer.join()

print("Alle Threads wurden beendet.")
