public class Frame { 
    private byte[] data;  // Made private for better encapsulation
    
    public Frame(byte[] d) { 
        this.data = d; 
    } 
    
    public byte[] getData() {
        return data;
    }
}
