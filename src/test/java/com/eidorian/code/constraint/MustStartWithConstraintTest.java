package com.eidorian.code.constraint;

import com.eidorian.code.data.Conference;
import com.eidorian.code.processor.Builder;
import com.eidorian.code.util.SchedulerProperties;
import com.eidorian.code.data.Slot;
import com.eidorian.code.data.Talk;
import com.eidorian.code.data.TalkType;
import com.eidorian.code.processor.Reader;
import com.eidorian.code.processor.Scheduler;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * These tests are specific to the default constraint implemented.
 *
 * Disable this test if you are using a different constraint.
 */
public class MustStartWithConstraintTest {
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
    public void testConstraintKeynoteAt9am() {
        testConstraintTalkAtTime(TalkType.KEYNOTE, "09:00");
    }

    @Test
    public void testConstraintLunchAt1230pm() {
        testConstraintTalkAtTime(TalkType.LUNCH, "12:30");
    }

    @Test
    public void testConstraintTeaAt3pm() {
        testConstraintTalkAtTime(TalkType.TEA, "15:00");
    }

    @Test
    public void testConstraintClosingAt5pm() {
        testConstraintTalkAtTime(TalkType.CLOSING, "17:00");
    }

    private void testConstraintTalkAtTime(TalkType type, String startTime) {
        scheduler.schedule();
        for(Slot s : conference.getAllSlots()) {
            Talk t = s.getTalk();
            String slotTime = Scheduler.TIME_FORMAT.format(s.getStartTime());
            if(t.getType().equals(type))
                Assert.assertTrue(type + " talk not scheduled at " + startTime
                    + ": " + t.getTitle(), startTime.equals(slotTime));
        }
    }
}
