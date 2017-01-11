package com.cpsmi.dto;

/**
 * Created by Misha on 22.11.2016.
 */
public class HintDTO {
    private String text;
    private int pointId;
    private int order;

    public HintDTO(int pointId, String text, int order) {
        this.text = text;
        this.pointId = pointId;
        this.order = order;
    }

    public int getPointId() {
        return pointId;
    }

    public void setPointId(int pointId) {
        this.pointId = pointId;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


}
