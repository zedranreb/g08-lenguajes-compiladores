package lyc.compiler.asm.operadorcomando;

import lyc.compiler.tercetos.*;
import lyc.compiler.asm.operadorcomando.*;
import lyc.compiler.table.*;
import java_cup.runtime.Symbol;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

public class operadorDEFAULT implements IOperadorComando {
    
    public List<String> ejecutar(Tercetos terceto, HashMap<Integer, String> listaEtiquetas, ArrayList<Tercetos> listaTercetos, Object trunc, ArrayList<Simbolo> tabla_simbolos) {
        List<String> respuesta = new ArrayList<String>();
        String operador = terceto.getOperador();
        String operando1 = terceto.getOperando1();
        String operando2 = terceto.getOperando2();
        
        if (operador.matches("[0-9]+(\\.[0-9]+)?")) {
            respuesta.add("\tFLD _" + operador.replace(".", "x"));
        } else {
            if(!operador.contains("\"") && !operador.contains(".")) {
                respuesta.add("\tFLD " + operador);
            }
        }

        return respuesta;
    }
}