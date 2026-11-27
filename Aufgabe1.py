import threading

    
class Stack:

    def __init__(self, size):
        self.stack = [0] * size
        self.top = 0
        self.size = size
        self.condition = threading.Condition()

    def push(self, item):
        with self.condition:
            while self.top == self.size:
                self.condition.wait()
            self.stack[self.top] = item
            self.top += 1
            self.condition.notify_all()

    def pop(self):
        with self.condition:
            while self.top == 0:
                self.condition.wait()
            self.top -= 1
            item = self.stack[self.top]
            print(item)
            self.condition.notify_all()
            return item
        
class Producer(threading.Thread):
    def __init__(self, stack, ):
        super().__init__()
        self.stack = stack

    def run(self):
        for i in range(10):
            self.stack.push(i)


class Consumer(threading.Thread):
    def __init__(self, stack):
        super().__init__()
        self.stack = stack

    def run(self):
        for i in range(10):
            self.stack.pop()

counter = 0

stack = Stack(1)
threads = [Producer(stack) for _ in range(10)] + [Consumer(stack) for _ in range(10)]

for t in threads:
    t.start()
for t in threads:
    t.join()