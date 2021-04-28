package md.leonis.ystt.oldmodel2;

public enum KnownSections {

    VERS(false),    // 1
    STUP(true),     // 2
    SNDS(true),     // 3
    TILE(true),     // 4
    ZONE(false),    // 5
    PUZ2(true),     // 6
    CHAR(true),     // 7
    CHWP(true),     // 8
    CAUX(true),     // 9
    TNAM(true),     // 10
    TGEN(true),     // 11
    ENDF(true),     // 12
    UNKN(true);     // Unknown section

    private final boolean withSize;

    KnownSections(boolean withSize) {
        this.withSize = withSize;
    }

    public boolean isWithSize() {
        return withSize;
    }
}
