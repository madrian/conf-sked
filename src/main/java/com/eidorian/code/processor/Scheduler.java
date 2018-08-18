package com.eidorian.code.processor;

import com.eidorian.code.data.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Schedules a <code>Conference</code> from a pool of talks.
 */
public class Scheduler {
    public static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm");
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat DATETIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    private Conference conference;
    private List<Talk> pool;

    public Scheduler(Conference conference, List<Talk> pool) {
        this.conference = conference;
        this.pool = pool;
    }

    /**
     * Schedules each block in the conference and calls
     * <code>
     *     block.schedule()
     * </code>
     */
    public void schedule() {
        List<Day> days = conference.getDays();
        for(Day d : days) {
            for(Block b: d.getBlocks()) {
                schedule(getUnscheduledTalks(), b);
            }
        }
        scheduleParallel(getUnscheduledTalks(), conference);
    }

    private void schedule(List<Talk> pool, Block block) {
        block.schedule(pool);
    }

    private void scheduleParallel(List<Talk> unscheduledPool, Conference conference) {
        for(Talk t : unscheduledPool) {
            Slot slot = findParallelSlot(t, conference);
            slot.setParallelTalk(t);
        }
    }

    /**
     * Finds a parallel slot for <code>talk</code> with the same <code>TalkType</code>.
     *
     * @param talk
     * @param conference
     * @return
     */
    private Slot findParallelSlot(Talk talk, Conference conference) {
        for(Slot s : conference.getAllSlots()) {
            if(s.getParallelTalk() == null && s.getTalk() != null
                    && s.getTalk().getType().equals(talk.getType())) {
                return s;
            }
        }
        throw new RuntimeException("Could not find a parallel slot for talk " + talk);
    }

    private List<Talk> getUnscheduledTalks() {
        List<Talk> talks = new ArrayList<Talk>();
        for(Talk t : pool) {
            if(!conference.isScheduled(t)) {
                talks.add(t);
            }
        }
        return talks;
    }
}
