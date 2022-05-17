# snakebyte  
Compiler written in Java targeting Python  
---
## Encountered issuses  
## [Documentation](https://github.com/csun-comp430-s22/snakebyte/blob/main/documentation.md)
## Run Compiler
```
It compiles but no result return
```
mvn exec:java -Dexec.mainClass="Compiler" -Dexec.args="arg1 arg2"
```
the following command for runtime but also no result return
```
```
mvn exec:java -Dexec.mainClass="Compiler" -Dexec.classpathScope=runtime
```
```
---  
## Syntax  
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

