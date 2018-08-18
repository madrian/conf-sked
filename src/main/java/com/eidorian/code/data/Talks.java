package com.eidorian.code.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Wrapper class for the list of talks.
 */
public class Talks {
    @JsonProperty("talks")
    List<Talk> talks;

    public List<Talk> getTalks() {
        return talks;
    }

    public void setTalks(List<Talk> talks) {
        this.talks = talks;
    }
}
