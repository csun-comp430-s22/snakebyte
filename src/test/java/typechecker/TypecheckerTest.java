package typechecker;

import typechecker.parser.*;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
public class TypecheckerTest {
    // the following code is not working for some reason
    // public static final Typechecker emptyTypechecker =
    //     new Typechecker(new Program(new ArrayList<ClassDef>(),
    //                                 new ExpStmt(new IntLiteralExp(0))));

    @Test
    public void testVariableInScope() throws TypeErrorException {
        final Typechecker emptyTypechecker =
        new Typechecker(new Program(new ArrayList<ClassDef>(),
                                    new ExpStmt(new IntLiteralExp(0))));
        final Type expectedType = new IntType();
        final Map<Var, Type> typeEnvironment = new HashMap<Var, Type>();
        typeEnvironment.put(new Var("x"), new IntType());
        
        final Type receivedType =
            emptyTypechecker.typeofVariable(new VarExp(new Var("x")),
                                            typeEnvironment);
        assertEquals(expectedType, receivedType);
    }
    
    @Test(expected = TypeErrorException.class)
    public void testVariableOutOfScope() throws TypeErrorException {
        final Typechecker emptyTypechecker =
        new Typechecker(new Program(new ArrayList<ClassDef>(),
                                    new ExpStmt(new IntLiteralExp(0))));
        final Map<Var, Type> typeEnvironment = new HashMap<Var, Type>();
        emptyTypechecker.typeofVariable(new VarExp(new Var("x")),
                                        typeEnvironment);
    }

    @Test
    public void testThisInClass() throws TypeErrorException {
        final Typechecker emptyTypechecker =
        new Typechecker(new Program(new ArrayList<ClassDef>(),
                                    new ExpStmt(new IntLiteralExp(0))));
        assertEquals(new ClassNameType(new ClassName("foo")),
                     emptyTypechecker.typeofThis(new ClassName("foo")));
    }

    @Test(expected = TypeErrorException.class)
    public void testThisNotInClass() throws TypeErrorException {
        final Typechecker emptyTypechecker =
        new Typechecker(new Program(new ArrayList<ClassDef>(),
                                    new ExpStmt(new IntLiteralExp(0))));
        emptyTypechecker.typeofThis(null);
    }
    @Test
    public void testIntType() throws TypeErrorException {
        final Typechecker emptyTypechecker =
        new Typechecker(new Program(new ArrayList<ClassDef>(),
                                    new ExpStmt(new IntLiteralExp(0))));
        final Map<Var, Type> typeEnvironment = new HashMap<Var, Type>();
       Type expected =  emptyTypechecker.typeofOp(new OpExp(new IntLiteralExp(1),
                                            new PlusOp(),
                                            new IntLiteralExp(0)),
                                  typeEnvironment,new ClassName("foo"));
        assertEquals(expected, new IntType());
    }
    @Test
    public void testBoolTypeLessThan() throws TypeErrorException{
        final Typechecker emptyTypechecker =
        new Typechecker(new Program(new ArrayList<ClassDef>(),
                                    new ExpStmt(new IntLiteralExp(0))));
        final Map<Var, Type> typeEnvironment = new HashMap<Var, Type>();
        Type expected =  emptyTypechecker.typeofOp(new OpExp(new IntLiteralExp(1),
                                            new LessThanOp(),
                                            new IntLiteralExp(0)),
                                  typeEnvironment,new ClassName("foo"));
        assertEquals(expected, new BoolType());
    }
    @Test
    public void testBoolTypeEqualsOne() throws TypeErrorException{
        final Typechecker emptyTypechecker =
        new Typechecker(new Program(new ArrayList<ClassDef>(),
                                    new ExpStmt(new IntLiteralExp(0))));
        final Map<Var, Type> typeEnvironment = new HashMap<Var, Type>();
        Type expected =  emptyTypechecker.typeofOp(new OpExp(new IntLiteralExp(1),
                                            new EqualsOp(),
                                            new IntLiteralExp(0)),
                                  typeEnvironment,new ClassName("foo"));
        assertEquals(expected, new BoolType());
    }
    @Test(expected = TypeErrorException.class)
    public void testBoolTypeLessThanTwo() throws TypeErrorException{
        final Typechecker emptyTypechecker =
        new Typechecker(new Program(new ArrayList<ClassDef>(),
                                    new ExpStmt(new IntLiteralExp(0))));
        final Map<Var, Type> typeEnvironment = new HashMap<Var, Type>();
        Type expected =  emptyTypechecker.typeofOp(new OpExp(new IntLiteralExp(1),
                                            new LessThanOp(),
                                            new BoolLiteralExp(true)),
                                  typeEnvironment,new ClassName("foo"));
    }
    @Test(expected = TypeErrorException.class)
    public void testBoolTypeLessThanThree() throws TypeErrorException{
        final Typechecker emptyTypechecker =
        new Typechecker(new Program(new ArrayList<ClassDef>(),
                                    new ExpStmt(new IntLiteralExp(0))));
        final Map<Var, Type> typeEnvironment = new HashMap<Var, Type>();
        Type expected =  emptyTypechecker.typeofOp(new OpExp(new BoolLiteralExp(true),
                                            new LessThanOp(),
                                            new IntLiteralExp(1)),
                                  typeEnvironment,new ClassName("foo"));
    }
    @Test(expected = TypeErrorException.class)
    public void testIntTypePlusTwo() throws TypeErrorException{
        final Typechecker emptyTypechecker =
        new Typechecker(new Program(new ArrayList<ClassDef>(),
                                    new ExpStmt(new IntLiteralExp(0))));
        final Map<Var, Type> typeEnvironment = new HashMap<Var, Type>();
        Type expected =  emptyTypechecker.typeofOp(new OpExp(new IntLiteralExp(1),
                                            new PlusOp(),
                                            new BoolLiteralExp(true)),
                                  typeEnvironment,new ClassName("foo"));
    }
    @Test(expected = TypeErrorException.class)
    public void testIntTypePlusThree() throws TypeErrorException{
        final Typechecker emptyTypechecker =
        new Typechecker(new Program(new ArrayList<ClassDef>(),
                                    new ExpStmt(new IntLiteralExp(0))));
        final Map<Var, Type> typeEnvironment = new HashMap<Var, Type>();
        Type expected =  emptyTypechecker.typeofOp(new OpExp(new BoolLiteralExp(true),
                                            new PlusOp(),
                                            new IntLiteralExp(1)),
                                  typeEnvironment,new ClassName("foo"));
    }
    @Test(expected = TypeErrorException.class)
    public void testBoolTypeEqualsTwo() throws TypeErrorException{
        final Typechecker emptyTypechecker =
        new Typechecker(new Program(new ArrayList<ClassDef>(),
                                    new ExpStmt(new IntLiteralExp(0))));
        final Map<Var, Type> typeEnvironment = new HashMap<Var, Type>();
        Type expected =  emptyTypechecker.typeofOp(new OpExp(new IntLiteralExp(1),
                                            new EqualsOp(),
                                            new BoolLiteralExp(true)),
                                  typeEnvironment,new ClassName("foo"));
    }
    @Test(expected = TypeErrorException.class)
    public void testBoolTypeEqualsThree() throws TypeErrorException{
        final Typechecker emptyTypechecker =
        new Typechecker(new Program(new ArrayList<ClassDef>(),
                                    new ExpStmt(new IntLiteralExp(0))));
        final Map<Var, Type> typeEnvironment = new HashMap<Var, Type>();
        Type expected =  emptyTypechecker.typeofOp(new OpExp(new BoolLiteralExp(true),
                                            new EqualsOp(),
                                            new IntLiteralExp(1)),
                                  typeEnvironment,new ClassName("foo"));
    }
    @Test(expected = TypeErrorException.class)
    public void testUnspportedOp() throws TypeErrorException{
        final Typechecker emptyTypechecker =
        new Typechecker(new Program(new ArrayList<ClassDef>(),
                                    new ExpStmt(new IntLiteralExp(0))));
        final Map<Var, Type> typeEnvironment = new HashMap<Var, Type>();
        Type expected =  emptyTypechecker.typeofOp(new OpExp(new IntLiteralExp(1),
                                            null,
                                            new IntLiteralExp(0)),
                                  typeEnvironment,new ClassName("foo"));
    }
    // @Test(expected = TypeErrorException.class)
    // public void testClassException() throws TypeErrorException{
    //     final Typechecker emptyTypechecker =
    //     new Typechecker(new Program(new ArrayList<ClassDef>(),
    //                                 new ExpStmt(new IntLiteralExp(0))));
    //     final Map<Var, Type> typeEnvironment = new HashMap<Var, Type>();
    //     final ClassName className = new ClassName("foo");
    //     Type expected = emptyTypechecker.typeofNew(new NewExp(className,new ArrayList<Expression>()),
    //                                                             typeEnvironment,className);
    // }
    @Test(expected = TypeErrorException.class)
    public void testTypeof() throws TypeErrorException{
        final Typechecker emptyTypechecker =
        new Typechecker(new Program(new ArrayList<ClassDef>(),
                                    new ExpStmt(new IntLiteralExp(0))));
        final Map<Var, Type> typeEnvironment = new HashMap<Var, Type>();
        Type expected =  emptyTypechecker.typeof(null,typeEnvironment, new ClassName("foo"));
    }
    @Test
    public void testTypeofvarexp() throws TypeErrorException{
        final Typechecker emptyTypechecker =
        new Typechecker(new Program(new ArrayList<ClassDef>(),
                                    new ExpStmt(new IntLiteralExp(0))));
        final Map<Var, Type> typeEnvironment = new HashMap<Var, Type>();
        typeEnvironment.put(new Var("x"), new IntType());
        Type expected =  emptyTypechecker.typeof(new VarExp(new Var("x")),typeEnvironment, new ClassName("foo"));
        assertEquals(expected,new IntType());
    }
    @Test
    public void testTypeofthis() throws TypeErrorException{
        final Typechecker emptyTypechecker =
        new Typechecker(new Program(new ArrayList<ClassDef>(),
                                    new ExpStmt(new IntLiteralExp(0))));

        final Map<Var, Type> typeEnvironment = new HashMap<Var, Type>();
        typeEnvironment.put(new Var("x"), new ClassNameType(new ClassName("foo")));
        Type expected =  emptyTypechecker.typeof(new ThisExp(),typeEnvironment, new ClassName("foo"));
        assertEquals(expected,new ClassNameType(new ClassName("foo")));
    }
    @Test
    public void testTypeofopexp() throws TypeErrorException{
        final Typechecker emptyTypechecker =
        new Typechecker(new Program(new ArrayList<ClassDef>(),
                                    new ExpStmt(new IntLiteralExp(0))));
        final Map<Var, Type> typeEnvironment = new HashMap<Var, Type>();
        typeEnvironment.put(new Var("x"), new IntType());
        Type expected =  emptyTypechecker.typeof(new OpExp(new VarExp(new Var("x")),
                                                            new PlusOp(),
                                                            new IntLiteralExp(1)),
                                                            typeEnvironment, new ClassName("foo"));
        assertEquals(expected,new IntType());
    }
}