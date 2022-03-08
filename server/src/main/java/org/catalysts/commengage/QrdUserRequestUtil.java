package org.catalysts.commengage;

public class QrdUserRequestUtil {
    public static final int QRD_PAGE_LIMIT = 1000;

    public static int getRequestOffset(int totalScans, int offsetProcessed) {
        int remaining = totalScans - offsetProcessed;
        if (remaining > QRD_PAGE_LIMIT)
            return remaining - QRD_PAGE_LIMIT;

        return 0;
    }

    public static int getRequestLimit(int totalScans, int offsetProcessed) {
        int remaining = totalScans - offsetProcessed;
        return Math.min(remaining, QRD_PAGE_LIMIT);
    }
}
