package com.eidorian.code.util;

import com.eidorian.code.data.TalkType;
import com.eidorian.code.processor.Reader;
import com.eidorian.code.processor.Scheduler;

import java.util.Date;
import java.util.Properties;

public class SchedulerProperties {
    public static final String TALKS_JSON = "conference.talks.json";
    public static final String CONF_START_DATE = "conference.start.date";
    public static final String CONF_DAYS = "conference.days";
    public static final String BLOCK_COUNT = "block.count";
    public static final String BLOCK_START_TIME = "block.start.time";
    public static final String BLOCK_END_TIME = "block.end.time";
    public static final String BLOCK_START_TALK_TYPE = "block.start.talk.type";

    private Properties properties;

    public SchedulerProperties(String propertyFile) {
        this.properties = Reader.readProperties(propertyFile);
    }

    public SchedulerProperties(Properties properties) {
        this.properties = properties;
    }

    public String getTalksJson() {
        return properties.getProperty(TALKS_JSON);
    }

    public void setConferenceStartDate(String date) {
        properties.setProperty(CONF_START_DATE, date);
    }

    public Date getConferenceStartDate() {
        String startDate = properties.getProperty(CONF_START_DATE);
        return CommonUtil.parseDate(startDate, Scheduler.DATE_FORMAT);
    }

    public void setConferenceDayCount(int count) {
        properties.setProperty(CONF_DAYS, String.valueOf(count));
    }

    public int getConferenceDayCount() {
        String dayCount = properties.getProperty(CONF_DAYS);
        return Integer.parseInt(dayCount);
    }

    public void setBlockCount(int count) {
        properties.setProperty(BLOCK_COUNT, String.valueOf(count));
    }

    public int getBlockCount() {
        String s = properties.getProperty(BLOCK_COUNT);
        if(s == null) return 0;
        return Integer.parseInt(s);
    }

    public String getBlockStartTime(int index) {
        return get(BLOCK_START_TIME, index);
    }

    public String getBlockEndTime(int index) {
        return get(BLOCK_END_TIME, index);
    }

    public TalkType getBlockStartTalkType(int index) {
        return TalkType.valueOf(get(BLOCK_START_TALK_TYPE, index));
    }

    public void setBlockStartTime(int index, String value) {
        set(BLOCK_START_TIME, value, index);
    }

    public void setBlockEndTime(int index, String value) {
        set(BLOCK_END_TIME, value, index);
    }

    public void setBlockStartTalkType(int index, TalkType type) {
        set(BLOCK_START_TALK_TYPE, type.toString(), index);
    }

    public String get(String name, int index) {
        return properties.getProperty(name + "." + index);
    }

    public void set(String name, String value, int index) {
        properties.setProperty(name + "." + index, value);
    }
}
