# SNAKEBYTE
## BY Karim Atalla, Jack Konyan, Javier Parra, Ruitao Wu
---
## Why this language, and why this language design?
We are targeting Python as our destination language because it is an object-oriented and functional programming language. Our group implemented the compiler in JAVA  because it is a language that we are all familiar with. Java and python have many similar features that could save us time during the development process. The goal of this project is to help us understand the mechanisms of source-to-source compilers.
## Code snippets with relevant explanations
### Subtyping
The first feature that we have is subtyping, with an example of it shown below:
```
Type type = new Int();
```
### Casting  
The second feature that we have is casting, which could convert a variable to different type. An example is shown below:
```
Int test1 = 1;
Str test2 = (Str)test1;
```
### Define a class
```python
# Define a class with default constructor
class test{
	Int a;
	Str b;
	test(Int a, Str b){
		this.a = a;
		this.b = b;
	}
	# Define a method with zero argument
	Int getA(){
		return this.a;
	}
	# Define a method with one argument
	Str getB(Int a){
		return this.b;
	}
	# Define a method with more than one argument
	Str getC(Str var1, Str var2){
		return var1 + var2;
	}
	#define a parameter and initialize it
	Int index = 0;
	#while loop
	while(index < 10){
		#print the value of index with condition
		if(index / 2 == 0){
			print(index);
		}
		#increment the value of index
		index = index + 1;
	}
}
# class extends another class
class test2 extends test{
	Int c;
	test2(Int a, Str b, Int c){
		super(a, b);
		this.c = c;
	}
	#Defien a type array
	Int[] array = new Int[10];
	Int index = 0;
	#add value to the array
	while(index < 10){
		array[index] = index;
		index = index + 1;
	}
}
```
## LIMITATIONS
- Many built-in methods for list/array, such as the `append()` method, were not included in our grammar and thus are not supported by our compiler
- Third party libraries are also not supported
- Our compiler only supports object-oriented programming parsers, so there is no support for functional programming parsers
- Our compiler will only accept statically typed code, even though python is dynamically typed. For example,
```python
a = "Hello" # is invalid
Str a = "Hello" # is valid
a = 1 # will cause an exception
```
- We did not include the generic feature in our compiler, for example:
```python
def hello(name: str) -> str: #this is not supported in our compiler
    return 'Hello ' + name
```
## Knowing what you know now, what would you do differently?
In the current design, we focused primarily on the object-oriented programming features. If we were to do things differently, we would had aimed to implement functional programming features.
We wouldn't select a different implementation language for the compiler due to the fact that everyone in our group is all fimilar with Java. However, we might have chosen a different target language for the compiler because Python requires specific indentation to read statements, which would require more work on the code generation. With this in mind, we would have selected some target language that is less sensitive to indentation, such as C/C++, JavaScript, TypeScript, etc.  
We would not change our integrated development environment, Visual Studio Code, as it has proved to be convenient to use and compiles our code with no issues. Plus it integrates the GitHub version control system well enough.
The team size is a critical factor in the design of our compiler. It is directly related to the number of features that we have implemented and the workload that we have to do. We wouldn't make any changes to the team size, because we would like to keep the team size as small as possible. That way, communicating with the team as well as discussing the design of our compiler is easier to accomplish.
## How do I compile your compiler?
Your system would need to have a Java 1.8 runtime environment installed to compile, as well as Apache Maven and Junit. We have been using Visual Studio Code to build and compile everything thus far.
## How do I run your compiler?
We have included a code generator, but it is buggy and no result return, to execute the following command: (Since we are using Apache Maven, the command is not convenient to use)
It compiles, but returns no result.
```
mvn exec:java -Dexec.mainClass="Compiler" -Dexec.args="arg1 arg2"
```
the following command for runtime but also no result return
```
```
mvn exec:java -Dexec.mainClass="Compiler" -Dexec.classpathScope=runtime
```
## Formal syntax definition<br>
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

