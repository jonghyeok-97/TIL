package code.cpu;

public class CPU {

    private final ControlUnit controlUnit;
    private final Registers registers;
    private final ArithmeticLogicUnit alu;

    public CPU() {
        this.registers = new Registers();
        this.controlUnit = new ControlUnit(registers);
        this.alu = new ArithmeticLogicUnit(registers);
    }

    // 레지스터(PC포함)을 모두 초기화
    public void reset() {
        registers.reset();
    }

    public String fetch() {
        return registers.getProgramCommand();
    }

    public void execute(String programCommand) {
        controlUnit.process(programCommand)
                        .ifPresent(alu::execute);
    }

    public int[] dump() {
        return registers.dump();
    }
}
