package lyc.compiler.table;

import java.util.Objects;

public class Simbolo {

    private String nombre;
    private String tipoDato;
    private String valor;
    private Integer longitud;

    public Simbolo(String nombre, String tipoDato, String valor, Integer longitud) {
        this.nombre = nombre;
        this.tipoDato = tipoDato;
        this.valor = valor;
        this.longitud = longitud;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipoDato() {
        return tipoDato;
    }

    public void setTipoDato(String tipoDato) {
        this.tipoDato = tipoDato;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public Integer getLongitud() {
        return longitud;
    }

    public void setLongitud(Integer longitud) {
        this.longitud = longitud;
    }

    @Override
    public String toString() {
        String td = tipoDato;
        if (nombre.charAt(0) == '_') { 
            if ("Float".equals(tipoDato)) { 
                td = "CONST_FLOAT";
            } else if ("Int".equals(tipoDato)) {
                td = "CONST_INT";
            } else if ("String".equals(tipoDato)) {
                td = "CONST_STR";
            }
        } 

        return nombre + " | " + td + " | " + valor + " | " + Objects.toString(longitud, "") + "\n";
    }
}