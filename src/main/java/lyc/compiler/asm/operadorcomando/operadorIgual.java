package lyc.compiler.asm.operadorcomando;
import lyc.compiler.tercetos.*;
import lyc.compiler.asm.operadorcomando.*;
import lyc.compiler.table.*;
import java_cup.runtime.Symbol;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

public class operadorIgual implements IOperadorComando {
   
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

        } else {
            // Quitar corchetes visuales 
            int indiceTarget = Integer.parseInt(operando1.replace("[", "").replace("]", ""));
            if(terceto.getIndice() - indiceTarget > 1 && ((boolean)trunc) == false){
                
                if (listaTercetos.get(indiceTarget -1 ).getOperador().matches("[0-9]+(\\.[0-9]+)?")) {
                    respuesta.add("\tFLD _" + listaTercetos.get(indiceTarget -1 ).getOperador().replace(".", "x"));
                } else if(!operador.contains("\"") && !operador.contains(".")){
                    respuesta.add("\tFLD " + listaTercetos.get(indiceTarget -1 ).getOperador());
                } 

            }   
        }
        trunc = false;

        respuesta.add("\tFSTP " + operando2);
        respuesta.add("");

        return respuesta;
    }
}
