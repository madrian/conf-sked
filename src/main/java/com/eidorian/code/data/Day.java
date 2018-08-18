package com.eidorian.code.data;

import java.util.Date;
import java.util.List;

/**
 * A <code>Day</code> consists of a list of <code>Block</code>s.
 */
public class Day {
    private Date date;
    private List<Block> blocks;

    public Day(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Block> getBlocks() {
        return blocks;
    }

    public void setBlocks(List<Block> blocks) {
        this.blocks = blocks;
    }
}
