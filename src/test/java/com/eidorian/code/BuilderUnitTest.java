package com.eidorian.code;

import com.eidorian.code.data.Conference;
import com.eidorian.code.data.Day;
import com.eidorian.code.processor.Builder;
import com.eidorian.code.util.SchedulerProperties;
import com.eidorian.code.data.TalkType;
import com.eidorian.code.processor.Scheduler;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Properties;

public class BuilderUnitTest {
    SchedulerProperties properties;

    @Before
    public void setUp() {
        properties = new SchedulerProperties(new Properties());
    }

    @After
    public void tearDown() {
        properties = null;
    }

    @Test
    public void testBuilderCreateConference() {
        String startDate = "2018-07-14";
        int count = 10;
        properties.setConferenceStartDate(startDate);
        properties.setConferenceDayCount(count);
        Conference c = new Builder(properties).build();
        Assert.assertEquals(count, c.getDays().size());
        String date = Scheduler.DATE_FORMAT.format(c.getStartDate());
        Assert.assertEquals(startDate, date);
    }

    @Test
    public void testBuilderCreateDays() {
        String[] daysArray = {"2018-05-10","2018-05-11", "2018-05-12"};
        int count = daysArray.length;
        properties.setConferenceStartDate(daysArray[0]);
        properties.setConferenceDayCount(count);
        Conference c = new Builder(properties).build();
        List<Day> days = c.getDays();
        Assert.assertEquals(count, days.size());
        for(int i = 0; i < count; i++) {
            String actual = Scheduler.DATE_FORMAT.format(
                    days.get(i).getDate());
            String expected = daysArray[i];
            Assert.assertEquals(expected, actual);
        }
    }

    @Test
    public void testBuilderCreateBlocks() {
        int count = 6;
        int days = 3;
        properties.setConferenceStartDate("2018-06-15");
        properties.setConferenceDayCount(days);
        properties.setBlockCount(count);
        for(int i = 0; i < 6; i++) {
            properties.setBlockStartTime(i, "09:00");
            properties.setBlockEndTime(i, "10:00");
            properties.setBlockStartTalkType(i, TalkType.KEYNOTE);
        }
        Conference c = new Builder(properties).build();
        Assert.assertEquals(count * days, c.getAllBlocks().size());
    }
}
