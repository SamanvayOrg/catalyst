package org.catalysts.commengage.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class GeolocationUtilTest {
    @Test
    public void round() {
        double rounded = GeolocationUtil.round("20.17300000000");
        assertEquals(20.173, rounded);
        assertNotEquals(20.17, rounded);
    }
}
