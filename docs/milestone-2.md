## Summary of implementation
As of now, the app is capable of parsing and drawing multiple functions, and variables. Only functions
with one input parameter are drawn to the screen. There are also a few predefined functions that the user
can invoke, those being cos, sin, tan, and ln. Users can also add and delete functions as they choose
Functions and variables may also call on each other (e.g. an input like "h(x) = f(x) + g(x)" will 
draw the result of adding f(x) and g(x) together, as long as both functions are defined). 

## Walk-through
Upon loading a graph from a screen (once implemented), the app will automatically load all the data 
selected from the database into the text boxes, so they can then be drawn and handled. In order to 
define a function, the user must write something like "functionname(parameters) = ...". For now, upon 
loading the app, a user should start off with a few functions already loaded from the database that 
show off the features of the parser. 