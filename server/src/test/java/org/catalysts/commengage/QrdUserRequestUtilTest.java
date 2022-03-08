package org.catalysts.commengage;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class QrdUserRequestUtilTest {
    @Test
    public void getRequestOffset() {
        assertEquals(3500, QrdUserRequestUtil.getRequestOffset(4500, 0));
        assertEquals(1500, QrdUserRequestUtil.getRequestOffset(4500, 2000));
        assertEquals(1, QrdUserRequestUtil.getRequestOffset(4500, 3499));
        assertEquals(0, QrdUserRequestUtil.getRequestOffset(4500, 3500));
        assertEquals(0, QrdUserRequestUtil.getRequestOffset(4500, 4000));
        assertEquals(0, QrdUserRequestUtil.getRequestOffset(4500, 4500));
    }

    @Test
    public void getRequestLimit() {
        assertEquals(1000, QrdUserRequestUtil.getRequestLimit(4500, 0));
        assertEquals(1000, QrdUserRequestUtil.getRequestLimit(4500, 2000));
        assertEquals(1000, QrdUserRequestUtil.getRequestLimit(4500, 3499));
        assertEquals(1000, QrdUserRequestUtil.getRequestLimit(4500, 3500));
        assertEquals(500, QrdUserRequestUtil.getRequestLimit(4500, 4000));
        assertEquals(0, QrdUserRequestUtil.getRequestLimit(4500, 4500));
    }
}
