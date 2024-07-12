package cpu.unit;

import java.util.Map;

public class IR {

    private final Map<String, InStructionType> inStructionType;

    public IR() {
        this.inStructionType = Map.ofEntries(
                Map.entry("0001", InStructionType.LOAD_REG),
                Map.entry("0010", InStructionType.LOAD_VALUE),
                Map.entry("0011", InStructionType.STORE_REG),
                Map.entry("0100", InStructionType.STORE_VALUE),
                Map.entry("0101", InStructionType.AND),
                Map.entry("0110", InStructionType.OR),
                Map.entry("0111", InStructionType.ADD_REG),
                Map.entry("1000", InStructionType.ADD_VALUE),
                Map.entry("1001", InStructionType.SUB_REG),
                Map.entry("1010", InStructionType.SUB_VALUE),
                Map.entry("1011", InStructionType.MOV)
        );
    }

    public InStructionType getCommand(String program) {
        String command = program.substring(0, 4);
        return inStructionType.get(command);
    }

    public enum InStructionType {
        LOAD_REG, LOAD_VALUE, STORE_REG, STORE_VALUE, AND, OR, ADD_REG, ADD_VALUE, SUB_REG, SUB_VALUE, MOV
    }

}
