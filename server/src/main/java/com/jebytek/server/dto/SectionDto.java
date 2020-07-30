package com.jebytek.server.dto;


import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

public class SectionDto {

    /**
     * ID
     */
    private String id;

    /**
     * TITLE
     */
    private String title;

    /**
     * COURSE_ID
     */
    private String courseId;

    /**
     * CHAPTER_ID
     */
    private String chapterId;

    /**
     * VIDEO
     */
    private String video;

    /**
     * TIME IN SECS
     */
    private Integer length;

    /**
     * C: CHARGE; F: FREE
     */
    private String charge;

    /**
     * INDEX
     */
    private Integer idx;

    /**
     * CREATE TIME
     */
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date createdAt;

    /**
     * UPDATE TIME
     */
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date updatedAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getChapterId() {
        return chapterId;
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public String getCharge() {
        return charge;
    }

    public void setCharge(String charge) {
        this.charge = charge;
    }

    public Integer getIdx() {
        return idx;
    }

    public void setIdx(Integer idx) {
        this.idx = idx;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", title=").append(title);
        sb.append(", courseId=").append(courseId);
        sb.append(", chapterId=").append(chapterId);
        sb.append(", video=").append(video);
        sb.append(", length=").append(length);
        sb.append(", charge=").append(charge);
        sb.append(", idx=").append(idx);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append("]");
        return sb.toString();
    }

}