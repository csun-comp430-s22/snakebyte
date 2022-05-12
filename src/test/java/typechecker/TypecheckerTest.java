package typechecker;

import typechecker.parser.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import lexer.IntegerValue;
import parser.IntExp;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.beans.Expression;
import java.util.ArrayList;
import java.util.Arrays;
public class TypecheckerTest {
    // the following code is not working for some reason

    public static Typechecker emptyTypechecker() throws TypeErrorException {
        return new Typechecker(new Program(new ArrayList<ClassDef>(),
                                           new ExpStmt(new IntLiteralExp(0))));
    }
    //create new class
    //class Foo(){
    //    void foo(){
    //        println("x");
    //    }
    //}
   public static Typechecker nonEmptyTypechecker() throws TypeErrorException {
        List<ClassDef> classes = new ArrayList<ClassDef>();
        ClassName className = new ClassName("Foo");
        List<VarDec> instanceVariables = new ArrayList<VarDec>();
        List<VarDec> constructorArguments = new ArrayList<VarDec>();
        List<Expression> superParams = new ArrayList<Expression>();
        List<Statement> constructorBody = new ArrayList<Statement>();
        List<MethodDef> methods = new ArrayList<MethodDef>();
        Statement printStmt = new PrintlnStmt(new VarExp(new Var("x")));
        MethodName methodName = new MethodName("foo");
        methods.add(new MethodDef(
            new VoidType(),methodName,new ArrayList<VarDec>(),printStmt
        ));
        classes.add(new ClassDef(className,
                                    instanceVariables,
                                    constructorArguments,
                                    superParams,
                                    constructorBody,
                                    methods));
        return new Typechecker(new Program(classes,
                                             new ExpStmt(new IntLiteralExp(0)))); 
    }
    @Test
    public void testVariableInScope() throws TypeErrorException {
        
        final Type expectedType = new IntType();
        final Map<Var, Type> typeEnvironment = new HashMap<Var, Type>();
        typeEnvironment.put(new Var("x"), new IntType());
        
        final Type receivedType =
            emptyTypechecker().typeofVariable(new VarExp(new Var("x")),
                                            typeEnvironment);
        assertEquals(expectedType, receivedType);
    }
    
    @Test(expected = TypeErrorException.class)
    public void testVariableOutOfScope() throws TypeErrorException {
        
        final Map<Var, Type> typeEnvironment = new HashMap<Var, Type>();
        emptyTypechecker().typeofVariable(new VarExp(new Var("x")),
                                        typeEnvironment);
    }

    @Test
    public void testThisInClass() throws TypeErrorException {
        
        assertEquals(new ClassNameType(new ClassName("foo")),
                     emptyTypechecker().typeofThis(new ClassName("foo")));
    }

    @Test(expected = TypeErrorException.class)
    public void testThisNotInClass() throws TypeErrorException {
        
        emptyTypechecker().typeofThis(null);
    }
    @Test
    public void testIntType() throws TypeErrorException {
        
        final Map<Var, Type> typeEnvironment = new HashMap<Var, Type>();
       Type expected =  emptyTypechecker().typeofOp(new OpExp(new IntLiteralExp(1),
                                            new PlusOp(),
                                            new IntLiteralExp(0)),
                                  typeEnvironment,new ClassName("foo"));
        assertEquals(expected, new IntType());
    }
    @Test
    public void testBoolTypeLessThan() throws TypeErrorException{
        
        final Map<Var, Type> typeEnvironment = new HashMap<Var, Type>();
        Type expected =  emptyTypechecker().typeofOp(new OpExp(new IntLiteralExp(1),
                                            new LessThanOp(),
                                            new IntLiteralExp(0)),
                                  typeEnvironment,new ClassName("foo"));
        assertEquals(expected, new BoolType());
    }
    @Test
    public void testBoolTypeEqualsOne() throws TypeErrorException{
        
        final Map<Var, Type> typeEnvironment = new HashMap<Var, Type>();
        Type expected =  emptyTypechecker().typeofOp(new OpExp(new IntLiteralExp(1),
                                            new EqualsEqualsOp(),
                                            new IntLiteralExp(0)),
                                  typeEnvironment,new ClassName("foo"));
        assertEquals(expected, new BoolType());
    }
    @Test(expected = TypeErrorException.class)
    public void testEqualsEqualsOPOne() throws TypeErrorException{
        
        final Map<Var, Type> typeEnvironment = new HashMap<Var, Type>();
        Type expected =  emptyTypechecker().typeofOp(new OpExp(new BoolLiteralExp(true),
                                            new EqualsEqualsOp(),
                                            new IntLiteralExp(0)),
                                  typeEnvironment,new ClassName("foo"));
        assertEquals(expected, new BoolType());
    }
    @Test(expected = TypeErrorException.class)
    public void testEqualsEqualsOPTwo() throws TypeErrorException{
        
        final Map<Var, Type> typeEnvironment = new HashMap<Var, Type>();
        Type expected =  emptyTypechecker().typeofOp(new OpExp(new IntLiteralExp(0),
                                            new EqualsEqualsOp(),
                                            new BoolLiteralExp(true)),
                                  typeEnvironment,new ClassName("foo"));
        assertEquals(expected, new BoolType());
    }
    @Test(expected = TypeErrorException.class)
    public void testBoolTypeLessThanTwo() throws TypeErrorException{
        
        final Map<Var, Type> typeEnvironment = new HashMap<Var, Type>();
        Type expected =  emptyTypechecker().typeofOp(new OpExp(new IntLiteralExp(1),
                                            new LessThanOp(),
                                            new BoolLiteralExp(true)),
                                  typeEnvironment,new ClassName("foo"));
    }
    @Test(expected = TypeErrorException.class)
    public void testBoolTypeLessThanThree() throws TypeErrorException{
        
        final Map<Var, Type> typeEnvironment = new HashMap<Var, Type>();
        Type expected =  emptyTypechecker().typeofOp(new OpExp(new BoolLiteralExp(true),
                                            new LessThanOp(),
                                            new IntLiteralExp(1)),
                                  typeEnvironment,new ClassName("foo"));
    }
    @Test(expected = TypeErrorException.class)
    public void testIntTypePlusTwo() throws TypeErrorException{
        
        final Map<Var, Type> typeEnvironment = new HashMap<Var, Type>();
        Type expected =  emptyTypechecker().typeofOp(new OpExp(new IntLiteralExp(1),
                                            new PlusOp(),
                                            new BoolLiteralExp(true)),
                                  typeEnvironment,new ClassName("foo"));
    }
    @Test(expected = TypeErrorException.class)
    public void testIntTypePlusThree() throws TypeErrorException{
        
        final Map<Var, Type> typeEnvironment = new HashMap<Var, Type>();
        Type expected =  emptyTypechecker().typeofOp(new OpExp(new BoolLiteralExp(true),
                                            new PlusOp(),
                                            new IntLiteralExp(1)),
                                  typeEnvironment,new ClassName("foo"));
    }
    @Test(expected = TypeErrorException.class)
    public void testBoolTypeEqualsTwo() throws TypeErrorException{
        
        final Map<Var, Type> typeEnvironment = new HashMap<Var, Type>();
        Type expected =  emptyTypechecker().typeofOp(new OpExp(new IntLiteralExp(1),
                                            new EqualsOp(),
                                            new BoolLiteralExp(true)),
                                  typeEnvironment,new ClassName("foo"));
    }
    @Test(expected = TypeErrorException.class)
    public void testBoolTypeEqualsThree() throws TypeErrorException{
        
        final Map<Var, Type> typeEnvironment = new HashMap<Var, Type>();
        Type expected =  emptyTypechecker().typeofOp(new OpExp(new BoolLiteralExp(true),
                                            new EqualsOp(),
                                            new IntLiteralExp(1)),
                                  typeEnvironment,new ClassName("foo"));
    }
    @Test(expected = TypeErrorException.class)
    public void testUnspportedOp() throws TypeErrorException{
        
        final Map<Var, Type> typeEnvironment = new HashMap<Var, Type>();
        Type expected =  emptyTypechecker().typeofOp(new OpExp(new IntLiteralExp(1),
                                            null,
                                            new IntLiteralExp(0)),
                                  typeEnvironment,new ClassName("foo"));
    }
    @Test(expected = TypeErrorException.class)
    public void testTypeof() throws TypeErrorException{
        
        final Map<Var, Type> typeEnvironment = new HashMap<Var, Type>();
        Type expected =  emptyTypechecker().typeof(null,typeEnvironment, new ClassName("foo"));
    }
    @Test
    public void testTypeofvarexp() throws TypeErrorException{
        
        final Map<Var, Type> typeEnvironment = new HashMap<Var, Type>();
        typeEnvironment.put(new Var("x"), new StringType());
        Type expected =  emptyTypechecker().typeof(new StringLiteralExp("x"),typeEnvironment, new ClassName("foo"));
        assertEquals(expected,new StringType());
    }
    @Test
    public void testTypeofStringType() throws TypeErrorException{
        
        final Map<Var, Type> typeEnvironment = new HashMap<Var, Type>();
        typeEnvironment.put(new Var("x"), new IntType());
        Type expected =  emptyTypechecker().typeof(new VarExp(new Var("x")),typeEnvironment, new ClassName("foo"));
        assertEquals(expected,new IntType());
    }
    @Test
    public void testTypeofthis() throws TypeErrorException{
        

        final Map<Var, Type> typeEnvironment = new HashMap<Var, Type>();
        typeEnvironment.put(new Var("x"), new ClassNameType(new ClassName("foo")));
        Type expected =  emptyTypechecker().typeof(new ThisExp(),typeEnvironment, new ClassName("foo"));
        assertEquals(expected,new ClassNameType(new ClassName("foo")));
    }
    @Test
    public void testTypeofopexp() throws TypeErrorException{
        
        final Map<Var, Type> typeEnvironment = new HashMap<Var, Type>();
        typeEnvironment.put(new Var("x"), new IntType());
        Type expected =  emptyTypechecker().typeof(new OpExp(new VarExp(new Var("x")),
                                                            new PlusOp(),
                                                            new IntLiteralExp(1)),
                                                            typeEnvironment, new ClassName("foo"));
        assertEquals(expected,new IntType());
    }
   /* @Test
    public void testTypeofNewExpression() throws TypeErrorException{
        ArrayList<ClassDef> tester = new ArrayList<ClassDef>();
        tester.add(new ClassDef(new ClassName("foo2"),new ArrayList<VarDec>(), new ArrayList<VarDec>(), new ArrayList<Expression>(), new ArrayList<Statement>(), new ArrayList<MethodDef>()));
        final Typechecker emptyTypechecker = new Typechecker(new Program(tester,
                                    new ExpStmt(new IntLiteralExp(0))));
        final Map<Var, Type> typeEnvironment = new HashMap<Var, Type>();
        typeEnvironment.put(new Var("foo2"), new ClassNameType(new ClassName("foo2")));
        Type expected =  emptyTypechecker.typeof(new NewExp(new ClassName("foo2"), Arrays.asList(new IntLiteralExp(0))),typeEnvironment, new ClassName("foo2"));
       //assertEquals(new ClassNameType(new ClassName("foo2")), expected);
    }*/
    @Test
    public void testTypeofmethodcall() throws TypeErrorException{
        final Type expectedType = new VoidType();
        List<MethodDef> methods = new ArrayList<MethodDef>();
        ClassName className = new ClassName("Foo");
        MethodName methodName = new MethodName("foo");
        MethodDef methodDef = new MethodDef(new StringType(),methodName,null,null);
        methods.add(methodDef);
        List<VarDec>instanceVars= Arrays.asList(new VarDec(new StringType(), new Var("string text")), 
                                                new VarDec(new StringType(), new Var("string text")));
        ClassDef classDef = new ClassDef(className,instanceVars,null,null,null,methods);
        final Map<Var, Type> typeEnvironment = new HashMap<Var, Type>();
        typeEnvironment.put(new Var("x"), new ClassNameType(className));
        Expression targetExpression = new VarExp(new Var("x"));
        
        List<Expression> arguments = new ArrayList<Expression>();
        final Type actualType = nonEmptyTypechecker().typeof(new MethodCallExp(targetExpression,methodName,arguments),
                                                        typeEnvironment,
                                                         new ClassName(""));
        assertEquals(actualType,expectedType);
    }
  /*  @Test
    public void testiWellTypedClassDef() throws TypeErrorException{
        List<MethodDef> methods = new ArrayList<MethodDef>();
        ClassName className = new ClassName("foo");
        MethodName methodName = new MethodName("foo");
        MethodDef methodDef = new MethodDef(new StringType(),methodName,null,null);
        methods.add(methodDef);
        List<VarDec>instanceVars= Arrays.asList(new VarDec(new StringType(), new Var("string text")));
        List<VarDec>Args= Arrays.asList(new VarDec(new StringType(), new Var("string text2")));
        List<Expression>Superpara= new ArrayList<Expression>();
        Expression expressions= new Expression(new, methodName, arguments)
        Superpara.add(expressions);
        ClassDef classDef = new ClassDef(className,instanceVars,Args,Superpara,null,methods);
        final Typechecker fulltype= new Typechecker(new Program(Arrays.asList(classDef), new ExpStmt(new IntLiteralExp(0))));
        fulltype.isWellTypedClassDef(classDef);
        //assertEquals(1,emptyTypechecker().isWellTypedClassDef(classDef));


    }*/
    @Test
    public void testGetClass() throws TypeErrorException{
        final ClassDef TesterType= nonEmptyTypechecker().getClass(new ClassName("Object"));
        assertEquals(null, TesterType );
    }
    @Test
    public void testbaseTypeEnviromentForClassNull() throws TypeErrorException{
        final ClassDef testerClassdef = new ClassDef(new ClassName("foo"),new ArrayList<VarDec>(), new ArrayList<VarDec>(), 
                                    new ArrayList<Expression>(), new ArrayList<Statement>(), new ArrayList<MethodDef>());
        final Map<Var, Type> TesterType= nonEmptyTypechecker().baseTypeEnvironmentForClass(new ClassName("Object"));
        assertEquals(new HashMap<Var, Type>(), TesterType);  
    }
    @Test
    public void testbaseTypeEnviromentForClass() throws TypeErrorException{
        final ClassDef testerClassdef = new ClassDef(new ClassName("foo"),new ArrayList<VarDec>(), new ArrayList<VarDec>(), 
                                    new ArrayList<Expression>(), new ArrayList<Statement>(), new ArrayList<MethodDef>());
        final Map<Var, Type> TesterType= nonEmptyTypechecker().baseTypeEnvironmentForClass(new ClassName("Foo"));
        assertEquals(new HashMap<Var, Type>(), TesterType);
    }
    @Test
    public void testGetParent() throws TypeErrorException{
        ArrayList<ClassDef> tester = new ArrayList<ClassDef>();
        tester.add(new ClassDef(new ClassName("foo"),new ArrayList<VarDec>(), new ArrayList<VarDec>(), 
                                new ArrayList<Expression>(), new ArrayList<Statement>(), new ArrayList<MethodDef>()));
        final Typechecker expected = new Typechecker(new Program(tester, new ExpStmt(new IntLiteralExp(0))));
        final ClassDef TesterType= expected.getParent(new ClassName("foo"));
        assertEquals(null, TesterType );
    }
    //Called method on non-class type
    @Test(expected = TypeErrorException.class)
    public void testTypeofmethodcallExceptionNonClass() throws TypeErrorException{
        final Type expectedType = new StringType();
        final Map<Var, Type> typeEnvironment = new HashMap<Var, Type>();
        typeEnvironment.put(new Var("x"), expectedType);
        Expression targetExpression = new VarExp(new Var("x"));
        MethodName methodName = new MethodName("foo");
        List<Expression> arguments = new ArrayList<Expression>();
        final Type actualType = emptyTypechecker().typeof(new MethodCallExp(targetExpression,methodName,arguments),
                                                        typeEnvironment,
                                                         new ClassName("foo"));
        assertEquals(actualType,expectedType);
    }
    // no class name
    @Test(expected = TypeErrorException.class)
    public void testTypeofmethodcallException() throws TypeErrorException{
        final Type expectedType = new StringType();
        final Map<Var, Type> typeEnvironment = new HashMap<Var, Type>();
        typeEnvironment.put(new Var("x"), new ClassNameType(new ClassName("foo")));
        Expression targetExpression = new VarExp(new Var("x"));
        MethodName methodName = new MethodName("foo");
        List<Expression> arguments = new ArrayList<Expression>();
        final Type actualType = emptyTypechecker().typeofMethodCall(new MethodCallExp(targetExpression,methodName,arguments),
                                                        typeEnvironment,
                                                         new ClassName(""));
    }

    @Test(expected = TypeErrorException.class)
    public void testgetclassException() throws TypeErrorException{
        
        final Map<Var, Type> typeEnvironment = new HashMap<Var, Type>();
        
        Type expected =  emptyTypechecker().typeof(new NewExp(new ClassName("foo2"), Arrays.asList(new IntLiteralExp(0))),typeEnvironment, new ClassName("foo2"));

    }
    //new op tests: <=,>=,==
    @Test
    public void testLessThanEqual() throws TypeErrorException{
        
        final Map<Var, Type> typeEnvironment = new HashMap<Var, Type>();
        Type expected =  emptyTypechecker().typeofOp(new OpExp(new IntLiteralExp(1),
                                            new LessThanEqualOp(),
                                            new IntLiteralExp(0)),
                                  typeEnvironment,new ClassName("foo"));
        assertEquals(expected, new BoolType());
    }
    @Test(expected = TypeErrorException.class)
    public void testLessThanEqualException() throws TypeErrorException{
        
        final Map<Var, Type> typeEnvironment = new HashMap<Var, Type>();
        Type expected =  emptyTypechecker().typeofOp(new OpExp(new BoolLiteralExp(true),
                                            new LessThanEqualOp(),
                                            new IntLiteralExp(0)),
                                  typeEnvironment,new ClassName("foo"));
    }
    @Test(expected = TypeErrorException.class)
    public void testLessThanEqualExceptionTwo() throws TypeErrorException{
        
        final Map<Var, Type> typeEnvironment = new HashMap<Var, Type>();
        Type expected =  emptyTypechecker().typeofOp(new OpExp(new IntLiteralExp(0),
                                            new LessThanEqualOp(),
                                            new BoolLiteralExp(true)),
                                  typeEnvironment,new ClassName("foo"));
    }
    @Test
    public void testGreaterThan() throws TypeErrorException{
        
        final Map<Var, Type> typeEnvironment = new HashMap<Var, Type>();
        Type expected =  emptyTypechecker().typeofOp(new OpExp(new IntLiteralExp(1),
                                            new GreaterThanOp(),
                                            new IntLiteralExp(0)),
                                  typeEnvironment,new ClassName("foo"));
    }
    @Test(expected = TypeErrorException.class)
    public void testGreaterThanException() throws TypeErrorException{
        
        final Map<Var, Type> typeEnvironment = new HashMap<Var, Type>();
        Type expected =  emptyTypechecker().typeofOp(new OpExp(new BoolLiteralExp(true),
                                            new GreaterThanOp(),
                                            new IntLiteralExp(0)),
                                  typeEnvironment,new ClassName("foo"));
    }
    @Test(expected = TypeErrorException.class)
    public void testGreaterThanExceptionTwo() throws TypeErrorException{
        
        final Map<Var, Type> typeEnvironment = new HashMap<Var, Type>();
        Type expected =  emptyTypechecker().typeofOp(new OpExp(new IntLiteralExp(0),
                                            new GreaterThanOp(),
                                            new BoolLiteralExp(true)),
                                  typeEnvironment,new ClassName("foo"));
    }
    @Test
    public void testGreaterThanEqual () throws TypeErrorException{
         
        final Map<Var, Type> typeEnvironment = new HashMap<Var, Type>();
        Type expected =  emptyTypechecker().typeofOp(new OpExp(new IntLiteralExp(1),
                                            new GreaterThanEqualOp(),
                                            new IntLiteralExp(0)),
                                  typeEnvironment,new ClassName("foo"));
    }
    @Test(expected = TypeErrorException.class)
    public void testGreaterThanEqualException() throws TypeErrorException{
        
        final Map<Var, Type> typeEnvironment = new HashMap<Var, Type>();
        Type expected =  emptyTypechecker().typeofOp(new OpExp(new BoolLiteralExp(true),
                                            new GreaterThanEqualOp(),
                                            new IntLiteralExp(0)),
                                  typeEnvironment,new ClassName("foo"));
    }
    @Test(expected = TypeErrorException.class)
    public void testGreaterThanEqualExceptionOne() throws TypeErrorException{
        
        final Map<Var, Type> typeEnvironment = new HashMap<Var, Type>();
        Type expected =  emptyTypechecker().typeofOp(new OpExp(new IntLiteralExp(0),
                                            new GreaterThanEqualOp(),
                                            new BoolLiteralExp(true)),
                                  typeEnvironment,new ClassName("foo"));
    }
    /**
        @Test(expected = TypeErrorException.class)
    public void testIntTypePlusThree() throws TypeErrorException{
        
        final Map<Var, Type> typeEnvironment = new HashMap<Var, Type>();
        Type expected =  emptyTypechecker().typeofOp(new OpExp(new BoolLiteralExp(true),
                                            new PlusOp(),
                                            new IntLiteralExp(1)),
                                  typeEnvironment,new ClassName("foo"));
    } */
    @Test
    public void testMinusOp() throws TypeErrorException{
        
        final Map<Var, Type> typeEnvironment = new HashMap<Var, Type>();
        Type expected =  emptyTypechecker().typeofOp(new OpExp(new IntLiteralExp(1),
                                            new MinusOP(),
                                            new IntLiteralExp(1)),
                                  typeEnvironment,new ClassName("foo"));
        assertEquals(expected, new IntType());
    }
    @Test(expected = TypeErrorException.class)
    public void testMinusOpExceptionOne() throws TypeErrorException{
        
        final Map<Var, Type> typeEnvironment = new HashMap<Var, Type>();
        Type expected =  emptyTypechecker().typeofOp(new OpExp(new BoolLiteralExp(true),
                                            new MinusOP(),
                                            new IntLiteralExp(1)),
                                  typeEnvironment,new ClassName("foo"));
        // assertEquals(expected, new IntType());
    }
     @Test(expected = TypeErrorException.class)
    public void testMinusOpExceptionTwo() throws TypeErrorException{
        
        final Map<Var, Type> typeEnvironment = new HashMap<Var, Type>();
        Type expected =  emptyTypechecker().typeofOp(new OpExp(new IntLiteralExp(1),
                                            new MinusOP(),
                                            new BoolLiteralExp(true)),
                                  typeEnvironment,new ClassName("foo"));
        // assertEquals(expected, new IntType());
    }
    @Test
    public void testTimeOp() throws TypeErrorException{
        
        final Map<Var, Type> typeEnvironment = new HashMap<Var, Type>();
        Type expected =  emptyTypechecker().typeofOp(new OpExp(new IntLiteralExp(1),
                                            new TimesOp(),
                                            new IntLiteralExp(1)),
                                  typeEnvironment,new ClassName("foo"));
        assertEquals(expected, new IntType());
    }
    @Test(expected = TypeErrorException.class)
    public void testTimeOpExceptionOne() throws TypeErrorException{
        
        final Map<Var, Type> typeEnvironment = new HashMap<Var, Type>();
        Type expected =  emptyTypechecker().typeofOp(new OpExp(new BoolLiteralExp(true),
                                            new TimesOp(),
                                            new IntLiteralExp(1)),
                                  typeEnvironment,new ClassName("foo"));
        assertEquals(expected, new IntType());
    }
     @Test(expected = TypeErrorException.class)
    public void testTimeOpExceptionTwo() throws TypeErrorException{
        
        final Map<Var, Type> typeEnvironment = new HashMap<Var, Type>();
        Type expected =  emptyTypechecker().typeofOp(new OpExp(new IntLiteralExp(1),
                                            new TimesOp(),
                                            new BoolLiteralExp(true)),
                                  typeEnvironment,new ClassName("foo"));
        assertEquals(expected, new IntType());
    }
    @Test
    public void testDivideOp() throws TypeErrorException{
        
        final Map<Var, Type> typeEnvironment = new HashMap<Var, Type>();
        Type expected =  emptyTypechecker().typeofOp(new OpExp(new IntLiteralExp(1),
                                            new DivideOp(),
                                            new IntLiteralExp(1)),
                                  typeEnvironment,new ClassName("foo"));
        assertEquals(expected, new IntType());
    }
    @Test(expected = TypeErrorException.class)
    public void testDivideOpExceptionOne() throws TypeErrorException{
        
        final Map<Var, Type> typeEnvironment = new HashMap<Var, Type>();
        Type expected =  emptyTypechecker().typeofOp(new OpExp(new BoolLiteralExp(true),
                                            new DivideOp(),
                                            new IntLiteralExp(1)),
                                  typeEnvironment,new ClassName("foo"));
        assertEquals(expected, new IntType());
    }
    @Test(expected = TypeErrorException.class)
    public void testDivideOpExceptionTwo() throws TypeErrorException{
        
        final Map<Var, Type> typeEnvironment = new HashMap<Var, Type>();
        Type expected =  emptyTypechecker().typeofOp(new OpExp(new IntLiteralExp(1),
                                            new DivideOp(),
                                            new BoolLiteralExp(true)),
                                  typeEnvironment,new ClassName("foo"));
        assertEquals(expected, new IntType());
    }
    //test while statement:
    // while (true) {
    // int x = 17;
    // }
    @Test
    public void testWhileStatement() throws TypeErrorException{
        final Map<Var, Type> typeEnvironment = new HashMap<Var, Type>();
        final Map<Var, Type> expectedRes = new HashMap<Var, Type>();
        Statement body = new VariableInitializationStmt(
            new VarDec(new IntType(),new Var("x")), new IntLiteralExp(17));
        WhileStmt whileStmt = new WhileStmt(new BoolLiteralExp(true), body);
        Map<Var, Type> received =  emptyTypechecker().isWellTypedStmt(whileStmt, 
                                                            typeEnvironment,
                                                            new ClassName("foo"),
                                                            new BoolType());
        assertEquals(expectedRes, received);
    }
    //test while with non-bool condition:
    // while (1+1) {
    // int x = 17;
    // }
    @Test(expected = TypeErrorException.class)
    public void testWhileStatementException() throws TypeErrorException{
        final Map<Var, Type> typeEnvironment = new HashMap<Var, Type>();
        final Map<Var, Type> expectedRes = new HashMap<Var, Type>();
        Statement body = new VariableInitializationStmt(
            new VarDec(new IntType(),new Var("x")), new IntLiteralExp(17));
        WhileStmt whileStmt = new WhileStmt(new OpExp(new IntLiteralExp(1),
                                            new PlusOp(),
                                            new IntLiteralExp(1)), body);
        Map<Var, Type> received =  emptyTypechecker().isWellTypedStmt(whileStmt, 
                                                            typeEnvironment,
                                                            new ClassName("foo"),
                                                            new BoolType());
        assertEquals(expectedRes, received);
    }
    //test if statement:
    // if (true) {
    // int x = 1;
    // }else{
    // int x = 2;
    // }
    @Test
    public void testIfStatement() throws TypeErrorException{
        final Map<Var, Type> typeEnvironment = new HashMap<Var, Type>();
        final Map<Var, Type> expected = new HashMap<Var, Type>();
        Statement trueBranch = new VariableInitializationStmt(
            new VarDec(new IntType(),new Var("x")), new IntLiteralExp(1));
        Statement falseBranch = new VariableInitializationStmt(
            new VarDec(new IntType(),new Var("x")), new IntLiteralExp(1));
        IfStatement ifStatement = new IfStatement(new BoolLiteralExp(true), trueBranch, falseBranch);
        Map<Var, Type> received =  emptyTypechecker().isWellTypedStmt(ifStatement,
                                                            typeEnvironment,
                                                            new ClassName("foo"),
                                                            new IntType());
        // assertEquals(expected,received);
    }
    //test if statement with non-bool condition:
    // if (1+1) {
    // int x = 1;
    // }else{
    // int x = 2;
    // }
    @Test(expected = TypeErrorException.class)
    public void testIfStatementException() throws TypeErrorException{
        final Map<Var, Type> typeEnvironment = new HashMap<Var, Type>();
        final Map<Var, Type> expected = new HashMap<Var, Type>();
        Statement trueBranch = new VariableInitializationStmt(
            new VarDec(new IntType(),new Var("x")), new IntLiteralExp(1));
        Statement falseBranch = new VariableInitializationStmt(
            new VarDec(new IntType(),new Var("x")), new IntLiteralExp(1));
        Expression guard = new OpExp(new IntLiteralExp(1),
                                            new PlusOp(),
                                            new IntLiteralExp(1));
        IfStatement ifStatement = new IfStatement(guard,trueBranch, falseBranch);
        Map<Var, Type> received =  emptyTypechecker().isWellTypedStmt(ifStatement,
                                                            typeEnvironment,
                                                            new ClassName("foo"),
                                                            new IntType());
        assertEquals(expected,received);
    }
    @Test
    public void testExpStmt() throws TypeErrorException{
        final Map<Var, Type> typeEnvironment = new HashMap<Var, Type>();
        final Map<Var, Type> expected = new HashMap<Var, Type>();
        Statement expStmt = new ExpStmt(new IntLiteralExp(1));
        Map<Var, Type> received =  emptyTypechecker().isWellTypedStmt(expStmt,
                                                            typeEnvironment,
                                                            new ClassName("foo"),
                                                            new IntType());
        assertEquals(expected,received);
    }
    @Test
    public void testReturnNonVoid() throws TypeErrorException{
        final Map<Var, Type> typeEnvironment = new HashMap<Var, Type>();
        final Map<Var, Type> expected = new HashMap<Var, Type>();
        ReturnNonVoidStmt returnNonVoidStmt = new ReturnNonVoidStmt(new IntLiteralExp(1));
        Map<Var, Type> received =  emptyTypechecker().isWellTypedStmt(returnNonVoidStmt,
                                                            typeEnvironment,
                                                            new ClassName("foo"),
                                                            new IntType());
        assertEquals(expected,received);
    }
    @Test
    public void testPrintStatement() throws TypeErrorException{
        final Map<Var, Type> typeEnvironment = new HashMap<Var, Type>();
        final Map<Var, Type> expected = new HashMap<Var, Type>();
        PrintlnStmt printStmt = new PrintlnStmt(new IntLiteralExp(1));
        Map<Var, Type> received =  emptyTypechecker().isWellTypedStmt(printStmt,
                                                            typeEnvironment,
                                                            new ClassName("foo"),
                                                            new IntType());
        assertEquals(expected,received);
    }
    @Test
    public void testReturnVoid() throws TypeErrorException{
        final Map<Var, Type> typeEnvironment = new HashMap<Var, Type>();
        final Map<Var, Type> expected = new HashMap<Var, Type>();
        ReturnVoidStmt returnVoidStmt = new ReturnVoidStmt();
        Map<Var, Type> received =  emptyTypechecker().isWellTypedStmt(returnVoidStmt,
                                                            typeEnvironment,
                                                            new ClassName("foo"),
                                                            new VoidType());
        assertEquals(expected,received);
    }
    //test block statement:
    // {
    // int x = 1;
    // }
    @Test
    public void testBlockStatement() throws TypeErrorException{
        final Map<Var, Type> typeEnvironment = new HashMap<Var, Type>();
        final Map<Var, Type> expected = new HashMap<Var, Type>();
        List<Statement> body = new ArrayList<Statement>();
        body.add(new PrintlnStmt(new IntLiteralExp(1)));
        BlockStmt blockStmt = new BlockStmt(body);
        Map<Var, Type> received =  emptyTypechecker().isWellTypedStmt(blockStmt,
                                                            typeEnvironment,
                                                            new ClassName("foo"),
                                                            new VoidType());
        assertEquals(expected,received);
    }
    @Test(expected = TypeErrorException.class)
    public void testReturnInProgramEntryPointException() throws TypeErrorException{
        final Map<Var, Type> typeEnvironment = new HashMap<Var, Type>();
        final Map<Var, Type> expected = new HashMap<Var, Type>();
        ReturnVoidStmt returnVoidStmt = new ReturnVoidStmt();
        Map<Var, Type> received =  emptyTypechecker().isWellTypedStmt(returnVoidStmt,
                                                            typeEnvironment,
                                                            new ClassName("foo"),
                                                            null);
        //assertEquals(expected,received);
    }
    @Test(expected = TypeErrorException.class)
    public void testUnspportStatement() throws TypeErrorException{
        final Map<Var, Type> typeEnvironment = new HashMap<Var, Type>();
        Map<Var, Type> received =  emptyTypechecker().isWellTypedStmt(null,
                                                            typeEnvironment,
                                                            new ClassName("foo"),
                                                            null);
    }
}