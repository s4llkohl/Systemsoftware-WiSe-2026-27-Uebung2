public class Stack {
    private int stackLength = 1;
    private int[] stack = new int[stackLength];
    private int top = 0;

    public void push (int in) throws InterruptedException{
        synchronized (this){
            while (top == stack.length){
                wait();
            }
            stack[top] = in;
            top++;
            notifyAll();
        }
    }

    public int pop() throws InterruptedException{
        synchronized (this){
            while (top == 0) {
                wait();
            }
            top--;
            int out = stack[top];
            notifyAll();
            return out;
        }
    }
}
