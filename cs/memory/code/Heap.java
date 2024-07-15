package code;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Heap {

    private final boolean[] memory;
    private final int size;
    private final Map<Integer, Pointer> mallocMemory;
    private final List<Pointer> mallocmemory = new LinkedList<>();
    private int address;

    public Heap(int size) {
        this.memory = new boolean[size];
        this.size = size;
        this.mallocMemory = new HashMap<>();
    }

    public Pointer save(int byteSize) {
        int heapAddress = -1;
        for (int i = 0; i < memory.length - byteSize; i++) {
            boolean canAllocate = true;
            // 메모리가 없다면,
            if (!memory[i]) {
                // 메모리에 bytesize 만큼 추가할수 있는지 확인
                for (int j = i; j < i + byteSize; j++) {
                    // bytesize만큼 추가할 수 없다면,
                    if (memory[j]) {
                        canAllocate = false;
                        // 추가할수 없는 다음부분부터 반복문 찾도록 진행.
                        i = j + 1;
                        break;
                    }
                }
            }
            if (canAllocate) {
                // 주소를 찾으면 반환값으로 저장
                heapAddress = i;
                // 해당 주소부터, bytesize 만큼 메모리 할당.
                malloc(i, byteSize);
            }
        }
        if (heapAddress == -1) {
            throw new IllegalArgumentException("Heap에 메모리 할당 불가");
        }
        return new Pointer(heapAddress, heapAddress + byteSize);
    }

    public void free(Pointer pointer) {
        int start = pointer.getAddress();
        int offset = pointer.getOffset();
        for (int i = start; i < offset; i++) {
            memory[i] = false;
        }
    }

    private void malloc(int baseAddress, int byteSize) {
        for (int i = baseAddress; i < baseAddress + byteSize; i++) {
            memory[i] = true;
        }
    }
}