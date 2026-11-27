import threading
import time
import random

counter = 0
lock = threading.Lock()

def register_visits():
    global counter

    for _ in range(1000):
        with lock:
            temp = counter

            if random.random() < 0.001:
                time.sleep(random.uniform(0, 0.00001))

            counter = temp + 1


threads = []

for _ in range(10):
    thread = threading.Thread(target=register_visits)
    threads.append(thread)
    thread.start()

for thread in threads:
    thread.join()

print(f"Erwartete Besucherzahl: {10000}")
print(f"Gezählte Besucherzahl: {counter}")