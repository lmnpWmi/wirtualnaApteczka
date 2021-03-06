package lmnp.wirtualnaapteczka.data.entities;

import lmnp.wirtualnaapteczka.data.dto.PhotoDescriptionTO;
import lmnp.wirtualnaapteczka.data.enums.MedicineTypeEnum;

import java.io.Serializable;
import java.util.Date;

public class Medicine implements Serializable {
    private static final long serialVersionUID = -7115888632811968571L;

    private String id;
    private String name;
    private MedicineTypeEnum type;
    private Integer amount;
    private Date dueDate;
    private Date createdAt;
    private Date updatedAt;
    private String userNotes;
    private String description;

    private boolean shareWithFriends;
    private String ownerUsername;

    private PhotoDescriptionTO photoDescriptionTO;

    public Medicine() {
        this.photoDescriptionTO = new PhotoDescriptionTO();
    }

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

    public MedicineTypeEnum getType() {
        return type;
    }

    public void setType(MedicineTypeEnum type) {
        this.type = type;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
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

    public String getUserNotes() {
        return userNotes;
    }

    public void setUserNotes(String userNotes) {
        this.userNotes = userNotes;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isShareWithFriends() {
        return shareWithFriends;
    }

    public void setShareWithFriends(boolean shareWithFriends) {
        this.shareWithFriends = shareWithFriends;
    }

    public PhotoDescriptionTO getPhotoDescriptionTO() {
        return photoDescriptionTO;
    }

    public void setPhotoDescriptionTO(PhotoDescriptionTO photoDescriptionTO) {
        this.photoDescriptionTO = photoDescriptionTO;
    }

    public String getOwnerUsername() {
        return ownerUsername;
    }

    public void setOwnerUsername(String ownerUsername) {
        this.ownerUsername = ownerUsername;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Medicine medicine = (Medicine) o;

        if (shareWithFriends != medicine.shareWithFriends) return false;
        if (id != null ? !id.equals(medicine.id) : medicine.id != null) return false;
        if (name != null ? !name.equals(medicine.name) : medicine.name != null) return false;
        if (type != medicine.type) return false;
        if (amount != null ? !amount.equals(medicine.amount) : medicine.amount != null) return false;
        if (dueDate != null ? !dueDate.equals(medicine.dueDate) : medicine.dueDate != null) return false;
        if (createdAt != null ? !createdAt.equals(medicine.createdAt) : medicine.createdAt != null) return false;
        if (updatedAt != null ? !updatedAt.equals(medicine.updatedAt) : medicine.updatedAt != null) return false;
        if (userNotes != null ? !userNotes.equals(medicine.userNotes) : medicine.userNotes != null) return false;
        if (description != null ? !description.equals(medicine.description) : medicine.description != null)
            return false;
        if (ownerUsername != null ? !ownerUsername.equals(medicine.ownerUsername) : medicine.ownerUsername != null)
            return false;
        return photoDescriptionTO != null ? photoDescriptionTO.equals(medicine.photoDescriptionTO) : medicine.photoDescriptionTO == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        result = 31 * result + (dueDate != null ? dueDate.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (updatedAt != null ? updatedAt.hashCode() : 0);
        result = 31 * result + (userNotes != null ? userNotes.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (shareWithFriends ? 1 : 0);
        result = 31 * result + (ownerUsername != null ? ownerUsername.hashCode() : 0);
        result = 31 * result + (photoDescriptionTO != null ? photoDescriptionTO.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Medicine{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", amount=" + amount +
                ", dueDate=" + dueDate +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", userNotes='" + userNotes + '\'' +
                ", description='" + description + '\'' +
                ", shareWithFriends=" + shareWithFriends +
                ", ownerUsername='" + ownerUsername + '\'' +
                ", photoDescriptionTO=" + photoDescriptionTO +
                '}';
    }
}
