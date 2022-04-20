package typechecker;

import typechecker.parser.*;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
public class TypecheckerTest {
    public static final Typechecker emptyTypechecker =
        new Typechecker(new Program(new ArrayList<ClassDef>(),
                                    new ExpStmt(new IntLiteralExp(0))));

    @Test
    public void testVariableInScope() throws TypeErrorException {
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
        final Map<Var, Type> typeEnvironment = new HashMap<Var, Type>();
        emptyTypechecker.typeofVariable(new VarExp(new Var("x")),
                                        typeEnvironment);
    }

    @Test
    public void testThisInClass() throws TypeErrorException {
        assertEquals(new ClassNameType(new ClassName("foo")),
                     emptyTypechecker.typeofThis(new ClassName("foo")));
    }

    @Test(expected = TypeErrorException.class)
    public void testThisNotInClass() throws TypeErrorException {
        emptyTypechecker.typeofThis(null);
    }
    @Test
    public void testIntType() throws TypeErrorException {
        final Map<Var, Type> typeEnvironment = new HashMap<Var, Type>();
       Type expected =  emptyTypechecker.typeofOp(new OpExp(new IntLiteralExp(1),
                                            new PlusOp(),
                                            new IntLiteralExp(0)),
                                  typeEnvironment,new ClassName("foo"));
        assertEquals(expected, new IntType());
    }
    @Test
    public void testBoolTypeLessThan() throws TypeErrorException{
        final Map<Var, Type> typeEnvironment = new HashMap<Var, Type>();
        Type expected =  emptyTypechecker.typeofOp(new OpExp(new IntLiteralExp(1),
                                            new LessThanOp(),
                                            new IntLiteralExp(0)),
                                  typeEnvironment,new ClassName("foo"));
        assertEquals(expected, new BoolType());
    }
    @Test
    public void testBoolTypeEqualsOne() throws TypeErrorException{
        final Map<Var, Type> typeEnvironment = new HashMap<Var, Type>();
        Type expected =  emptyTypechecker.typeofOp(new OpExp(new IntLiteralExp(1),
                                            new EqualsOp(),
                                            new IntLiteralExp(0)),
                                  typeEnvironment,new ClassName("foo"));
        assertEquals(expected, new BoolType());
    }
    @Test(expected = TypeErrorException.class)
    public void testBoolTypeLessThanTwo() throws TypeErrorException{
        final Map<Var, Type> typeEnvironment = new HashMap<Var, Type>();
        Type expected =  emptyTypechecker.typeofOp(new OpExp(new IntLiteralExp(1),
                                            new LessThanOp(),
                                            new BoolLiteralExp(true)),
                                  typeEnvironment,new ClassName("foo"));
    }
    @Test(expected = TypeErrorException.class)
    public void testIntTypePlusTwo() throws TypeErrorException{
        final Map<Var, Type> typeEnvironment = new HashMap<Var, Type>();
        Type expected =  emptyTypechecker.typeofOp(new OpExp(new IntLiteralExp(1),
                                            new PlusOp(),
                                            new BoolLiteralExp(true)),
                                  typeEnvironment,new ClassName("foo"));
    }
    @Test(expected = TypeErrorException.class)
    public void testBoolTypeEqualsTwo() throws TypeErrorException{
        final Map<Var, Type> typeEnvironment = new HashMap<Var, Type>();
        Type expected =  emptyTypechecker.typeofOp(new OpExp(new IntLiteralExp(1),
                                            new EqualsOp(),
                                            new BoolLiteralExp(true)),
                                  typeEnvironment,new ClassName("foo"));
        //assertEquals(expected, new BoolType());
    }
}