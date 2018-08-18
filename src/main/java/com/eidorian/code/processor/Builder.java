package com.eidorian.code.processor;

import com.eidorian.code.constraint.MustStartWithConstraint;
import com.eidorian.code.data.*;
import com.eidorian.code.util.CommonUtil;
import com.eidorian.code.util.SchedulerProperties;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Helper class to initialize the objects.
 */
public class Builder {
    private SchedulerProperties properties;

    public Builder(SchedulerProperties properties) {
        this.properties = properties;
    }

    /**
     * Builds from the properties loaded.
     * @return
     */
    public Conference build() {
        Date startDate = properties.getConferenceStartDate();
        int n = properties.getConferenceDayCount();
        Conference conference = createConference(startDate, n);
        return conference;
    }

    private Conference createConference(Date startDate,
            int numberOfDays) {
        Conference conference = new Conference(startDate);
        conference.setDays(createDays(startDate, numberOfDays));
        return conference;
    }

    private List<Day> createDays(Date date, int count) {
        List<Day> days = new ArrayList<Day>();
        for(int i = 0; i < count; i++) {
            Day d = new Day(date);
            d.setBlocks(createBlocks(date));
            days.add(d);
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(Calendar.DATE, 1);
            date = c.getTime();
        }
        return days;
    }

    private List<Block> createBlocks(Date date) {
        List<Block> blocks = new ArrayList<Block>();

        int count = properties.getBlockCount();
        String blockDate = Scheduler.DATE_FORMAT.format(date);

        for(int i = 0; i < count; i++) {
            Block b = new Block();
            String startTime = properties.getBlockStartTime(i);
            String endTime = properties.getBlockEndTime(i);
            b.setStartTime(CommonUtil.parseDate(blockDate + " " + startTime,
                    Scheduler.DATETIME_FORMAT));
            b.setEndTime(CommonUtil.parseDate(blockDate + " " + endTime,
                    Scheduler.DATETIME_FORMAT));
            TalkType talkType = properties.getBlockStartTalkType(i);
            b.setConstraint(new MustStartWithConstraint(talkType));
            blocks.add(b);
        }

        return blocks;
    }

    public static Talk createLunch() {
        return new Talk(TalkType.LUNCH);
    }

    public static Talk createTea() {
        return new Talk(TalkType.TEA);
    }

    public static Talk createEmptyTalk(TalkType type) {
        return new Talk(type, "(Free slot)", "Not available");
    }
}
