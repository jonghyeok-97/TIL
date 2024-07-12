package cpu.unit;

import cpu.unit.IR.InStructionType;

public class Bus {

    private final int dst;
    private final int op1;
    private int op2;
    private InStructionType type;

    public Bus(int dst, int op1, int op2) {
        this.dst = dst;
        this.op1 = op1;
        this.op2 = op2;
    }

    public Bus(int dst, int op) {
        this.dst = dst;
        this.op1 = op;
    }

    public InStructionType getType() {
        return type;
    }

    public int getDst() {
        return dst;
    }

    public int getOp1() {
        return op1;
    }

    public int getOp2() {
        return op2;
    }

    public void addType(InStructionType type) {
        this.type = type;
    }
}
