public class Consumer extends Thread{
    private Stack stack;

    public Consumer(Stack stack){
        this.stack = stack;
    }

    @Override
    public void run(){
        for(int i = 1; i<= 10; i++){
            stack.pop();
            System.out.println(stack.toString());
        }
    }
}
