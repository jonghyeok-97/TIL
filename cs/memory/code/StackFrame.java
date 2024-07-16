package code;

public class StackFrame {

    private String name;
    private int address;
    private Pointer pointer;

    public StackFrame(String name, int address) {
        this.name = name;
        this.address = address;
    }

    public StackFrame(Pointer pointer, int address) {
        this.address = address;
        this.pointer = pointer;
    }

    public int getAddress() {
        return address;
    }

    public Pointer getPointer() {
        return pointer;
    }

    public boolean has(String name) {
        return this.name.equals(name);
    }

    public boolean hasPointer() {
        return pointer != null;
    }

    public String getName() {
        return name;
    }
}
