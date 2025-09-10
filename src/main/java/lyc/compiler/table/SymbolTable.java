package lyc.compiler.table;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class SymbolTable {

	private ArrayList<Simbolo> symbolTable = new ArrayList<>();

	public void addNewSymbols(Simbolo symbol) {
		symbolTable.add(symbol);
	}

	public void addNewSymbols(ArrayList<Simbolo> newSymbols, String dataType) {
		for (Simbolo symbol : newSymbols) {
			try {
				if (!symbolTable.contains(symbol)) {
					symbol.setTipoDato(dataType);
					symbolTable.add(symbol);
				}
			} catch (Exception e) {
				System.out.println("Error al procesar el símbolo: " + e.getMessage());
			}
		}
	}

	public ArrayList<Simbolo> getSymbolTable() {
		return symbolTable;
	}

	public boolean containsSymbol(String symbolName) {
		for (Simbolo symbol : symbolTable) {
			if (symbol.getNombre().equals(symbolName)) {
				return true;
			}
		}
		return false;
	}

	public Simbolo getSymbol(String symbolName) {
		for (Simbolo symbol : symbolTable) {
			if (symbol.getNombre().equals(symbolName)) {
				return symbol;
			}
		}
		return null;
	}

}