package lyc.compiler.asm.operadorcomando;
import lyc.compiler.tercetos.*;
import lyc.compiler.asm.operadorcomando.*;
import lyc.compiler.table.*;
import java_cup.runtime.Symbol;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
public class operadorDivision implements IOperadorComando {
   
    public List<String> ejecutar(Tercetos terceto, HashMap<Integer, String> listaEtiquetas, ArrayList<Tercetos> listaTercetos, Object trunc, ArrayList<Simbolo> tabla_simbolos) {
        List<String> respuesta = new ArrayList<String>();
        String operador = terceto.getOperador();
        String operando1 = terceto.getOperando1();
        String operando2 = terceto.getOperando2();

        
        if(!operando1.contains("[")){
            if(operando1.contains("@"))
                respuesta.add("\tFLD " + operando1);
            else
                respuesta.add("\tFLD _" + operando1);
        }

        if(!operando2.contains("[")){
            if(operando2.contains("@"))
                respuesta.add("\tFLD " + operando2);
            else
                respuesta.add("\tFLD _" + operando2);
        }
        
        respuesta.add("\tFDIV ");
        respuesta.add("\tfrndint ");
        
        return respuesta;
    }
    
}
