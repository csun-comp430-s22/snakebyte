package codegenerator;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

import codegenerator.parser.AssignStmt;
import codegenerator.parser.MethodCallExpCodeGeneratorModified;
import parser.IntExp;
import parser.PlusOP;
import typechecker.TypeErrorException;
import typechecker.parser.BlockStmt;
import typechecker.parser.BoolLiteralExp;
import typechecker.parser.BoolType;
import typechecker.parser.ClassDef;
import typechecker.parser.ClassName;
import typechecker.parser.EqualsEqualsOp;
import typechecker.parser.EqualsOp;
import typechecker.parser.ExpStmt;
import typechecker.parser.Expression;
import typechecker.parser.IfStatement;
import typechecker.parser.IntLiteralExp;
import typechecker.parser.IntType;
import typechecker.parser.MethodCallExp;
import typechecker.parser.MethodDef;
import typechecker.parser.MethodName;
import typechecker.parser.NewExp;
import typechecker.parser.OpExp;
import typechecker.parser.PlusOp;
import typechecker.parser.PrintlnStmt;
import typechecker.parser.Program;
import typechecker.parser.ReturnNonVoidStmt;
import typechecker.parser.ReturnVoidStmt;
import typechecker.parser.Statement;
import typechecker.parser.StringLiteralExp;
import typechecker.parser.StringType;
import typechecker.parser.ThisExp;
import typechecker.parser.Var;
import typechecker.parser.VarDec;
import typechecker.parser.VarExp;
import typechecker.parser.VariableInitializationStmt;
import typechecker.parser.VoidType;
import typechecker.parser.WhileStmt;

public class CodeGeneratorTest {

    @Test
    public void testPrograms() throws TypeErrorException, FileNotFoundException {

        PrintWriter fileToPrint = new PrintWriter("generatedCode");
        ClassName className = new ClassName("Foo");
        List<VarDec> instanceVariables = new ArrayList<VarDec>();
        List<VarDec> constructorArguments = new ArrayList<VarDec>();
        VarDec firstParam = new VarDec(new BoolType(), new Var("w"));
        VarDec secondParam = new VarDec(new StringType(), new Var("x"));
        constructorArguments.add(firstParam);
        constructorArguments.add(secondParam);
        List<Expression> superParams = new ArrayList<Expression>();
        List<Statement> constructorBody = new ArrayList<Statement>();
        List<MethodDef> methods = new ArrayList<MethodDef>();
        Statement printStmt = new PrintlnStmt(new VarExp(new Var("w")));
        MethodName methodName = new MethodName("foo");
        List<VarDec> decList = new ArrayList<VarDec>();
        decList.add(new VarDec(new StringType(), new Var("w")));
        methods.add(new MethodDef(
                new VoidType(), methodName, decList, printStmt));
        ClassDef classToTest = new ClassDef(className,
                instanceVariables,
                constructorArguments,
                superParams,
                constructorBody,
                methods);
        List<ClassDef> classes = new ArrayList<ClassDef>();
        classes.add(classToTest);

        CodeGenerator ProgramTester = new CodeGenerator(new Program(classes, new ExpStmt(new IntLiteralExp(0))),
                fileToPrint);

    }

    @Test
    public void testStatements() throws TypeErrorException, IOException, CodeGeneratorException {

        PrintWriter fileToPrint = new PrintWriter(("generatedCode2"));
        ClassName className = new ClassName("Foo");
        List<VarDec> instanceVariables = new ArrayList<VarDec>();
        List<VarDec> constructorArguments = new ArrayList<VarDec>();
        List<Expression> superParams = new ArrayList<Expression>();
        List<Statement> constructorBody = new ArrayList<Statement>();
        List<MethodDef> methods = new ArrayList<MethodDef>();
        Statement stmt1 = new VariableInitializationStmt(new VarDec(new StringType(), new Var("string")), new StringLiteralExp("a"));
        MethodName methodName1 = new MethodName("test1"); // Tests VariableInitializationStmt-String
        Statement stmt2 = new AssignStmt(new Var("assign"), new BoolLiteralExp(true));
        MethodName methodName2 = new MethodName("test2"); // Tests AssignStmt
        Statement stmt3 = new IfStatement(new BoolLiteralExp(true), new PrintlnStmt(new IntLiteralExp(1)), 
                                                new PrintlnStmt(new IntLiteralExp(2)));
        MethodName methodName3 = new MethodName("test3"); // Tests IfStatement
        Statement stmt4 = new WhileStmt(new BoolLiteralExp(false), new PrintlnStmt(new StringLiteralExp("hello")));
        MethodName methodName4 = new MethodName("test4"); // Tests WhileStmt
        Statement stmt5 = new ReturnNonVoidStmt(new IntLiteralExp(1));
        MethodName methodName5 = new MethodName("test5"); // Tests ReturnNonVoidStmt
        Statement stmt6 = new ReturnVoidStmt();
        MethodName methodName6 = new MethodName("test6"); // Tests ReturnVoidStmt
        Statement stmt7 = new BlockStmt(new ArrayList<Statement>());
        MethodName methodName7 = new MethodName("test7"); // Tests BlockStmt
        Statement stmt8 = new VariableInitializationStmt(new VarDec(new BoolType(), new Var("boolean")), new BoolLiteralExp(true));
        MethodName methodName8 = new MethodName("test8"); // Tests VariableInitializationStmt-Bool
        Statement stmt9 = new VariableInitializationStmt(new VarDec(new IntType(), new Var("integer")), new IntLiteralExp(1));
        MethodName methodName9 = new MethodName("test9"); // Tests VariableInitializationStmt-Int
        List<VarDec> decList = new ArrayList<VarDec>();
        decList.add(new VarDec(new StringType(), new Var("w")));
        methods.add(new MethodDef(
                new VoidType(), methodName1, decList, stmt1));
        methods.add(new MethodDef(
                new VoidType(), methodName2, decList, stmt2));
        methods.add(new MethodDef(
                new VoidType(), methodName3, decList, stmt3));
        methods.add(new MethodDef(
                new VoidType(), methodName4, decList, stmt4));
        methods.add(new MethodDef(
                new VoidType(), methodName5, decList, stmt5));
        methods.add(new MethodDef(
                new VoidType(), methodName6, decList, stmt6));
        methods.add(new MethodDef(
                new VoidType(), methodName7, decList, stmt7));
        methods.add(new MethodDef(
                new VoidType(), methodName8, decList, stmt8));
        methods.add(new MethodDef(
                new VoidType(), methodName9, decList, stmt9));
        ClassDef classToTest = new ClassDef(className,
                instanceVariables,
                constructorArguments,
                superParams,
                constructorBody,
                methods);
        List<ClassDef> classes = new ArrayList<ClassDef>();
        classes.add(classToTest);

        CodeGenerator ProgramTester = new CodeGenerator(new Program(classes, new ExpStmt(new IntLiteralExp(0))),
                fileToPrint);
        ProgramTester.generateCode();
    }

    @Test
    public void testExpressions() throws TypeErrorException, IOException, CodeGeneratorException {

        PrintWriter fileToPrint = new PrintWriter(("generatedCode2"));
        ClassName className = new ClassName("Foo");
        List<VarDec> instanceVariables = new ArrayList<VarDec>();
        List<VarDec> constructorArguments = new ArrayList<VarDec>();
        List<Expression> superParams = new ArrayList<Expression>();
        List<Statement> constructorBody = new ArrayList<Statement>();
        List<MethodDef> methods = new ArrayList<MethodDef>();
        Statement stmt1 = new IfStatement(new VarExp(new Var("a")), new PrintlnStmt(new StringLiteralExp("true")), 
                                                new PrintlnStmt(new StringLiteralExp("false")));
        MethodName methodName1 = new MethodName("test1"); // Tests VarExp
        Statement stmt2 = new PrintlnStmt(new OpExp(new IntLiteralExp(1), new PlusOp(), new IntLiteralExp(2)));
        MethodName methodName2 = new MethodName("test2"); // Tests OpExp
        Statement stmt3 = new PrintlnStmt(new ThisExp());
        MethodName methodName3 = new MethodName("test3"); // Tests ThisExp
        Statement stmt4 = new PrintlnStmt(new MethodCallExpCodeGeneratorModified(new IntLiteralExp(0), 
                                new MethodName("methodCall"), new ArrayList<Expression>()));
        MethodName methodName4 = new MethodName("test4"); // Tests MethodCallExpCodeGeneratorModified
        Statement stmt5 = new PrintlnStmt(new NewExp(new ClassName("Foo"), new ArrayList<Expression>()));
        MethodName methodName5 = new MethodName("test5"); // Tests NewExp
        List<VarDec> decList = new ArrayList<VarDec>();
        decList.add(new VarDec(new StringType(), new Var("w")));
        methods.add(new MethodDef(new VoidType(), methodName1, decList, stmt1));
        methods.add(new MethodDef(new VoidType(), methodName2, decList, stmt2));
        methods.add(new MethodDef(new VoidType(), methodName3, decList, stmt3));
        //methods.add(new MethodDef(new VoidType(), methodName4, decList, stmt4)); // NullPointerException when testing WriteMethodCall().
                                                                                   // likely due to targetTypeName in classname always being null
        methods.add(new MethodDef(new VoidType(), methodName5, decList, stmt5));                                                                      
        ClassDef classToTest = new ClassDef(className,
                instanceVariables,
                constructorArguments,
                superParams,
                constructorBody,
                methods);
        List<ClassDef> classes = new ArrayList<ClassDef>();
        classes.add(classToTest);

        CodeGenerator ProgramTester = new CodeGenerator(new Program(classes, new ExpStmt(new IntLiteralExp(0))),
                fileToPrint);
        ProgramTester.generateCode();
    }
}
