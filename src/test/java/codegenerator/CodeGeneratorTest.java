package codegenerator;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

import typechecker.TypeErrorException;
import typechecker.parser.BoolType;
import typechecker.parser.ClassDef;
import typechecker.parser.ClassName;
import typechecker.parser.ExpStmt;
import typechecker.parser.Expression;
import typechecker.parser.IntLiteralExp;
import typechecker.parser.MethodDef;
import typechecker.parser.MethodName;
import typechecker.parser.PrintlnStmt;
import typechecker.parser.Program;
import typechecker.parser.Statement;
import typechecker.parser.StringType;
import typechecker.parser.Var;
import typechecker.parser.VarDec;
import typechecker.parser.VarExp;
import typechecker.parser.VoidType;

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

}
