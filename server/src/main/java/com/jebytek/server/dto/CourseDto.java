package com.jebytek.server.dto;

import com.alibaba.fastjson.annotation.JSONField;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class CourseDto {

    /**
     * ID
     */
    private String id;

    /**
     * Name
     */
    private String name;

    /**
     * Description
     */
    private String description;

    /**
     * Time (in secs)
     */
    private Integer time;

    /**
     * Price (USD)
     */
    private BigDecimal price;

    /**
     * Thumb
     */
    private String thumb;

    /**
     * Level|ENUM[CourseLevelEnum]: ONE("1", "Easy"), TWO("2", "Medium"), THREE("3", "Hard")
     */
    private String level;

    /**
     * Charge|ENUM[CourseChargeEnum]: CHARGE("C", "Charge"), FREE("F", "Free")
     */
    private String charge;

    /**
     * Status|ENUM[CourseStatusEnum]: PUBLISHED("P", "Published"), DRAFT("D", "Draft")
     */
    private String status;

    /**
     * Enrolled Count
     */
    private Integer enrolled;

    /**
     * Index
     */
    private Integer idx;

    /**
     * Created At
     */
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date createdAt;

    /**
     * Updated At
     */
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date updatedAt;

    private List<CategoryDto> categorys;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getCharge() {
        return charge;
    }

    public void setCharge(String charge) {
        this.charge = charge;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getEnrolled() {
        return enrolled;
    }

    public void setEnrolled(Integer enrolled) {
        this.enrolled = enrolled;
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

    public List<CategoryDto> getCategorys() {
        return categorys;
    }

    public void setCategorys(List<CategoryDto> categorys) {
        this.categorys = categorys;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("CourseDto{");
        sb.append("id='").append(id).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", time=").append(time);
        sb.append(", price=").append(price);
        sb.append(", thumb='").append(thumb).append('\'');
        sb.append(", level='").append(level).append('\'');
        sb.append(", charge='").append(charge).append('\'');
        sb.append(", status='").append(status).append('\'');
        sb.append(", enrolled=").append(enrolled);
        sb.append(", idx=").append(idx);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append(", categorys=").append(categorys);
        sb.append('}');
        return sb.toString();
    }

}