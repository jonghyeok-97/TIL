package code;

import code.Pointer;
import java.util.LinkedList;
import java.util.List;

public class Stack {

    // 1024바이트
    private int totalSize;
    private final List<Pointer> pointers;

    private int stackPointer;
    private final List<String> callStack;

    public Stack(int totalSize, int size) {
        this.totalSize = size;
        this.pointers = new LinkedList<>();
        this.callStack = new LinkedList<>();
    }

    // stack에 저장된 물리 주소를 반환
    public void save(Pointer pointer) {
        // 포인터 메모리는 4바이트
        totalSize -= 4;
        if (totalSize < 0) {
            throw new IllegalArgumentException("스택 오버 플로우");
        }
        pointers.add(pointer);
    }

    public void free(Pointer pointer) {
        pointers.remove(pointer);
        totalSize += 4;
    }

    public void call(String name, int paramCount) {
        for (int i = 0; i < paramCount; i++) {
            callStack.add(name);
            stackPointer++;
        }
    }

    public void returnFrom(String name) {
        String call;
        try {
            call = callStack.get(callStack.size() - 1);
            // callStack 에 함수가 없으면 아무런 동작하지 않는다.
        } catch (IndexOutOfBoundsException e) {
            return;
        }
        if (!name.equals(call)) {
            throw new IllegalArgumentException("가장 늦게 호출한 함수가 아닙니다.");
        }
        callStack.remove(call);
    }
}
