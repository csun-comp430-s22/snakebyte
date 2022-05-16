package codegenerator;

import typechecker.*;
import typechecker.parser.ClassName;
import typechecker.parser.MethodName;
import typechecker.parser.ClassDef;
import typechecker.parser.MethodDef;
import typechecker.parser.Program;
import lexer.*;
import parser.*;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.Iterator;

import java.io.PrintWriter;
import java.io.IOException;

public class CodeGenerator {

    public final Program program;
    public final PrintWriter output;

    public final Map<ClassName, ClassDef> classes;
    public final Map<ClassName, Map<MethodName, MethodDef>> methods;
    public final Map<ClassName, VTable> vtables;

    public CodeGenerator(final Program program,
            final PrintWriter output) throws TypeErrorException {
        this.program = program;
        this.output = output;
        classes = Typechecker.makeClassMap(program.classes);
        methods = Typechecker.makeMethodMap(classes);
        vtables = new HashMap<ClassName, VTable>();
        for (final ClassName className : classes.keySet()) {
            makeVTableForClass(className);
        }
    }

    private VTable makeVTableForClass(final ClassName className) throws TypeErrorException {
        VTable vtable = vtables.get(className);
        // save vtables as we create them, and only compute if needed
        if (vtable == null) {
            if (className.name.equals(Typechecker.BASE_CLASS_NAME)) {
                // object's vtable is empty
                vtable = new VTable(className);
            } else {
                // some class with a parent class
                // get a copy of the parent's vtable, and extend off of that
                final ClassDef classDef = Typechecker.getClass(className, classes);
                vtable = makeVTableForClass(classDef.extendsClassName).copyTable(className);
                for (final MethodDef methodDef : classDef.methods) {
                    vtable.addOrUpdateMethod(methodDef.methodName);
                }
            }
            vtables.put(className, vtable);
        }
        return vtable;
    }

    public static FunctionName nameManglFunctionName(final ClassName className,
            final MethodName methodName) {
        return new FunctionName(className.name + "" + methodName.name);

    }

}
