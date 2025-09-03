package lyc.compiler.files;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class SymbolTableGenerator implements FileGenerator{

	private List<SymbolTable> symbolTable;
	
	public SymbolTableGenerator(List<SymbolTable> symbolTable) {
		this.symbolTable = symbolTable;
	}
	
    @Override
    public void generate(FileWriter fileWriter) throws IOException {
        fileWriter.write("|  NOMBRE  |  TIPO  |  VALOR  |  LONGITUD  ");
        for (SymbolTable symbol : this.symbolTable) {
        	fileWriter.write(symbol.getName()+"  |"+symbol.getType() + "  |" + symbol.getValue() + "  |"+symbol.getLength() +"  |"+"\n");
        }

    }
}
