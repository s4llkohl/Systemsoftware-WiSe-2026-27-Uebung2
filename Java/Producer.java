public class Producer extends Thread{
    private Stack stack;

    public Producer(Stack stackIn){
        this.stack = stackIn;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 10; i++){
                stack.push(i);
            }
        } catch (InterruptedException e){
            System.out.println(e.getStackTrace());
        }
    }
}
