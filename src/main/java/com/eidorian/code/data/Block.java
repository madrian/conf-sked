package com.eidorian.code.data;

import com.eidorian.code.constraint.Constraint;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A <code>Block</code> consists of a list of <code>Slots</code>.
 * <p>
 *     The slots are scheduled following the constraint set for
 *     this block.
 * </p>
 */
public class Block {
    private Date startTime;
    private Date endTime;
    private List<Slot> slots = new ArrayList<Slot>();
    private Constraint constraint;

    /** Adds a slot at the end of the <code>slots</code> queue.
     *
     * @param slot
     * @return <code>true</code> if added successfully without conflict in time,
     * <code>false</code> otherwise.
     */
    public boolean add(Slot slot) {
        if(!slot.getEndTime().after(endTime)) {
            slots.add(slot);
            //slot.getTalk().setScheduled(true);
            return true;
        }
        return false;
    }

    /**
     * Inserts a slot at the beginning of the block.
     *
     * @param slot
     * @return
     */
    public boolean insertFirst(Slot slot) {
        boolean result;
        if(slots.isEmpty()) {
            result = add(slot);
        } else {
            //TODO support insertion and reschedule the slots
            throw new IllegalStateException("Block slots are not empty.");
        }
        return result;
    }

    /**
     * Applies the constraint set for this block.
     *
     * <p>
     *     This calls <code>constraint.apply()</code>.
     * </p>
     *
     * @param pool
     */
    public void schedule(List<Talk> pool) {
        constraint.apply(pool, this);
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

    public List<Slot> getSlots() {
        return slots;
    }

    public void setSlots(List<Slot> slots) {
        this.slots = slots;
    }

    public Constraint getConstraint() {
        return constraint;
    }

    public void setConstraint(Constraint constraint) {
        this.constraint = constraint;
    }
}
