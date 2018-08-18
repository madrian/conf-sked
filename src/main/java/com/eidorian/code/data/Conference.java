package com.eidorian.code.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A <code>Conference</code> consists of a list of <code>Day</code>s.
 */
public class Conference {
    List<Day> days;
    Date startDate;

    public Conference(Date startDate) {
        this.startDate = startDate;
    }

    public List<Day> getDays() {
        return days;
    }

    public void setDays(List<Day> days) {
        this.days = days;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public List<Block> getAllBlocks() {
        List<Block> all = new ArrayList<Block>();
        for(Day d : days) {
            all.addAll(d.getBlocks());
        }
        return all;
    }

    public List<Slot> getAllSlots() {
        List<Slot> all = new ArrayList<Slot>();
        for(Block b : getAllBlocks()) {
            all.addAll(b.getSlots());
        }
        return all;
    }

    public boolean isScheduled(Talk talk) {
        List<Slot> slots = getAllSlots();
        for(Slot s : slots) {
            if(s.getTalk().equals(talk) || (s.getParallelTalk() != null && s.getParallelTalk().equals(talk)))
                return true;
        }
        return false;
    }
}
