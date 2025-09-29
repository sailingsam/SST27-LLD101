/**
 * Enum representing the type of entry/exit gate
 */
public enum EntryExitType {
    ENTRY("Entry Gate"),
    EXIT("Exit Gate"),
    BOTH("Entry/Exit Gate");

    private final String displayName;

    EntryExitType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
