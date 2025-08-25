public class SimpleDecoder implements Decoder {
    public Frame decode(byte[] fileBytes) {
        // pretend decoding - same as original logic
        return new Frame(fileBytes);
    }
}
