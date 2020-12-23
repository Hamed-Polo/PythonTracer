
// Made up stack class

public class BlockStack<CodeBlock> {
    /**
     * value is for whatever value in the stack
     * prev is for the previous value in the stack
     */
    CodeBlock value;
    BlockStack<CodeBlock> prev;

    /**
     * default constructor
     */
    BlockStack(){

    }

    /**
     * second constructor with arguments to use
     * @param prev - argument to set to prev
     * @param value - argument to set to value
     */
    BlockStack(BlockStack<CodeBlock> prev, CodeBlock value){
        this.prev = prev;
        this.value = value;
    }

    /**
     * Implementing our push method
     * @param value - argument used to push a value into the stack
     */
    public void push(CodeBlock value){
        this.prev = new BlockStack<>(this.prev, this.value);
        this.value = value;
    }

    /**
     * @return if the stack is empty or not
     */
    public boolean isEmpty(){
        return this.prev == null;
    }

    /**
     * Implementing our pop method
     * @return the removed value at the top of the stack
     */
    public CodeBlock pop(){
        if (this.isEmpty()){
            throw new IllegalArgumentException("Stack is empty.");
        }
        CodeBlock top = this.value;
        this.value = this.prev.value;
        this.prev = this.prev.prev;
        return top;
    }

    /**
     * @return the top value of the stack without removing it from the stack
     */
    public CodeBlock peek(){
        return this.value;
    }

    /**
     * @return the size of the stack
     */
    public int size(){
        int total;

        if (isEmpty()){
            total = 0;
        }
        else{
            total = 1 + this.prev.size();
        }
        return total;
    }
}