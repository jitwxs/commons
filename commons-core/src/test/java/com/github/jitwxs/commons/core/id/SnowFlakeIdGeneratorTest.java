package com.github.jitwxs.commons.core.id;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SnowFlakeIdGeneratorTest {
    SnowFlakeIdGenerator idGenerator;
    SnowFlakeIdGenerator idGenerator1;
    @Before
    public void beforeInit() {
        idGenerator = new SnowFlakeIdGenerator(0, 0);
        idGenerator1 = new SnowFlakeIdGenerator(0, 1);
    }

    @Test
    public void nextId() {
        System.out.println(idGenerator.nextId());
        System.out.println(idGenerator1.nextId());
    }
}