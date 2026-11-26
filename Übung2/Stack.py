import threading
import time

class Stack:
    def __init__(self, size):
        self.stack = []
        self.size = size
        self.condition = threading.Condition()

    def push(self, item):
        with self.condition:
            while len(self.stack) == self.size:
                print("Stack ist voll. Producer wartet...")
                self.condition.wait()

            self.stack.insert(0, item)
            print(f"Push: {item}")

            self.condition.notify()

    def pop(self):
        with self.condition:
            while len(self.stack) == 0:
                print("Stack ist leer. Consumer wartet...")
                self.condition.wait()

            item = self.stack.pop(0)
            print(f"Pop: {item}")

            self.condition.notify()
            return item

class Producer(threading.Thread):
    def __init__(self, stack, producer_id):
        super().__init__()
        self.stack = stack
        self.producer_id = producer_id

    def run(self):
        for i in range(10):
            item = (self.producer_id, i)
            self.stack.push(item)


class Consumer(threading.Thread):
    def __init__(self, stack, consumer_id):
        super().__init__()
        self.stack = stack
        self.consumer_id = consumer_id

    def run(self):
        for i in range(10):
            item = self.stack.pop()
            print(f"Consumer {self.consumer_id} erhält: {item}")

# Möglichst kleiner Stack
stack = Stack(1)

producers = []
consumers = []

# 10 Producer erstellen
for i in range(10):
    producers.append(Producer(stack, i))

# 10 Consumer erstellen
for i in range(10):
    consumers.append(Consumer(stack, i))

# Alle Threads starten
for p in producers:
    p.start()

for c in consumers:
    c.start()

# Auf alle Threads warten
for p in producers:
    p.join()

for c in consumers:
    c.join()