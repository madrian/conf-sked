package com.eidorian.code.constraint;

import com.eidorian.code.data.Block;
import com.eidorian.code.data.Talk;

import java.util.List;

public interface Constraint {

    /**
     * Applies the constraint to <code>block</code> when scheduling the
     * <code>pool</code> of talks.
     *
     * @param pool
     * @param block
     * @return
     */
    boolean apply(List<Talk> pool, Block block);

}
