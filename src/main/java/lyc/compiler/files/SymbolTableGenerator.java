package lyc.compiler.files;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;
import java.util.ArrayList;

import lyc.compiler.table.Simbolo; 

public class SymbolTableGenerator implements FileGenerator {

    private final ArrayList<Simbolo> symbolTable;

    public SymbolTableGenerator(ArrayList<Simbolo> tablaSimbolos) {
        this.symbolTable = tablaSimbolos;
    }

    @Override
    public void generate(FileWriter fileWriter) throws IOException {

        fileWriter.write(String.format("%-10s | %-15s | %-10s | %-8s%n",
                "NOMBRE", "TIPO DATO", "VALOR", "LONGITUD"));

        for (Simbolo symbol : symbolTable) {
            fileWriter.write(
                String.format("%-10s | %-15s | %-10s | %-10s%n",
                    symbol.getNombre(),
                    symbol.getTipoFormateado(), 
                    symbol.getValor(),
                    Objects.toString(symbol.getLongitud(), "")
                )
            );
        }
    }

}
