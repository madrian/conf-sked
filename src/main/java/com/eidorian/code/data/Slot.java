package com.eidorian.code.data;

import com.eidorian.code.processor.Scheduler;
import com.eidorian.code.util.CommonUtil;

import java.util.Date;

/**
 * A <code>Slot</code> represents a scheduled talk.
 * <p>
 *     A <code>talk</code> is scheduled from <code>startTime</code> to <code>endTime</code>.
 * </p>
 */
public class Slot {
    private Date startTime;
    private Date endTime;
    // the talk reserved on this slot
    private Talk talk;
    // a parallel talk scheduled on this same slot
    private Talk parallelTalk;

    public Slot() {};

    public Slot(Date startTime, Talk talk) {
        this.startTime = startTime;
        this.endTime = CommonUtil.addMinutes(startTime, talk.getType().getMinutes());
        this.talk = talk;
    }

    @Override
    public String toString() {
        return Scheduler.TIME_FORMAT.format(startTime) + " " + talk.getTitle();
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Talk getTalk() {
        return talk;
    }

    public void setTalk(Talk talk) {
        this.talk = talk;
    }

    public Talk getParallelTalk() {
        return parallelTalk;
    }

    public void setParallelTalk(Talk parallelTalk) {
        this.parallelTalk = parallelTalk;
    }
}
