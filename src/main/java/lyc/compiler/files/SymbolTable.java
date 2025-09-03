package lyc.compiler.files;

import java.io.FileWriter;
import java.io.IOException;

public class SymbolTable {
	private String name;
    private String type;
    private Object value;
    private int length;

    public SymbolTable(String name, String type, Object value, int length) {
        this.name = name;
        this.type = type;
        this.value = value;
        this.length = length;
    }
    
    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public Object getValue() {
        return value;
    }

    public int getLength() {
        return length;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
