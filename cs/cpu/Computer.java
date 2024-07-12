import cpu.CPU;
import java.util.Arrays;
import java.util.stream.IntStream;

public class Computer {

    public static void main(String[] args) {
        CPU cpu = new CPU();

        IntStream.range(0, 6)
                .forEach(i -> {
                    String programCommand = cpu.fetch();
                    cpu.execute(programCommand);
                });
        System.out.println("예상 동작 결과 : " + Arrays.toString(cpu.dump()));
        cpu.reset();
        System.out.println("reset결과 : " + Arrays.toString(cpu.dump()));
    }
}
