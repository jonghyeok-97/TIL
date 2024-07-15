package code.cpu;

import code.cpu.unit.Bus;
import code.cpu.unit.IR.InStructionType;

// [ADD, SUB, AND, OR] 명령어만 처리 가능
public class ArithmeticLogicUnit {

    private final Registers registers;

    public ArithmeticLogicUnit(Registers registers) {
        this.registers = registers;
    }

    public void and(int dstReg, int opReg1, int opReg2) {
        int opValue1 = registers.getValue(opReg1);
        int opValue2 = registers.getValue(opReg2);

        int result = opValue1 & opValue2;

        registers.load(dstReg, result);
    }

    public void or(int dstReg, int opReg1, int opReg2) {
        int opValue1 = registers.getValue(opReg1);
        int opValue2 = registers.getValue(opReg2);

        int result = opValue1 | opValue2;

        registers.load(dstReg, result);
    }

    public void addReg(int dstReg, int opReg1, int opReg2) {
        int opValue1 = registers.getValue(opReg1);
        int opValue2 = registers.getValue(opReg2);

        registers.load(dstReg, opValue1 + opValue2);
    }

    public void addValue(int dstReg, int opReg, int number) {
        int opValue = registers.getValue(opReg);

        registers.load(dstReg, opValue + number);
    }

    public void subReg(int dstReg, int opReg1, int opReg2) {
        int opValue1 = registers.getValue(opReg1);
        int opValue2 = registers.getValue(opReg2);

        registers.load(dstReg, opValue1 - opValue2);
    }

    public void subValue(int dstReg, int opReg, int number) {
        int opValue = registers.getValue(opReg);

        registers.load(dstReg, opValue - number);
    }

    public void execute(Bus databus) {
        InStructionType type = databus.getType();
        int dst = databus.getDst();
        int op1 = databus.getOp1();
        int op2 = databus.getOp2();
        switch (type) {
            case AND:
                and(dst, op1, op2);
                break;
            case OR:
                or(dst, op1, op2);
                break;
            case SUB_REG:
                subReg(dst, op1, op2);
                break;
            case SUB_VALUE:
                subValue(dst, op1, op2);
                break;
            case ADD_REG:
                addReg(dst, op1, op2);
                break;
            case ADD_VALUE:
                addValue(dst, op1, op2);
        }
    }
}
