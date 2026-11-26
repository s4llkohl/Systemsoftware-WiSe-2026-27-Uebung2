
public class Producer extends Thread{
    private Stack stack;

    public Producer(Stack stack){
        this.stack = stack;
    }

    @Override
    public void run(){
        for(int i = 1; i<= 10 ; i++){
            stack.push(i);
            System.out.println(stack.toString());
        }
    }
}
