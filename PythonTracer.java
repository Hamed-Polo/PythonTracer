import java.io.File;
import java.util.Scanner;


// Driver class
public class PythonTracer {
    /**
     * Prompts the user for the name of a file containing a single Python
     * function, determines its order of complexity, and
     * prints the result to the console.
     * @param args - arguments of strings used for the main method
     */
    public static void main(String[] args) {
        Scanner h = new Scanner(System.in);
        String filename = "";

        // while filename does not equal to quit
        while (!filename.equalsIgnoreCase("quit")) {
            // print this statement
            System.out.print("Please enter a file name (or 'quit' to quit): ");
            filename = h.nextLine();

            // if the user types in quit
            if (filename.equalsIgnoreCase("quit")) {
                // print this statement
                System.out.print("\n" + "Program terminating successfully...");
                // and quit the program
                break;
            }
            else {
                try {
                    // else try to use the traceFile method on whatever the
                    // user typed and print out the overall complexity
                    Complexity result = traceFile(filename);
                    System.out.println("\n" + "Overall complexity of " +
                            filename.substring(0, filename.length() - 3) +
                            ": " + result + "\n");
                }
                catch (Exception e) {
                    System.out.print(", try again." + "\n");
                }
            }
        }
    }

    // used to determine the indentation of each statement
    public static final int SPACE_COUNT = 4;

    /**
     * Opens the indicated file and traces through the code of the Python
     * function contained within the file, returning the Big-Oh order of
     * complexity of the function. During operation, the stack trace should be
     * printed to the console as code blocks are pushed to/popped from the
     * stack.
     * @param filename - file argument we'll use
     * @return - A Complexity object representing the total order of complexity
     * of the Python code contained within the file.
     */
    public static Complexity traceFile(String filename) {
        // creating empty stack
        BlockStack<CodeBlock> stack = new BlockStack<>();
        // creating a variable for the highest complexity
        Complexity highestComplexity = new Complexity();

        try {
            // h is the scanner object for the file "filename"
            Scanner h = new Scanner(new File(filename));
            // the data for the file
            String fileData;
            // the number of indentations
            int indents, spaces;
            // to see whether it's a new block or not
            boolean block, updated;
            // to see whether it's the first block or not
            boolean firstBlock = true;
            // to keep track of the name for the blocks
            int num = 1;

            // while the scanner has the next line
            while (h.hasNextLine()) {
                fileData = h.nextLine();

                // if it's length is 0 or empty, continue
                if (fileData.equalsIgnoreCase("") ||
                        fileData.length() == 0) {
                    continue;
                }
                // if we found an # or "\"
                if (fileData.contains("#") ||
                        fileData.contains("\"")) {
                    // set the num to 1
                    num = 1;
                }
                else {
                    // else count the spaces
                    spaces = 0;

                    for (int d = 0; d < fileData.length(); d++) {
                        if (fileData.charAt(d) == ' ') {
                            spaces += 1;
                        }
                        else {
                            break;
                        }
                    }
                    // divide it by the space count and sent both block and
                    // the number of indents to 1
                    indents = spaces / SPACE_COUNT;
                    block = indents == 1;

                    // if the file has def
                    if (fileData.contains("def")) {
                        // create a new block, it's complexity and it's
                        // highestSubComplexity objects
                        CodeBlock codeBlock = new CodeBlock();
                        Complexity blockComplexity = new Complexity();
                        Complexity highestSubComplexity = new Complexity();

                        // set the complexity and highestSubComplexity to the
                        // objects made
                        codeBlock.setBlockComplexity(blockComplexity);
                        codeBlock.setHighestSubComplexity
                                (highestSubComplexity);
                        // set the name of the block
                        codeBlock.setName("1");
                        // push the block into the stack
                        stack.push(codeBlock);

                        System.out.print("\n" + "Entering block " +
                                codeBlock.getName() + " 'def' :" + "\n" +
                                "\t" + codeBlock.toString() + "\n");
                    }
                    // if the file has for
                    else if (fileData.contains("for") || fileData.contains(
                            "elif") || fileData.contains("if") ||
                            fileData.contains("else") ||
                            fileData.contains("while")) {
                        String name;
                        if (fileData.contains("for")){
                            name = "for";
                        }
                        else if (fileData.contains("elif")){
                            name = "elif";
                        }
                        else if (fileData.contains("if")){
                            name = "if";
                        }
                        else if (fileData.contains("while")){
                            name = "while";
                        }
                        else{
                            name = "else";
                        }
                        // pretty much do the same steps
                        CodeBlock codeBlock = new CodeBlock();
                        Complexity blockComplexity = new Complexity();
                        Complexity highestSubComplexity = new Complexity();

                        codeBlock.setBlockComplexity(blockComplexity);
                        codeBlock.setHighestSubComplexity
                                (highestSubComplexity);

                        // if it's the first block, set the name and turn
                        // first block to false
                        if (firstBlock) {
                            codeBlock.setName("1." + num);
                            firstBlock = false;
                        }
                        // else if it's just another block
                        else if (block) {
                            num += 1; // add 1 to num
                            codeBlock.setName("1." + num); // set the name

                            // while the top of the stack's name doesn't
                            // equal to one
                            while (!stack.peek().getName().equalsIgnoreCase(
                                    "1")) {
                                // create an object for the the old top and
                                // the current top
                                CodeBlock oldTop = stack.pop();
                                Complexity current =
                                        stack.peek().getHighestSubComplexity();
                                updated = false; // haven't updated the block
                                // yet

                                // compare the current top and the old top's
                                // nPower's
                                if (current.getNPower() < oldTop.
                                        getBlockComplexity().getNPower()) {
                                    // if the current nPower is less then the
                                    // old top's nPower, set the current
                                    // nPower to the sum of both of their
                                    // nPowers
                                    current.setNPower(oldTop.
                                            getBlockComplexity().getNPower() +
                                            current.getNPower());
                                    // set the highest complexity to current
                                    highestComplexity = current;
                                    // set updated to true since it has been
                                    // updated
                                    updated = true;
                                }
                                // we do the same for the log power
                                if (current.getLogPower() < oldTop.
                                        getBlockComplexity().getLogPower()) {
                                    current.setLogPower(oldTop.
                                            getBlockComplexity().getLogPower()
                                            + current.getLogPower());
                                    highestComplexity = current;
                                    updated = true;
                                }
                                // we also do the same for the
                                // highestSubComplexity but this time, it's
                                // if the current's nPower or logPower is
                                // less than or equal to the old top's nPower
                                // or logPower
                                if (current.getNPower() <= oldTop.
                                        getHighestSubComplexity().getNPower())
                                {
                                    current.setNPower(oldTop.
                                            getHighestSubComplexity().
                                            getNPower() + current.getNPower());
                                    highestComplexity = current;
                                    updated = true;
                                }
                                if (current.getLogPower() <= oldTop.
                                        getHighestSubComplexity().
                                        getLogPower()) {
                                    current.setLogPower(oldTop.
                                            getHighestSubComplexity().
                                            getLogPower() +
                                            current.getLogPower());
                                    highestComplexity = current;
                                    updated = true;
                                }
                                // if the block has been updated
                                if (updated) {
                                    // set the top of the stack's value
                                    // highestSubComplexity to current
                                    stack.peek().setHighestSubComplexity
                                            (current);
                                    // then print you leaving the block while
                                    // updating
                                    System.out.print("\n" + "Leaving block " +
                                            oldTop.getName() + ", updating " +
                                            "block " + stack.peek().getName()
                                            + ":" + "\n");
                                }
                                else {
                                    // else just leave the block with nothing
                                    // to update
                                    System.out.print("\n" + "Leaving block " +
                                            oldTop.getName() + ", nothing " +
                                            "to update." + "\n");
                                }
                                // print out the complexity and the
                                // highestSubComplexity of the block that
                                // you're in
                                System.out.print("\n" + "\t" +
                                        stack.peek().toString() + "\n");
                            }
                        }
                        // if neither, set the name back to it's past name
                        else {
                            codeBlock.setName(String.format("%s.%d",
                                    stack.peek().getName(), num));
                        }
                        String loopV =
                                Character.toString(fileData.trim().charAt(6));
                        codeBlock.setLoopVariable(loopV);
                        // if the file contains and N then set the nPower of
                        // the complexity to 1
                        if (fileData.contains(" N")) {
                            codeBlock.getBlockComplexity().setNPower(1);
                        }
                        // if the file contains log_N then set the logPower
                        // of the complexity to 1
                        if (fileData.contains("log_N")) {
                            codeBlock.getBlockComplexity().setLogPower(1);
                        }
                        // push the block to the stack
                        stack.push(codeBlock);
                        System.out.print("\n" + "Entering block " +
                                codeBlock.getName() + " '" + name + "' :" +
                                "\n" + "\t" + codeBlock.toString() + "\n");
                    }
                    // now we're checking if the file has the loop variable
                    // plus the contents "+=" or "-="
                    else if (fileData.contains(stack.peek().
                            getLoopVariable() + " +=") || fileData.
                            contains(stack.peek().getLoopVariable() + " -=")) {
                        // if so, set the top stack's block complexity's
                        // nPower to the nPower plus 1
                        Complexity peek = stack.peek().getBlockComplexity();
                        peek.setNPower(peek.getNPower() + 1);
                        // we found an updated statement
                        System.out.println("\n" + "Found update statement, " +
                                "updating block " + stack.peek().getName() +
                                ":" + "\n" + "\t" + stack.peek().toString());
                    }
                    // if the file has the loop variable plus "*=" or "/="
                    else if (fileData.contains(stack.peek().
                            getLoopVariable() + " *=") || fileData.
                            contains(stack.peek().getLoopVariable() + " /=")) {
                        // set the top stack's block complexity's logPower to
                        // the logPower plus 1
                        Complexity peek = stack.peek().getBlockComplexity();
                        peek.setLogPower(peek.getLogPower() + 1);
                        // this is also an updated statement
                        System.out.println("\n" + "Found update statement, " +
                                "updating block " + stack.peek().getName() +
                                ":" + "\n" + "\t" + stack.peek().toString());
                    }
                }
            }
            // after all the checking, close the scanner/file
            h.close();
        }
        catch (Exception e) {
            System.out.print("File does not exist or command is invalid");
        }
        // if the toString of the stack's peek does not equal to one
        if (!stack.peek().toString().equalsIgnoreCase("1")) {
            // we do the same method/implementation
            while (!stack.peek().getName().equalsIgnoreCase(
                    "1")) {
                // create an object for the oldTop
                CodeBlock oldTop = stack.pop();
                // create an object for the currentTop
                Complexity current =
                        stack.peek().getHighestSubComplexity();
                // set updated to false
                boolean updated = false;

                // comparing the current top's nPower to the old top's
                // block complexity's nPower
                if (current.getNPower() < oldTop.getBlockComplexity().
                        getNPower()) {
                    current.setNPower(oldTop.getBlockComplexity().getNPower() +
                            current.getNPower());
                    highestComplexity = current;
                    updated = true;
                }
                // comparing the current top's logPower to the old top's
                // block complexity's logPower
                if (current.getLogPower() < oldTop.getBlockComplexity().
                        getLogPower()) {
                    current.setLogPower(oldTop.getBlockComplexity().
                            getLogPower() + current.getLogPower());
                    highestComplexity = current;
                    updated = true;
                }
                // comparing the current top's nPower to the old top's
                // highestSubComplexity's nPower
                if (current.getNPower() <= oldTop.getHighestSubComplexity().
                        getNPower()) {
                    current.setNPower(oldTop.getHighestSubComplexity().
                            getNPower() + current.getNPower());
                    highestComplexity = current;
                    updated = true;
                }
                // comparing the current top's logPower to the old top's
                // highestSubComplexity logPower
                if (current.getLogPower() <= oldTop.getHighestSubComplexity().
                        getLogPower()) {
                    current.setLogPower(oldTop.getHighestSubComplexity().
                            getLogPower() + current.getLogPower());
                    highestComplexity = current;
                    updated = true;
                }
                // if updated then set the peek's highestSubComplexity to
                // current
                if (updated) {
                    stack.peek().setHighestSubComplexity(current);
                    System.out.print("\n" + "Leaving block " +
                            oldTop.getName() + ", updating " +
                            "block " + stack.peek().getName()
                            + ":" + "\n");
                }
                // else just print that you're leaving the block and that
                // there's nothing to update
                else {
                    System.out.print("\n" + "Leaving block " + oldTop.getName()
                            + ", nothing to update." + "\n");
                }
                // printing out the toString to tell the complexity for the
                // block complexity and the highestSubComplexity
                System.out.print("\t" + stack.peek().toString() + "\n");
            }
        }
        System.out.print("\n" + "Leaving block " + stack.peek().getName() + "."
                + "\n");
        // after all the checking, set the peek's highestSubComplexity to the
        // highestPoint variable which contains the overall complexity of the
        // file
        stack.peek().setHighestSubComplexity(highestComplexity);
        // return that overall/highest complexity of the file
        return stack.pop().getHighestSubComplexity();
    }
}