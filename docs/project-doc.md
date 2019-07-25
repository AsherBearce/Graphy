# Final project documentation
At this point in the apps development, most of the functionality is written, however it's not 
implemented. The views are not completely connected to the database yet, and so it's not possible
to save or load much from the database queries. However, the graph editor is fully functioning and 
supports a few of the operations I wanted from the start of this project. Currently, the editor 
supports multiple variable functions, and graphing single variable functions. The parser also 
supports a few transcendental functions, such as sin, cos, and tan. It also supports natural logarithm
and log base 10. More excitingly, it supports taking derivatives of functions, and even partial derivatives
of multi-variable functions. 

It should also be noted that the editor may crash upon certain invalid inputs. 

# How to use the app
Upon first start, the user should be greeted by the editor screen. There will be a plus button located
just underneath the viewing window, that can be tapped to add a new input to the editor. Inputs can be 
defined one of two ways: 1.) simply defining an expression in terms of a single variable, such as 
"sin(x) * x". 2.) The user can specify the name of a function as well. These names can be multiple 
characters long. To define a function with a name, something such as "f(x) = sin(x) * x" will work.
Note that if you are writing a function with multiple inputs, those MUST be specified within the 
parenthesis next the function name. I.e "f(x, y, z) = ...". Those variables must also appear in the 
expression. To take derivatives, type in the word "derivative" as if you were calling a function.
The arguments must be in the order of first the expression or function you're taking the derivative of,
the variable to differentiate with respect to, and finally at what point to take said derivative at.
For example "derivative(f(x), x, x)" will parse correctly. To delete an input, there is an "X" icon
next to each input, tap it to remove your input. To get to the file navigation screen (which does not
currently function correctly), swipe to the right and click on the "Main Menu" option in the navigation
drawer. To get back to the editor, either open the the file from the "Open File" option, or choose
"new graph" in the navigation drawer.

# Documentation For this app
* [Entity relationship diagrams](ERD.md)
* [Wireframes](wireframes.md)
* [Javadocs](index.html)

# Documentation for the parser
You'll find all the code and documentation for my parser in the link below. All code from that repository
is packed as a jar and listed as a dependency.
* [Parser Repository](https://github.com/AsherBearce/GraphyParserCorev2)
* [GitHub pages](https://asherbearce.github.io/GraphyParserCorev2/)