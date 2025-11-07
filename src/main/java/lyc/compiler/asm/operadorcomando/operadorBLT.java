package lyc.compiler.asm.operadorcomando;
import lyc.compiler.tercetos.*;
import lyc.compiler.asm.operadorcomando.*;
import lyc.compiler.table.*;
import java_cup.runtime.Symbol;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
public class operadorBLT implements IOperadorComando {
   
    public List<String> ejecutar(Tercetos terceto, HashMap<Integer, String> listaEtiquetas, ArrayList<Tercetos> listaTercetos, Object trunc, ArrayList<Simbolo> tabla_simbolos) {
        List<String> respuesta = new ArrayList<String>();
        String operador = terceto.getOperador();
        String operando1 = terceto.getOperando1();
        String operando2 = terceto.getOperando2();

        int destinoBLT = Integer.parseInt(operando1.replace("[", "").replace("]", ""));
                    
        if (listaEtiquetas.get(destinoBLT) == null) { // si no hay más instrucciones salta a la etiqueta final
            respuesta.add("\tJB FINAL_LABEL" );
            respuesta.add("");
            
        } else {
            respuesta.add("\tJB " + listaEtiquetas.get(destinoBLT));
            respuesta.add("");
        }
        
        return respuesta;
    }
}
