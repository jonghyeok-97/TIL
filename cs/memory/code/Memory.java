package code;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Memory {

    private static final int BASE_ADDRESS = 0;

    private final int totalSize;
    private final Map<String, Integer> typeMap;
    private final Set<Integer> possibleLengths;
    private Stack stack;
    private Heap heap;

    public Memory(int totalSize) {
        this.totalSize = totalSize;
        this.typeMap = new HashMap<>();
        this.possibleLengths = Set.of(1, 2, 4, 8, 16, 32);
    }

    public int init(int stackSize, int heapSize) {
        if (stackSize + heapSize > totalSize) {
            throw new IllegalArgumentException("전체 메모리 오버");
        }
        this.stack = new Stack(totalSize, stackSize);
        this.heap = new Heap(heapSize);

        // 기본 주소
        return BASE_ADDRESS;
    }

    public void setSize(String type, int length) {
        if (typeMap.containsKey(type)) {
            throw new IllegalArgumentException("이미 등록된 타입입니다.");
        }
        if (!possibleLengths.contains(length)) {
            throw new IllegalArgumentException("등록할 수 없는 사이즈입니다.");
        }
        typeMap.put(type, length);
    }

    public int malloc(String type, int count) {
        if (!typeMap.containsKey(type)) {
            throw new IllegalArgumentException("등록되지 않은 타입입니다.");
        }
        int length = typeMap.get(type);
        // 8바이트로 패딩
        if (length < 8) {
            length = 8;
        }
        int byteSize = length * count;

        Pointer pointer = heap.save(byteSize);
        return stack.save(pointer);
    }

    public void free(int stackPointer) {
        Pointer pointer = stack.free(stackPointer);
        heap.free(pointer);
    }

    public void call(String name, int paramCount) {
        if (!(0 <= paramCount && paramCount <= 10)) {
            throw new IllegalArgumentException("잘못된 paramCount입니다.");
        }
        if (name.length() > 8) {
            throw new IllegalArgumentException("name이 너무 깁니다.");
        }
        stack.call(name, paramCount);
    }

    public void returnFrom(String name) {
        stack.returnFrom(name);
    }

    public int[] usage() {
        int stackSize = stack.getSize();
        int stackInUseSize = stack.getInUseSize();
        int stackRemainSize = stack.getRemainSize();

        int heapSize = heap.getSize();
        int heapInUseSize = heap.getInUseSize();
        int heapRemainSize = heap.getRemainSize();

        return new int[]{stackSize, stackInUseSize, stackRemainSize, heapSize, heapInUseSize, heapRemainSize};
    }

    public String callStack() {
        return stack.getCalls();
    }
}
