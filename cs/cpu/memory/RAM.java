package memory;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class RAM {

    public static final Map<Integer, String> ADDRESS = initialize();

    private static Map<Integer, String> initialize() {
        Map<Integer, String> address = new HashMap<>();
        IntStream.rangeClosed(0, 1000)
                .forEach(i -> address.put(i, "0"));
        // MOV R4, 0x00A0
        address.put(500, "1011100010100000");
        // MOV R5, 0x0002
        address.put(516, "1011101000000010");
        // LOAD R1, R4, R5
        address.put(532, "0001001100000101");
        // ADD R2, R1, #4
        address.put(548, "1000010001100100");
        // SUB R3, R1, R2
        address.put(564, "1001011001000010");
        // STORE R3, R4, #4
        address.put(580, "0100011100100100");
        return address;
    }
}
