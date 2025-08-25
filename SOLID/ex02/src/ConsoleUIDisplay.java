public class ConsoleUIDisplay implements UIDisplay {
    public void showPlayback(int bytesLength) {
        // Keep original Unicode character to maintain same output
        System.out.println("\u25B6 Playing " + bytesLength + " bytes");
    }
    
    public void showCacheStatus(boolean isCached) {
        System.out.println("Cached last frame? " + isCached);
    }
}
