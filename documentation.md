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
<code>
var<span style="color:red;"> variable name</span><br>
classname <span style="color:red;"> custom object </span><br>
functionname <span style="color:red;">is a name of a function</span><br>
Str <span style="color:red;">is string type</span><br>
Boolean ::= <span style="color:red;"> true | false</span><br>
i <span style="color:red"> is an integer</span><br>
s <span style="color:red"> is a string</span><br>
b <span style="color:red"> is Boolean</span><br>
Int <span style="color:red"> Integers</span><br>
type ::= <span style="color:red"> IntegersInt | Boolean | Str | type[ ] </span><br>  
exp  <span style="color:red">is an expression</span><br>  
vardec ::= type var  <span style="color:red">variable declaration</span><br>
Op::= +| - | * | / | > | < | = | == | >= | <=<span style="color:red">arithmetic, assignment & comparison operators </span><br>
exp ::= exp Op exp | exp.functionname(exp*) <span style="color:red">method call</span><br>
exp ::= i | s| b | var  
        |this | <span style="color:red">Refers to my instance</span>
|super <span style="color:red">refers to superclass (parent) objects</span>
|var=exp  
| new classname(exp*)  <span style="color:red">new instance of a class</span> 
| (type)exp <span style="color:red">Casts an expression as a type</span> 
| new type [exp] | exp[exp] | exp[exp]   = exp  Variables, strings, arrays and integers<br>
statement ::= type var ‘=’ exp ‘;’| <span style="color:red">variable assignment</span><br>
while (exp) statement | <span style="color:red">while loops</span><br>
{ statement * } | <span style="color:red">block</span><br>
break; | <span style="color:red">break</span><br>
if (exp) statement else statement | <span style="color:red">if/else conditional</span><br>
return exp; | <span style="color:red">return an expression</span><br>
print(exp) | <span style="color:red">Prints something to the terminal</span><br>
methoddef ::= type methodname (vardec*) statement | <span style="color:red">zero or more parameter</span>  
extension ::= epsilon | <span style="color:red">`extends` classname</span>  
classdef ::= `class` classname extension {   
vardec*  
methoddef * | <span style="color:red">method zero or more</span>  
classname(vardec*){ statement*  }   <span style="color:red">refers to more than one,           
use comma to separate different variable</span>   
}

program ::= classdef * exp | program
</code>