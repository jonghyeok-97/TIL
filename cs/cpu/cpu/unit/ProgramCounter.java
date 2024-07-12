package cpu.unit;

import memory.RAM;

public class ProgramCounter {
    private static final int BIT_ARCHITECTURE = 16;

    private int memoryAddress;

    // 메모리는 구현하지 않았기 때문에 주소를 명령어가 있는 500으로 초기화
    public ProgramCounter() {
        this.memoryAddress = 500;
    }

    public void reset() {
        this.memoryAddress = 0;
    }

    public String getProgramCommand() {
        String programCommand = RAM.ADDRESS.get(memoryAddress);
        // 명령어 없음
        if (programCommand == null) {
            return "";
        }
        // 16비트
        memoryAddress += BIT_ARCHITECTURE;
        return programCommand;
    }
}
