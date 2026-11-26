public class Stack {
    private Integer[] stack;
    private int top;
    private int length;

    public Stack(int length){
        stack = new Integer[length];
        top = -1;
        this.length = length;
    }
    public synchronized void push(Integer item){
        while(top == length-1){
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        top++;
        stack[top] = item;
        notify();

    }

    public synchronized Integer pop(){
        while(top == -1){
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        Integer temp = stack[top];
        stack[top] = null;
        top--;
        notify();
        return temp;

    }


    @Override
    public String toString(){
        String output ="[ ";
        Integer temp;
        for(int i = 0 ; i < length; i++) {
            temp = stack[i];
           if(temp != null)
               output = output + temp ;
           else
               output += "__";

           if(i != length-1)
               output += " , ";

        }
        return output + " ]";
    }
}
