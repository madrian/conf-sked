package com.eidorian.code.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * A <code>Talk</code> data type based from the JSON input file.
 */
public class Talk {
    @JsonProperty("type")
    TalkType type;
    @JsonProperty("description")
    String description;
    @JsonProperty("tags")
    List<String> tags;
    @JsonProperty("title")
    String title;

    //boolean isScheduled;

    public Talk() {}

    public Talk(TalkType type) {
        this.type = type;
    }

    public Talk(TalkType type, String title, String description) {
        this.type = type;
        this.title = title;
        this.description = description;
    }

    public String toString() {
        return "[type=" + type + ",title=" + title + ",description=" + description +
            ",tags=" + tags + "]";
    }

    public TalkType getType() {
        return type;
    }

    public void setType(TalkType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

//    public boolean isScheduled() {
//        return isScheduled;
//    }

//    public void setScheduled(boolean scheduled) {
//        isScheduled = scheduled;
//    }
}
