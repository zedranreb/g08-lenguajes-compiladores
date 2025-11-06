public class ManejadorAssembler {
    private ArrayList<String> asmInstructions;
    private ArrayList<Simbolo> tabla_simbolos;
    private ArrayList<Tercetos> tercetos;
    private HashMap<Integer, String> tercetoLabels;


    public ManejadorAssembler(ArrayList<Simbolo> tabla_simbolos, ArrayList<Tercetos> tercetos) {
        this.tabla_simbolos = tabla_simbolos;
        this.tercetos = tercetos;
        this.asmInstructions = new ArrayList<>();
        this.tercetoLabels = new HashMap<>();
    }

    public void generateAssembler() {
        asignarEtiquetasATercetos();
        filtrarEtiquetasBranch();
        insertarCabezera();
        insertarTablaDeSimbolos();
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
        for(Terceto terceto : tercetos) {
            String operador = terceto.getOperador();
            if (       operador.equals("BLE") || operador.equals("BGE") 
                    || operador.equals("BLT") || operador.equals("BGT") 
                    || operador.equals("BNE") || operador.equals("BEQ")
                    || operador.equals("BI")
                ) {
                try {
                    // Limpiar los corchetes para obtener el número de terceto
                    int destino = Integer.parseInt(terceto.getOperando1().replace("[", "").replace("]", "").replace("terceto", "").replace("_", ""));
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
}
