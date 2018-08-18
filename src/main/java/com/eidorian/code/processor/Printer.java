package com.eidorian.code.processor;

import com.eidorian.code.data.*;

/**
 * Prints the conference schedule.
 */
public class Printer {

    public void write(Conference conference) {
        int n = 1;
        for(Day d : conference.getDays()) {
            System.out.println("Day " + n++);
            write(d);
            System.out.println();
        }
    }

    public void write(Day day) {
        for(Block b : day.getBlocks()) {
            write(b);
        }
    }

    public void write(Block block) {
        for(Slot s : block.getSlots()) {
            String time = Scheduler.TIME_FORMAT.format(s.getStartTime()) + " ";
            String title = s.getTalk().getTitle() == null ? "" : s.getTalk().getTitle() + " " ;
            String type = s.getTalk().getType().toString();
            System.out.println(time + title + type);
            // check for parallel talk
            if(s.getParallelTalk() != null) {
                Talk pTalk = s.getParallelTalk();
                time = " -or- ";
                title = pTalk.getTitle() == null ? "" : pTalk.getTitle() + " ";
                type  = pTalk.getType().toString();
                System.out.println(time + title + type);
            }
        }
    }
}
