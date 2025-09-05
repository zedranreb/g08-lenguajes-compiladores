package lyc.compiler.files;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import lyc.compiler.tabla_simbolos.SymbolTable;

public class SymbolTableGenerator implements FileGenerator{

    private final ArrayList<SymbolTable> symbolTable;

    public SymbolTableGenerator(ArrayList<SymbolTable> tablaSimbolos) {
        this.symbolTable = tablaSimbolos;
    }

    @Override
    public void generate(FileWriter fileWriter) throws IOException {
        fileWriter.write("NOMBRE | TIPODATO | VALOR | LONGITUD \n");
        for (SymbolTable symbol : symbolTable) {
            fileWriter.write(symbol.getNombre() + " | " + symbol.getTipoDatoImprimir() + " | " + symbol.getValor() + " | " + Objects.toString(symbol.getLongitud(), "") + "\n");
        }
    }
}
