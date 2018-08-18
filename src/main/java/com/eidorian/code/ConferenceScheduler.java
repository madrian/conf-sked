package com.eidorian.code;

import com.eidorian.code.data.Conference;
import com.eidorian.code.processor.Builder;
import com.eidorian.code.processor.Reader;
import com.eidorian.code.processor.Scheduler;
import com.eidorian.code.util.SchedulerProperties;
import com.eidorian.code.data.Talk;
import com.eidorian.code.processor.Printer;

import java.util.List;

/**
 * Main class that starts the conference scheduler.
 */
public class ConferenceScheduler {

    public static void main(String[] args) {
        String propertyFile;
        if(args != null && args.length > 0) {
            propertyFile = args[0];
        } else {
            propertyFile = "scheduler.properties";
        }
        SchedulerProperties properties = new SchedulerProperties(propertyFile);
        String inputFile = properties.getTalksJson();
        List<Talk> talks = Reader.readJson(inputFile);

        Conference conference = new Builder(properties).build();
        Scheduler scheduler = new Scheduler(conference, talks);
        scheduler.schedule();
        Printer printer = new Printer();
        printer.write(conference);

        return;
    }
}
