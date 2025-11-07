package lyc.compiler.asm;
import lyc.compiler.tercetos.*;
import lyc.compiler.table.*;
import lyc.compiler.asm.operadorcomando.*;
import java_cup.runtime.Symbol;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

public class ManejadorAssembler {
    private ArrayList<String> asmInstructions;
    private ArrayList<Simbolo> tabla_simbolos;
    private ArrayList<Tercetos> tercetos;
    private HashMap<Integer, String> tercetoLabels;
    private final HashMap<String,IOperadorComando> comandos = new HashMap<>();


    public ManejadorAssembler(ArrayList<Simbolo> tabla_simbolos, ArrayList<Tercetos> tercetos) {
        this.tabla_simbolos = tabla_simbolos;
        this.tercetos = tercetos;
        this.asmInstructions = new ArrayList<>();
        this.tercetoLabels = new HashMap<>();
        this.inicializarComandos();
    }

    public void generarAssembler() {
        this.asignarEtiquetasATercetos();
        this.filtrarEtiquetasBranch();
        this.insertarCabezera();
        this.insertarTablaDeSimbolos();
        this.insertarCodigoTercetos();
    }

    private void inicializarComandos() {
        comandos.put(":=", new operadorIgual());
        comandos.put("=", new operadorIgual());
        comandos.put("DEFAULT", new operadorDEFAULT());
    }

    private void asignarEtiquetasATercetos() {
        for (int i = 0; i < tercetos.size(); i++) {
            tercetoLabels.put(i + 1, "LABEL_" + (i + 1));
        }
    }

    private void filtrarEtiquetasBranch() {
        // Dado el siguiente terceto: [46] (BLT , [61] , _).
        // Analizar cuando debe incorporarse incorporarse los saltos por Etiqueta 
        // Almacenar el 61 dentro de una lista para generar una etiqueta que nos permita saltar a este terceto.
        HashMap<Integer, String> branchLabels = new HashMap<>();
        for(Tercetos terceto : tercetos) {
            String operador = terceto.getOperador();
            if (       operador.equals("BLE") || operador.equals("BGE") 
                    || operador.equals("BLT") || operador.equals("BGT") 
                    || operador.equals("BNE") || operador.equals("BEQ")
                    || operador.equals("BI")
                ) {
                try {
                    // Limpiar los corchetes para obtener el número de terceto
                    int destino = Integer.parseInt(terceto.getOperando1().replace("[", "").replace("]", "").replace("_", ""));
                    branchLabels.put(destino, tercetoLabels.get(destino));
                } catch(Exception e) {
                    continue;
                }
                
            }
        }
        
        tercetoLabels = branchLabels;
    }

    private void insertarCabezera() {
        asmInstructions.add("include macros2.asm");
        asmInstructions.add("include number.asm\n");
        asmInstructions.add(".MODEL  LARGE");
        asmInstructions.add(".386");
        asmInstructions.add(".STACK 200h");
    }

    private void insertarTablaDeSimbolos() {
        asmInstructions.add("");
        asmInstructions.add("");
        asmInstructions.add(".DATA");

        for (Simbolo symbol : tabla_simbolos) {
            try {                
                if(symbol.getValor().isEmpty()) { // es una variable
                    asmInstructions.add("\t" + symbol.getNombre() + "\t\tdd ?" );
                } else { // es una cte
                    if(symbol.getTipoDato().equals("String")) { // el nombre del string no puede tener " "
                        asmInstructions.add("\t" + symbol.getNombre() + "\t\tdb\t\t" + symbol.getValor() +", '$'");
                    }
                    else if(symbol.getTipoDato().equals("Int")){
                        asmInstructions.add("\t" + symbol.getNombre() + "\t\tdd\t\t" + symbol.getValor()+ ".0");
                    }
                    else if(symbol.getTipoDato().equals("Float") && symbol.getNombre().contains(".")) {
                        String valor = symbol.getValor();
                    
                        if (valor.startsWith(".")) {
                            valor = "0" + valor;  
                        } else if (valor.endsWith(".")) {
                            valor = valor + "00";  
                        }
                        String nuevoNombre = valor.replace('.','x');
                        symbol.setNombre("_" + nuevoNombre);
                        symbol.setValor(valor);
                    }
                    else {
                        asmInstructions.add("\t" + symbol.getNombre() + "\t\tdd\t\t" + symbol.getValor());
                    }
                }
                if(symbol.getValor().contains("@")){
                    asmInstructions.add("\t" + symbol.getNombre() + "\t\tdd\t\t" + symbol.getValor());
                }
            } catch(Exception e){
                continue;
            }
        }

        asmInstructions.add("truncMode DW 0F7FFH ");
        asmInstructions.add("saltoLinea                  db 0Dh, 0Ah, '$'");
        asmInstructions.add("");
    }

    private void insertarCodigoTercetos() {
        asmInstructions.add(".CODE");
        asmInstructions.add("START:");
        asmInstructions.add("\tmov AX, @DATA");
        asmInstructions.add("\tmov DS, AX");
        asmInstructions.add("fldcw truncMode");
        asmInstructions.add("");
        int tercetoIndex = 1;
        Object trunc = false;

        for(Tercetos terceto : tercetos) {
            try {
                String destino = tercetoLabels.get(terceto.getIndice());
                String operador = terceto.getOperador();
                String operando1 = terceto.getOperando1();
                String operando2 = terceto.getOperando2();
                
                if (destino == null) {
                    destino = "FINAL_LABEL";
                }
                // Formatear valor datos
                if(operador.contains(".")) {
                    String contenido = operador;
                    if (contenido.startsWith(".")) { // Insertar prefijo
                        contenido = "0" + contenido; 
                    } else if (contenido.endsWith(".")) {
                        contenido = contenido + "00";  // Insertar sufijo
                    }
                    contenido = contenido.replace(".", "x");
                    contenido = "_" + contenido;
                    operador = contenido;
                }

                // Agregar etiqueta
                if (tercetoLabels.containsKey(tercetoIndex)) {
                    asmInstructions.add(tercetoLabels.get(tercetoIndex) + ":");
                }
                
                // Extraer logica de desarrollo de instrucciones a patron comando.
                IOperadorComando cmd = comandos.get(operador);
                if(cmd == null) cmd = comandos.get("DEFAULT");
                asmInstructions.addAll(cmd.ejecutar(terceto, tercetoLabels, tercetos, trunc));
                tercetoIndex++;

            } catch (Exception e) {
                continue;
            }

            asmInstructions.add("\tFINAL_LABEL:");
            asmInstructions.add("\tMOV AX, 4C00h");
            asmInstructions.add("\tINT 21h");
            asmInstructions.add("END START");
        }

        
    }

    public ArrayList<String> getAsmInstructions() {
        return asmInstructions;
    }
}
