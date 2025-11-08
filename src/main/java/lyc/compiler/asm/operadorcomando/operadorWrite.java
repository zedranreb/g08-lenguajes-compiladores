package lyc.compiler.asm.operadorcomando;
import lyc.compiler.tercetos.*;
import lyc.compiler.asm.operadorcomando.*;
import lyc.compiler.table.*;
import java_cup.runtime.Symbol;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

public class operadorWrite implements IOperadorComando {
   
	public List<String> ejecutar(Tercetos terceto, HashMap<Integer, String> listaEtiquetas, ArrayList<Tercetos> listaTercetos, Object trunc, ArrayList<Simbolo> tabla_simbolos) {
        String operando1 = terceto.getOperando1();
    	List<String> respuesta = new ArrayList<String>();

    	System.out.print(operando1 + " : ");
    	System.out.println(SymbolTable.getSymbolTypeStatic(operando1, tabla_simbolos));

    	if(SymbolTable.getSymbolTypeStatic(operando1, tabla_simbolos)== "String")
    		respuesta.add("\tDisplayString " + operando1);
        else if (SymbolTable.getSymbolTypeStatic(operando1, tabla_simbolos)== "String")
        	respuesta.add("\tDisplayString "+operando1);
        else if(SymbolTable.getSymbolTypeStatic(operando1, tabla_simbolos)== "Float" || SymbolTable.getSymbolTypeStatic(operando1, tabla_simbolos)== "Int" )
        	respuesta.add("\tDisplayFloat " + operando1 + ",2");
        else
        	respuesta.add("\tDisplayFloat _" + operando1+ ",2");

        return respuesta;
    }
}
