package cpu;

import cpu.unit.ProgramCounter;
import java.util.Arrays;

public class Registers {

    private final ProgramCounter pc;
    private final int[] registers;

    public Registers() {
        this.pc = new ProgramCounter();
        registers = new int[7];
    }

    public void reset() {
        pc.reset();
        Arrays.fill(registers, 0);
    }

    public void load(int dstRegIndex, int value) {
        registers[dstRegIndex] = value;
    }

    public int getValue(int regIndex) {
        return registers[regIndex];
    }

    public String getProgramCommand() {
        return pc.getProgramCommand();
    }

    // 레지스터는 1~7부터 있다고 가정하기 때문에 인덱스 +1 만큼 이동
    public int[] dump() {
        return Arrays.copyOfRange(registers, 1, 8);
    }
}
