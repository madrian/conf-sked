package com.eidorian.code.util;

import com.eidorian.code.processor.Builder;
import com.eidorian.code.data.Talk;
import com.eidorian.code.data.TalkType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Common utility methods.
 */
public class CommonUtil {
    private CommonUtil() {}

    public static Date addMinutes(Date time, int minutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        cal.add(Calendar.MINUTE, minutes);
        return cal.getTime();
    }

    public static Date subtractMinutes(Date time, int minutes) {
        return addMinutes(time, 0 - minutes);
    }

    public static Talk searchTalk(List<Talk> pool, TalkType type) {
        Talk talk = null;
        if(TalkType.LUNCH.equals(type)) return Builder.createLunch();
        if(TalkType.TEA.equals(type)) return Builder.createTea();
        for(Talk t : pool) {
            if(t.getType().equals(type)) {
                talk = t;
            }
        }
        return talk;
    }

    public static Date parseDate(String date, SimpleDateFormat format) {
        Date d = null;
        try {
            d = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return d;
    }
}
