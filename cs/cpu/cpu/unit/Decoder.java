package cpu.unit;

import cpu.unit.IR.InStructionType;

public class Decoder {

    public Bus decode(InStructionType type, String program) {
        if (type == InStructionType.MOV) {
            // 목적 레지스터 인덱스
            int dst = Integer.parseInt(program.substring(4, 7), 2);
            int op = Integer.parseInt(program.substring(7), 2);
            return new Bus(dst, op);
        }

        // 목적 레지스터 인덱스
        int dst = Integer.parseInt(program.substring(4, 7), 2);

        // 오퍼랜드 레지스터 인덱스
        int op1 = Integer.parseInt(program.substring(7, 10), 2);

        // 10번째 비트가 1이면  11비트~15비트는 숫자
        if (program.charAt(10) == '1') {
            int op2 = Integer.parseInt(program.substring(11, 16), 2);
            return new Bus(dst, op1, op2);
        }
        // 13비트~15비트는 Register 인덱스
        int op2 = Integer.parseInt(program.substring(13, 16), 2);
        return new Bus(dst, op1, op2);
    }
}
