package codegenerator;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

import codegenerator.lexer.ConstructorToken;
import codegenerator.lexer.IntegerTokenCodeGen;
import codegenerator.lexer.PrintlnToken;
import codegenerator.parser.AssignStmt;
import codegenerator.parser.IdentifierToken;
import codegenerator.parser.MethodCallExpCodeGeneratorModified;
import codegenerator.parser.PaserCodeGen;
import lexer.ClassToken;
import lexer.CommaToken;
import lexer.EqualsToken;
import lexer.ExtendsToken;
import lexer.LeftCurlyToken;
import lexer.LeftParenToken;
import lexer.PlusToken;
import lexer.ReturnToken;
import lexer.RightCurlyToken;
import lexer.RightParenToken;
import lexer.SemiColonToken;
import lexer.SuperToken;
import lexer.Token;
import lexer.TrueToken;
import parser.IntExp;
import parser.ParseException;
import parser.PlusOP;
import typechecker.TypeErrorException;
import typechecker.parser.BlockStmt;
import typechecker.parser.BoolLiteralExp;
import typechecker.parser.BoolType;
import typechecker.parser.ClassDef;
import typechecker.parser.ClassName;
import typechecker.parser.ClassNameType;
import typechecker.parser.DivideOp;
import typechecker.parser.EqualsEqualsOp;
import typechecker.parser.EqualsOp;
import typechecker.parser.ExpStmt;
import typechecker.parser.Expression;
import typechecker.parser.GreaterThanEqualOp;
import typechecker.parser.GreaterThanOp;
import typechecker.parser.IfStatement;
import typechecker.parser.IntLiteralExp;
import typechecker.parser.IntType;
import typechecker.parser.LessThanEqualOp;
import typechecker.parser.LessThanOp;
import typechecker.parser.MethodCallExp;
import typechecker.parser.MethodDef;
import typechecker.parser.MethodName;
import typechecker.parser.MinusOP;
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
import typechecker.parser.TimesOp;
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
        ClassName className1 = new ClassName("Foo");
        ClassName classname2 = new ClassName("Bar");
        List<VarDec> instanceVariables = new ArrayList<VarDec>();
        List<VarDec> constructorArguments = new ArrayList<VarDec>();
        List<Expression> superParams1 = new ArrayList<Expression>();
        List<Expression> superParams2 = new ArrayList<Expression>();
        Expression superExp = new VarExp(new Var("x"));
        superParams2.add(superExp);
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

        List<Statement> nestList = new ArrayList<>();
        Statement nestStmt = new ReturnVoidStmt();
        nestList.add(nestStmt);
        Statement stmt7 = new BlockStmt(nestList);
        MethodName methodName7 = new MethodName("test7"); // Tests BlockStmt

        Statement stmt8 = new VariableInitializationStmt(new VarDec(new BoolType(), new Var("boolean")), new BoolLiteralExp(true));
        MethodName methodName8 = new MethodName("test8"); // Tests VariableInitializationStmt-Bool

        Statement stmt9 = new VariableInitializationStmt(new VarDec(new IntType(), new Var("integer")), new IntLiteralExp(1));
        MethodName methodName9 = new MethodName("test9"); // Tests VariableInitializationStmt-Int

        List<VarDec> decList = new ArrayList<VarDec>();
        decList.add(new VarDec(new StringType(), new Var("y")));
        decList.add(new VarDec(new StringType(), new Var("z")));
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
        ClassDef classToTest1 = new ClassDef(className1,
                instanceVariables,
                constructorArguments,
                superParams1,
                constructorBody,
                methods);
        ClassDef classToTest2 = new ClassDef(classname2,
                instanceVariables,
                constructorArguments,
                superParams2,
                constructorBody,
                methods);
        List<ClassDef> classes = new ArrayList<ClassDef>();
        classes.add(classToTest1);
        classes.add(classToTest2);

        CodeGenerator ProgramTester = new CodeGenerator(new Program(classes, new ExpStmt(new IntLiteralExp(0))),
                fileToPrint);
        ProgramTester.generateCode();
    }

    @Test
    public void testExpressions() throws TypeErrorException, IOException, CodeGeneratorException {

        PrintWriter fileToPrint = new PrintWriter(("generatedCode3"));
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
        Statement stmt4 = new PrintlnStmt(new MethodCallExpCodeGeneratorModified(new ThisExp(), 
                                new MethodName("test1"), new ClassNameType(new ClassName("Foo")), new ArrayList<Expression>()));
        MethodName methodName4 = new MethodName("test4"); // Tests MethodCallExpCodeGeneratorModified
        Statement stmt5 = new PrintlnStmt(new NewExp(new ClassName("Foo"), new ArrayList<Expression>()));
        MethodName methodName5 = new MethodName("test5"); // Tests NewExp
        List<VarDec> decList = new ArrayList<VarDec>();
        decList.add(new VarDec(new StringType(), new Var("w")));
        methods.add(new MethodDef(new VoidType(), methodName1, decList, stmt1));
        methods.add(new MethodDef(new VoidType(), methodName2, decList, stmt2));
        methods.add(new MethodDef(new VoidType(), methodName3, decList, stmt3));
        methods.add(new MethodDef(new VoidType(), methodName4, decList, stmt4)); // NullPointerException when testing WriteMethodCall().
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

    @Test
    public void testOps() throws TypeErrorException, IOException, CodeGeneratorException {

        PrintWriter fileToPrint = new PrintWriter(("generatedCode4"));
        ClassName className = new ClassName("Foo");
        List<VarDec> instanceVariables = new ArrayList<VarDec>();
        List<VarDec> constructorArguments = new ArrayList<VarDec>();
        List<Expression> superParams = new ArrayList<Expression>();
        List<Statement> constructorBody = new ArrayList<Statement>();
        List<MethodDef> methods = new ArrayList<MethodDef>();
        Statement stmt1 = new PrintlnStmt(new OpExp(new IntLiteralExp(2), new MinusOP(), new IntLiteralExp(1)));
        MethodName methodName1 = new MethodName("test1"); // Tests MinusOP
        Statement stmt2 = new PrintlnStmt(new OpExp(new IntLiteralExp(1), new TimesOp(), new IntLiteralExp(2)));
        MethodName methodName2 = new MethodName("test2"); // Tests TimesOP
        Statement stmt3 = new PrintlnStmt(new OpExp(new IntLiteralExp(2), new DivideOp(), new IntLiteralExp(1)));
        MethodName methodName3 = new MethodName("test3"); // Tests DividOp
        Statement stmt4 = new PrintlnStmt(new OpExp(new IntLiteralExp(2), new GreaterThanOp(), new IntLiteralExp(1)));
        MethodName methodName4 = new MethodName("test4"); // Tests GreaterThanOp
        Statement stmt5 = new PrintlnStmt(new OpExp(new IntLiteralExp(1), new LessThanOp(), new IntLiteralExp(2)));
        MethodName methodName5 = new MethodName("test5"); // Tests LessThanOp
        Statement stmt6 = new PrintlnStmt(new OpExp(new IntLiteralExp(2), new GreaterThanEqualOp(), new IntLiteralExp(2)));
        MethodName methodName6 = new MethodName("test6"); // Tests GreaterThanEqualOp
        Statement stmt7 = new PrintlnStmt(new OpExp(new IntLiteralExp(3), new LessThanEqualOp(), new IntLiteralExp(4)));
        MethodName methodName7 = new MethodName("test7"); // Tests LessThanEqualOp
        Statement stmt8 = new PrintlnStmt(new OpExp(new IntLiteralExp(4), new EqualsEqualsOp(), new IntLiteralExp(4)));
        MethodName methodName8 = new MethodName("test8"); // Tests EqualEqualOp
        List<VarDec> decList = new ArrayList<VarDec>();
        decList.add(new VarDec(new StringType(), new Var("w")));
        methods.add(new MethodDef(new VoidType(), methodName1, decList, stmt1));
        methods.add(new MethodDef(new VoidType(), methodName2, decList, stmt2));
        methods.add(new MethodDef(new VoidType(), methodName3, decList, stmt3));
        methods.add(new MethodDef(new VoidType(), methodName4, decList, stmt4));
        methods.add(new MethodDef(new VoidType(), methodName5, decList, stmt5));
        methods.add(new MethodDef(new VoidType(), methodName6, decList, stmt6));
        methods.add(new MethodDef(new VoidType(), methodName7, decList, stmt7));
        methods.add(new MethodDef(new VoidType(), methodName8, decList, stmt8));                                                                
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

    // Parser Tests ----------------------------------------------------
    // Program should resemble the following:
    /* class Foo extends Bar {
        int a;
        def constructor (self, String s, Bool b):
                var c, var d = 1 + 2, 3 + 4; // -, *, / operators not implemented yet
                print(true);
        }

        void Test()
                return; 
    }
    */

    /* @Test
    public void testParser() throws ParseException{
        final Token[] program = {new ClassToken(), new IdentifierToken("Foo"), new ExtendsToken(),
                new IdentifierToken("Bar"), new LeftCurlyToken(), new IntType(), new IdentifierToken("a"),
                new SemiColonToken(), new ConstructorToken(), new LeftParenToken(), new IdentifierToken("Foo"), 
                new CommaToken(), new StringType(), new IdentifierToken("s"), new CommaToken(), new BoolType(), 
                new IdentifierToken("b"), new RightParenToken(), new LeftCurlyToken(), new SuperToken(), new LeftParenToken(), 
                new RightParenToken(), new SemiColonToken(), new IdentifierToken("c"), new CommaToken(), new IdentifierToken("d"), new EqualsToken(), new IntegerTokenCodeGen(1), 
                new PlusToken(), new IntegerTokenCodeGen(2), new CommaToken(), new IntegerTokenCodeGen(3), new PlusToken(), 
                new IntegerTokenCodeGen(4), new PrintlnToken(), new LeftParenToken(), new TrueToken(), new RightParenToken(), new SemiColonToken(), 
                new RightCurlyToken(), new VoidType(), new IdentifierToken("Test"), new LeftParenToken(), new RightParenToken(), new ReturnToken(), 
                new SemiColonToken(), new RightCurlyToken(), new ReturnToken(), new SemiColonToken()};
        final PaserCodeGen parser = new PaserCodeGen(program);
        parser.parseProgram();
    } */
}
