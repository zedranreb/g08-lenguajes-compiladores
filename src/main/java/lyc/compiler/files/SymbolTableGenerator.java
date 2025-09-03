package lyc.compiler.files;

import java.io.FileWriter;
import java.io.IOException;

public class SymbolTableGenerator implements FileGenerator{

	private List<SymbolTable> symbolTable;
	
	public SymbolTableGenerator(List<SymbolTable> symbolTable) {
		this.symbolTable = symbolTable;
	}
	
    @Override
    public void generate(FileWriter fileWriter) throws IOException {
        fileWriter.write("|  NOMBRE  |  TIPO  |  VALOR  |  LONGITUD  ");
        for (SymbolTable symbol : this.symbolTable) {
        	fileWriter.write(symbol.getNombre()+"  |"+symbol.getType() + "  |" + symbol.getValor() + "  |"+symbol.getLength() +"  |"+"\n");
        }

    }
}
