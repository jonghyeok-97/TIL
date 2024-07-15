package code.cpu;

import code.cpu.unit.IR;
import code.cpu.unit.IR.InStructionType;
import code.cpu.unit.Bus;
import code.cpu.unit.Decoder;
import java.util.Optional;
import code.memory.RAM;

public class ControlUnit {

    private final IR ir;
    private final Decoder decoder;
    private final Registers registers;

    public ControlUnit(Registers registers) {
        this.ir = new IR();
        this.decoder = new Decoder();
        this.registers = registers;
    }

    public Optional<Bus> process(String program) {
        assert program.length() == 16;
        InStructionType type = ir.getCommand(program);

        Bus dataBus = decoder.decode(type, program);

        int op1 = dataBus.getOp1();
        int op2 = dataBus.getOp2();
        int dst = dataBus.getDst();

        switch (type) {
            case MOV:
                move(dst, op1);
                return Optional.empty();
            case LOAD_REG:
                loadReg(dst, op1, op2);
                return Optional.empty();
            case LOAD_VALUE:
                loadValue(dst, op1, op2);
                return Optional.empty();
            case STORE_REG:
                storeReg(dst, op1, op2);
                return Optional.empty();
            case STORE_VALUE:
                storeValue(dst, op1, op2);
                return Optional.empty();
        }
        dataBus.addType(type);
        return Optional.of(dataBus);
    }

    private void storeValue(int dstReg, int opReg, int opValue) {
        int regValue = registers.getValue(opReg);
        int address = regValue + opValue;
        int dstValue = registers.getValue(dstReg);

        RAM.ADDRESS.put(address, Integer.toBinaryString(dstValue));
    }

    private void storeReg(int dstReg, int opReg1, int opReg2) {
        int value1 = registers.getValue(opReg1);
        int value2 = registers.getValue(opReg2);
        int address = value1 + value2;
        int dstRegValue = registers.getValue(dstReg);

        RAM.ADDRESS.put(address, Integer.toBinaryString(dstRegValue));
    }

    private void loadReg(int dstReg, int opReg1, int opReg2) {
        int regValue1 = registers.getValue(opReg1);
        int regValue2 = registers.getValue(opReg2);
        String value = RAM.ADDRESS.get(regValue1 + regValue2);

        registers.load(dstReg, Integer.parseInt(value, 2));
    }

    private void loadValue(int dstReg, int opReg, int opValue) {
        int regValue = registers.getValue(opReg);
        String value = RAM.ADDRESS.get(regValue + opValue);

        registers.load(dstReg, Integer.parseInt(value, 2));
    }

    private void move(int dstReg, int opValue) {
        registers.load(dstReg, opValue);
    }
}
