package lyc.compiler.asm.operadorcomando;
import lyc.compiler.tercetos.*;
import lyc.compiler.asm.operadorcomando.*;
import lyc.compiler.table.*;
import java_cup.runtime.Symbol;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

public class operadorWriteLN implements IOperadorComando {
   
	public List<String> ejecutar(Tercetos terceto, HashMap<Integer, String> listaEtiquetas, ArrayList<Tercetos> listaTercetos, Object trunc, ArrayList<Simbolo> tabla_simbolos) {
        String operando1 = terceto.getOperando1();
    	List<String> respuesta = new ArrayList<String>();
    	
        if(SymbolTable.getSymbolTypeStatic("_"+operando1, tabla_simbolos)== "String")
        	respuesta.add("\tDisplayString " + "_"+operando1);
        else if (SymbolTable.getSymbolTypeStatic(operando1, tabla_simbolos)== "String")
        	respuesta.add("\tDisplayString "+operando1);
        else if (SymbolTable.getSymbolTypeStatic(operando1, tabla_simbolos)== "DATE_CONVERTED")
        	respuesta.add("\tDisplayFloat "+operando1+",0");
        else if(SymbolTable.getSymbolTypeStatic("_"+operando1, tabla_simbolos)== "Float" ||
        		SymbolTable.getSymbolTypeStatic("_"+operando1, tabla_simbolos)== "Int" )
        	respuesta.add("\tDisplayFloat _" + operando1+ ",2");
        else
        	respuesta.add("\tDisplayFloat " + operando1+ ",2");
        	respuesta.add("\tmov dx, OFFSET saltoLinea ");
        	respuesta.add("\tmov ah, 9 ");
        	respuesta.add("\tint 21h ");
        
        return respuesta;
    }
}
