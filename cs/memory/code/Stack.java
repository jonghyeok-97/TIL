package code;

import java.util.LinkedList;
import java.util.List;
import java.util.StringJoiner;

public class Stack {

    // 5000 시작
    private int stackPointer;
    // 1024
    private int size;
    // 5000 - 1024 = 3976
    private int minAddress;

    private final List<StackFrame> stackFrames;

    public Stack(int baseAddress, int stackSize) {
        this.stackPointer = baseAddress;
        this.size = stackSize;
        this.minAddress = baseAddress - stackSize;
        this.stackFrames = new LinkedList<>();
//        this.callStack = new CallStack(totalSize, size);
    }

    // stack에 저장된 물리 주소를 반환
    public int save(Pointer pointer) {
        // 포인터 메모리는 4바이트
        stackPointer -= 4;
        if (stackPointer < minAddress) {
            throw new IllegalArgumentException("스택 오버 플로우");
        }
        StackFrame stackFrame = new StackFrame(pointer, stackPointer - 4);
        stackFrames.add(stackFrame);
        return stackPointer;
    }

    public Pointer free(int stackPointer) {
        // 콜스택에서 맨 마지막 스택프레임 꺼내고
        StackFrame stackFrame = stackFrames.get(stackFrames.size() - 1);
        // 지우고
        stackFrames.remove(stackFrame);
        // 스택포인터 증가시키고
        this.stackPointer = stackPointer + 4;
        // 스택프레임안의 포인터 반환
        return stackFrame.getPointer();
    }

    public void call(String name, int paramCount) {
        // paramCount 만큼 생성된 pointer 개수만큼 stackPointer 감소
        int pointerSize = paramCount * 4;
        stackPointer -= pointerSize;

        if (stackPointer < minAddress) {
            throw new IllegalArgumentException("스택 오버 플로우");
        }
        // name함수를 가지고, stackPointer 위치를 가진 스택프레임을 콜스택에 저장.
        StackFrame stackFrame = new StackFrame(name, stackPointer);
        stackFrames.add(stackFrame);
    }

    public void returnFrom(String name) {
        // call 호출한 경우 없으면 동작 안함.
        boolean isCall = stackFrames.stream()
                .anyMatch(stackFrame -> stackFrame.has(name));
        if (!isCall) {
            return;
        }

        StackFrame lastStackFrame = stackFrames.get(stackFrames.size() - 1);
        // 마지막 call호출의 name가 동일한지.
        if (!lastStackFrame.has(name)) {
            throw new IllegalArgumentException("스택 프레임 제거 불가");
        }
        stackPointer = lastStackFrame.getAddress();
        stackFrames.remove(lastStackFrame);
        // call 이후에 stack영역에 포인터 값 찾기
        for (int i = stackFrames.size() - 1; i >= 0; i--) {
            StackFrame stackFrame = stackFrames.get(i);
            if (stackFrame.hasPointer()) {
                stackFrames.remove(stackFrame);
                stackPointer += 4;
            }
        }
    }

    public int getSize() {
        return size;
    }

    public int getInUseSize() {
        return size + minAddress - stackPointer;
    }

    public int getRemainSize() {
        return stackPointer - minAddress;
    }

    public String getCalls() {
        StringJoiner joiner = new StringJoiner(" -> ");
        stackFrames.stream()
                .filter(stackFrame -> stackFrame.getName() != null)
                .forEach(stackFrame -> joiner.add(stackFrame.getName() + " " + Integer.toHexString(stackFrame.getAddress()).toUpperCase()));

        return joiner.toString();
    }
}
