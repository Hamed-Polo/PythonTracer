public class CodeBlock {
    /**
     * BLOCK_TYPES is a array of strings for the types of blocks available
     * for nesting
     *
     * We also set int variables to the corresponding indices of the array
     *
     * blockComplexity - to keep track of the big oh complexity of the block
     *
     * highSubComplexity - to keep track of the big oh complexity of the
     * highest order block nested within a certain block.
     *
     * name - used to keep track of the nested structure of the blocks(1, 1
     * .1, 1.2, 1.3, etc)
     *
     * loopVariable - only used for while blocks, it's set to null and will
     * update when a while block is traced and the name of it;s loop variable
     * has been determined.
     */
    public static final String[] BLOCK_TYPES = {"def", "for", "while", "if",
            "elif", "else"};
    public static final int DEF = 0, FOR = 1, WHILE = 2, IF = 3, ELIF = 4,
            ELSE = 5;
    private Complexity blockComplexity;
    private Complexity highestSubComplexity;
    private String name;
    private String loopVariable;

    /**
     * Default constructor
     */
    public CodeBlock(){

    }

    /**
     * set blockComplexity to the given argument
     * @param blockComplexity - argument we'll use
     */
    public void setBlockComplexity(Complexity blockComplexity) {
        this.blockComplexity = blockComplexity;
    }

    /**
     * @return blockComplexity
     */
    public Complexity getBlockComplexity() {
        return blockComplexity;
    }

    /**
     * set highestSubComplexity to the given argument
     * @param highestSubComplexity - argument we'll use
     */
    public void setHighestSubComplexity(Complexity highestSubComplexity){
        this.highestSubComplexity = highestSubComplexity;
    }

    /**
     * @return highestSubComplexity
     */
    public Complexity getHighestSubComplexity(){
        return highestSubComplexity;
    }

    /**
     * set name to the given argument
     * @param name - argument we'll use
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return name
     */
    public String getName(){
        return name;
    }

    /**
     * set loopVariable to the given argument
     * @param loopVariable - argument we'll use
     */
    public void setLoopVariable(String loopVariable){
        this.loopVariable = loopVariable;
    }

    /**
     * @return loopVariable
     */
    public String getLoopVariable() {
        return loopVariable;
    }

    /**
     * @return a string format of the block's name with it's complexity and
     * highest sub-complexity
     *
     * It's not needed but will be very helpful
     */
    public String toString(){
        String p1 = String.format("%s%s",":       block complexity = ",
                blockComplexity.toString());

        String p2 = String.format("%s%s", "       highest sub-complexity" +
                " = ", highestSubComplexity.toString());

        return "BLOCK " + name + p1 + p2;
    }
}