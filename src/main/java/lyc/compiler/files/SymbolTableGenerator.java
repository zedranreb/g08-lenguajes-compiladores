package lyc.compiler.files;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;
import java.util.ArrayList;

import lyc.compiler.table.Simbolo;

public class SymbolTableGenerator implements FileGenerator{

    private final ArrayList<Simbolo> symbolTable;

    public SymbolTableGenerator(ArrayList<Simbolo> tablaSimbolos) {
        this.symbolTable = tablaSimbolos;
    }

    @Override
    public void generate(FileWriter fileWriter) throws IOException {
        fileWriter.write("NOMBRE | TIPO DATO | VALOR | LONGITUD \n");
        for (Simbolo symbol : symbolTable) {
        	fileWriter.write(String.valueOf(symbol));
        }
    }
}
