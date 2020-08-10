package com.jebytek.server.dto;

public class IdxDto {
    private String id;
    private int curIdx;
    private int newIdx;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCurIdx() {
        return curIdx;
    }

    public void setCurIdx(int curIdx) {
        this.curIdx = curIdx;
    }

    public int getNewIdx() {
        return newIdx;
    }

    public void setNewIdx(int newIdx) {
        this.newIdx = newIdx;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("IdxDto{");
        sb.append("id='").append(id).append('\'');
        sb.append(", curIdx=").append(curIdx);
        sb.append(", newIdx=").append(newIdx);
        sb.append('}');
        return sb.toString();
    }
}
