package com.eidorian.code.constraint;

import com.eidorian.code.data.Block;
import com.eidorian.code.data.Slot;
import com.eidorian.code.data.Talk;
import com.eidorian.code.data.TalkType;
import com.eidorian.code.processor.Builder;
import com.eidorian.code.util.CommonUtil;

import java.util.Date;
import java.util.List;

/**
 * A <code>Constraint</code> implementation where a <code>Block</code> must
 * start with the given <code>TalkType</code>.
 * <p>
 *     The rest of the talks are scheduled in the same order as they are in
 *     the pool and the talks <code>KEYNOTE</code> and
 *     <code>CLOSING</code> are ignored.
 * </p>
 * <p>
 *     A sample use case for this constraint is when a conference day is
 *     split into multiple blocks and the split is done in such a way that
 *     each block must start with a specific talk type.
 *     (See README for details)
 * </p>
 */
public class MustStartWithConstraint implements Constraint {
    TalkType startWithTalkType;

    public MustStartWithConstraint(TalkType talkType) {
        this.startWithTalkType = talkType;
    }

    public boolean apply(List<Talk> pool, Block block) {
        Date start = block.getStartTime();

        Talk talk = CommonUtil.searchTalk(pool, startWithTalkType);
        if(talk == null) {
            talk = Builder.createEmptyTalk(startWithTalkType);
        }
        // start the block with this talk type
        Slot slot = new Slot(start, talk);
        block.insertFirst(slot);
        start = slot.getEndTime();

        // schedule the rest of the talks
        // do not schedule keynote and closing talks
        for(Talk t : pool) {
            if(t.getType().equals(TalkType.KEYNOTE)
                || t.getType().equals(TalkType.CLOSING))
                    continue;
            slot = new Slot(start, t);
            if(block.add(slot)) {
                start = slot.getEndTime();
            }
        }

        return true;
    }

    public TalkType getStartWithTalkType() {
        return startWithTalkType;
    }

    public void setStartWithTalkType(TalkType startWithTalkType) {
        this.startWithTalkType = startWithTalkType;
    }
}
