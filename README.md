# PythonTracer
Most programming languages are organized as structured blocks of statements, with some blocks nested within others. Functions, which are examples of such blocks, execute statements and other blocks contained within them. Similarly, flow control structures, such as for and while loops, are blocks which can be executed several times subject to some condition. The Python programming language is an example of a language which follows this principle, and is even flexible enough to allow functions to be nested within functions.  I’m creating a code tracer program which takes the name of a Python file containing a single function and outputs the Big-Oh order of complexity of that function. To make things easier, several restrictions will be made on the format of the input code, and some techniques for text parsing will be described below. I implemented a BlockStack class to determine the complexity of blocks with nested blocks, and used the rules of Big-Oh complexity to determine the total complexity for the function.
