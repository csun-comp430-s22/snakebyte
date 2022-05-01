# SNAKEBYTE
## BY Karim Atalla, Jack Konyan, Javier Parra, Ruitao Wu
---
## TARGET LANGUAGE
We are targeted Python as our destination because it is object-oriented language and functional programming language. Our group implemented JAVA language because it is the mutual language that we all familiar with. java and python have many similar feature that could save a lot of time during the development. The goal of this project is to help us to understand the mechanism of source-to-source compiler.
## LIMITATIONS
1. Not include much built-in method for list/array such as the `append()` method is not supported by our compiler
2. Third party library also not supported
## FEATURES
### Subtyping
The first feature that we have is subtyping an example as shown below
```
Type type = new Int();
```
### Casting  
Second feature that we have is casting which could convert a variable to different type an example as shown below
```
Int test1 = 1;
Str test2 = (Str)test1;
```
### Define method not use Python class

## SYNTAX<br>
```  
var variable name  
classname custom object  
functionname is a name of a function  
Str is string  
Boolean ::= true | false  
boolean_value ::= `true` | `false`  
i is an integer  
s is a String  
b is Boolean  
Int Integers   
type ::= Int | Boolean | Str | type[ ]   
exp  is an expression  
vardec ::= type var  variable declaration     
Op::= +| - | * | / | > | < | = | == | >= | <=    arithmetic, assignment & comparison operators  
exp ::= exp Op exp | exp.functionname(exp*) method call  
	
exp ::= i | s| b | var  
|this | Refers to my instance  
|super refers to superclass (parent) objects
|var=exp  
| new classname(exp*)  new instance of a class  
| (type)exp Casts an expression as a type   
| new type [exp] | exp[exp] | exp[exp]   = exp  Variables, strings, arrays and integers  
statement ::= type var ‘=’ exp ‘;’| variable assignment   
while (exp) statement | while loops   
{ statement * } | block  
break; | break  
 if (exp) statement else statement | if/else conditional  
 		return exp; | return an expression  
		print(exp) | Prints something to the terminal   
methoddef ::= type methodname (vardec*) statement | zero or more parameter   
extension ::= epsilon | `extends` classname   
classdef ::= `class` classname extension {   
		vardec*  
methoddef * | method zero or more  
classname(vardec*){ statement*  }   refers to more than one,           
             use comma to separate different variable   
}   

program ::= classdef * exp | program  
```  

