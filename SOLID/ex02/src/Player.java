public class Player {
    private final Decoder decoder;
    private final UIDisplay uiDisplay;
    private final FrameCache frameCache;

    public Player(Decoder decoder, UIDisplay uiDisplay, FrameCache frameCache) {
        this.decoder = decoder;
        this.uiDisplay = uiDisplay;
        this.frameCache = frameCache;
    }

    public void play(byte[] fileBytes) {
        // decode using injected decoder
        Frame frame = decoder.decode(fileBytes);
        
        // cache the frame
        frameCache.store(frame);
        
        // display UI using injected display
        uiDisplay.showPlayback(fileBytes.length);
        uiDisplay.showCacheStatus(frameCache.hasFrame());
    }
}