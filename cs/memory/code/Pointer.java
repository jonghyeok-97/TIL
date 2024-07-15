package code;

public class Pointer {

    private final int address;
    private final int offset;


    public Pointer(int address, int offset) {
        this.address = address;
        this.offset = offset;
    }

    public int getAddress() {
        return address;
    }

    public int getOffset() {
        return offset;
    }

    //    public void save(int heapAddress) {
//        stackPointers.add(heapAddress);
//    }
}
