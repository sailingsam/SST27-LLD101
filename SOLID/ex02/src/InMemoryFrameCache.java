public class InMemoryFrameCache implements FrameCache {
    private Frame lastFrame;
    
    public void store(Frame frame) {
        this.lastFrame = frame;
    }
    
    public boolean hasFrame() {
        return lastFrame != null;
    }
}
