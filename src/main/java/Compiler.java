
// import typechecker.*;
// import lexer.*;
// import parser.*;
import codegenerator.*;
import codegenerator.parser.PaserCodeGen;
import lexer.TokenizerException;
import parser.ParseException;
import typechecker.TypeErrorException;
import typechecker.Typechecker;
import codegenerator.CodeGeneratorException;
import codegenerator.lexer.TokenizerCodeGen;
import typechecker.parser.Program;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Compiler {
    //read test.snakebyte file ouput test.py file
    public static void printUsage() {
        System.out.println("Takes the following params:");
        System.out.println("-Input filename (.snakebyte)");
        System.out.println("-Output filename (.py)");
    }

    public static String fileContentsAsString(final String inputFilename) throws IOException {
        final StringBuilder builder = new StringBuilder();
        final BufferedReader reader = new BufferedReader(new FileReader(inputFilename));
        try {
            String line = null;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
                builder.append("\n");
            }
            return builder.toString();
        } finally {
            reader.close();
        }
    }

    public static void compile(final String inputFilename,
            final String outputFilename)
            throws IOException,
            TokenizerException,
            ParseException,
            TypeErrorException,
            CodeGeneratorException {
        final String input = fileContentsAsString(inputFilename);
        //compile error, confusion between lexer.Token and Parser.Token
        final Program program = PaserCodeGen.parse(TokenizerCodeGen.tokenize(input));

        Typechecker.typecheck(program);
        final PrintWriter output = new PrintWriter(new BufferedWriter(new FileWriter(outputFilename)));
        try {
            // this is not ready to run yet
            CodeGenerator.generateCode(program, output);

        } finally {
            output.close();
        }
    }

    public static void main(final String[] args)
            throws IOException,
            TokenizerException,
            ParseException,
            TypeErrorException,
            CodeGeneratorException {
        if (args.length != 2) {
            printUsage();
        } else {
            compile(args[0], args[1]);
        }
    }
}