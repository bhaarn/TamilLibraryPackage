package com.padhuga.tamillibrary.models;

public class SearchRetriever {
    private final String title;
    private final int position;
    private final int groupPosition;
    private final int childPosition;

    public SearchRetriever(String title, int position, int groupPosition, int childPosition) {
        this.title = title;
        this.position = position;
        this.groupPosition = groupPosition;
        this.childPosition = childPosition;
    }

    public String getTitle() {
        return title;
    }

    public int getPosition() {
        return position;
    }

    public int getGroupPosition() {
        return groupPosition;
    }

    public int getChildPosition() {
        return childPosition;
    }
}
