package com.eidorian.code;

import com.eidorian.code.data.Conference;
import com.eidorian.code.processor.Builder;
import com.eidorian.code.util.SchedulerProperties;
import com.eidorian.code.data.Block;
import com.eidorian.code.processor.Scheduler;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Properties;

/**
 * This tests the building of blocks from property file.
 */
public class BuilderLoadFromPropertiesTest {
    Properties properties;
    List<Block> blocks;

    @Before
    public void setUp() {
        SchedulerProperties properties = new SchedulerProperties(
                "scheduler.properties");
        Conference conference = new Builder(properties).build();
        blocks = conference.getAllBlocks();
    }

    @After
    public void tearDown() {
        properties = null;
        blocks = null;
    }

    @Test
    public void testBuilderCreateBlocks() {
        Assert.assertEquals(8, blocks.size());
    }

    @Test
    public void testBuilderCreateBlocksB1StartTime() {
        testBuilderCreateBlocksBlockStartTime(0, "09:00");
    }

    @Test
    public void testBuilderCreateBlocksB2StartTime() {
        testBuilderCreateBlocksBlockStartTime(1, "12:30");
    }

    @Test
    public void testBuilderCreateBlocksB3StartTime() {
        testBuilderCreateBlocksBlockStartTime(2, "15:00");
    }

    @Test
    public void testBuilderCreateBlocksB4StartTime() {
        testBuilderCreateBlocksBlockStartTime(3, "17:00");
    }

    @Test
    public void testBuilderCreateBlocksBlock1EndTime() {
        testBuilderCreateBlocksBlockEndTime(0, "12:30");
    }

    @Test
    public void testBuilderCreateBlocksB2EndTime() {
        testBuilderCreateBlocksBlockEndTime(1, "15:00");
    }

    @Test
    public void testBuilderCreateBlocksB3EndTime() {
        testBuilderCreateBlocksBlockEndTime(2, "17:00");
    }

    @Test
    public void testBuilderCreateBlocksB4EndTime() {
        testBuilderCreateBlocksBlockEndTime(3, "17:30");
    }

    @Test
    public void testBuilderCreateBlocksConstraintLoaded() {
        for(Block b : blocks) {
            Assert.assertNotNull(b.getConstraint());
        }
    }

    private void testBuilderCreateBlocksBlockStartTime(int index, String time) {
        Block b = blocks.get(index);
        Assert.assertEquals(time, Scheduler.TIME_FORMAT
                .format(b.getStartTime()));
    }

    private void testBuilderCreateBlocksBlockEndTime(int index, String time) {
        Block b = blocks.get(index);
        Assert.assertEquals(time, Scheduler.TIME_FORMAT
                .format(b.getEndTime()));
    }
}
