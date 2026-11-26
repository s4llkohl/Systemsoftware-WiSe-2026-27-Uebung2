
import threading

# -------------------------------------------------------
# Aufgabenteil a)
# -------------------------------------------------------

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

            self.stack[self.count] = item
            self.count += 1

            # ---------------------------
            # mit möglichem Deadlock
            # self.condition.notify()
            # ---------------------------
            # ohne Deadlock für c)
            self.condition.notify_all()
            # ---------------------------

    def pop(self):

        with self.condition:

            while self.count == 0:
                self.condition.wait()

            self.count -= 1
            item = self.stack[self.count]

            # ---------------------------
            # mit möglichem Deadlock
            # self.condition.notify()
            # ---------------------------
            # ohne Deadlock für c)
            self.condition.notify_all()
            # ---------------------------

            return item


# -------------------------------------------------------
# Aufgabenteil b)
# -------------------------------------------------------

class Producer(threading.Thread):

    def __init__(self, stack):
        super().__init__()
        self.stack = stack

    def run(self):

        for i in range(10):
            self.stack.push(i)
            print(f"Producer {self.name}: {i}")


class Consumer(threading.Thread):

    def __init__(self, stack):
        super().__init__()
        self.stack = stack

    def run(self):

        for i in range(10):
            item = self.stack.pop()
            print(f"Consumer {self.name}: {item}")


# -------------------------------------------------------
# Aufgabenteil c)
# Ausführung des Programms
# -------------------------------------------------------

if __name__ == "__main__":

    stack = Stack(1)

    producers = [Producer(stack) for _ in range(10)]
    consumers = [Consumer(stack) for _ in range(10)]

    for thread in producers + consumers:
        thread.start()

    for thread in producers + consumers:
        thread.join()

    print("Programm beendet.")
