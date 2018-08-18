package com.eidorian.code;

import com.eidorian.code.data.Conference;
import com.eidorian.code.processor.Builder;
import com.eidorian.code.data.Slot;
import com.eidorian.code.data.Talk;
import com.eidorian.code.processor.Reader;
import com.eidorian.code.processor.Scheduler;
import com.eidorian.code.util.SchedulerProperties;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;

public class SchedulerTest {
    Scheduler scheduler;
    Conference conference;
    List<Talk> pool;

    @Before
    public void setUp() {
        pool = Reader.readJson("talks.json");
        SchedulerProperties properties = new SchedulerProperties(
                "scheduler.properties");
        conference = new Builder(properties).build();
        scheduler = new Scheduler(conference, pool);
    }

    @After
    public void tearDown() {
        scheduler = null;
        pool = null;
        conference = null;
    }

    @Test
    public void testScheduleAllTalksScheduled() {
        scheduler.schedule();
        for(Talk t : pool) {
            Assert.assertTrue("Unscheduled talk " + t.getTitle(), conference.isScheduled(t));
        }
    }

    @Test
    public void testScheduleNoDuplicates() {
        scheduler.schedule();
        HashSet<String> titles = new HashSet<String>();
        for(Slot s : conference.getAllSlots()) {
            String title = s.getTalk().getTitle();
            if(title != null) {
                Assert.assertTrue("Duplicate title: " + title, titles.add(title));
            }
        }
    }

    @Test
    public void testScheduleSlotsWithSameStartTimes() {
        scheduler.schedule();
        HashSet<String> times = new HashSet<String>();
        for(Slot s : conference.getAllSlots()) {
            String startTime = Scheduler.DATETIME_FORMAT.format(s.getStartTime());
            if(startTime != null) {
                Assert.assertTrue("Same start times : " + startTime, times.add(startTime));
            }
        }
    }
}
