package models;

import java.util.Date;

public class Avis {

    private int id;
    private int createdBy;
    private int deliveryId;
    private Date createdDate;
    private String comment;

    public Avis() {
    }

    public Avis(int createdBy, int deliveryId, Date createdDate, String comment) {
        this.createdBy = createdBy;
        this.deliveryId = deliveryId;
        this.createdDate = createdDate;
        this.comment = comment;
    }

    public Avis(int id, int createdBy, int deliveryId, Date createdDate, String comment) {
        this.id = id;
        this.createdBy = createdBy;
        this.deliveryId = deliveryId;
        this.createdDate = createdDate;
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public int getDeliveryId() {
        return deliveryId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public void setDeliveryId(int deliveryId) {
        this.deliveryId = deliveryId;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Avis{" +
                "id=" + id +
                ", createdBy=" + createdBy +
                ", deliveryId=" + deliveryId +
                ", createdDate=" + createdDate +
                ", comment='" + comment + '\'' +
                '}';
    }
}
