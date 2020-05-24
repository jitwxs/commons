package com.github.jitwxs.commons.core.util.datatype;

import org.junit.Test;

public class BitUtilsTest {
    @Test
    public void testSetBitValue() {
        System.out.println(BitUtils.setBitValue(0, 10, 1));
    }

    @Test
    public void testGetBitValue() {
        System.out.println(BitUtils.getBitValue(1024, 10));
    }
}