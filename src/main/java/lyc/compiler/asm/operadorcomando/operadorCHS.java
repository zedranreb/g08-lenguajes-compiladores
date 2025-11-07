package lyc.compiler.asm.operadorcomando;
import lyc.compiler.tercetos.*;
import lyc.compiler.asm.operadorcomando.*;
import lyc.compiler.table.*;
import java_cup.runtime.Symbol;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
public class operadorCHS implements IOperadorComando {
   
    public List<String> ejecutar(Tercetos terceto, HashMap<Integer, String> listaEtiquetas,  ArrayList<Tercetos> listaTercetos, Object trunc) {
        List<String> respuesta = new ArrayList<String>();
        String operador = terceto.getOperador();
        String operando1 = terceto.getOperando1();
        
        if(operando1.contains("@"))
            respuesta.add("\tFLD " + operando1);
        else
            respuesta.add("\tFLD _" + operando1);
        
        respuesta.add("\tFCHS ");
        
        return respuesta;
    }
}
