package lmnp.wirtualnaapteczka.dto;

import java.util.Date;

import lmnp.wirtualnaapteczka.data.MedicineTypeEnum;

/**
 * MedicineItem model for Medicine List Activity list view.
 */

public class MedicineItem {

    private Long id;
    private String name;
    private String category;
    private MedicineTypeEnum type;
    private Long amount;
    private Date dueDate;
    private String photoUrl;
    private String userNotes;
    private String description;

    private boolean overdue;
    private boolean toAcquire;

    public MedicineItem() {
    }

    public MedicineItem(Long id, String name, String category, MedicineTypeEnum type, Long amount, Date dueDate, String photoUrl, String userNotes, String description, boolean overdue, boolean toAcquire) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.type = type;
        this.amount = amount;
        this.dueDate = dueDate;
        this.photoUrl = photoUrl;
        this.userNotes = userNotes;
        this.description = description;
        this.overdue = overdue;
        this.toAcquire = toAcquire;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public MedicineTypeEnum getType() {
        return type;
    }

    public void setType(MedicineTypeEnum type) {
        this.type = type;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
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

    public boolean isOverdue() {
        return overdue;
    }

    public void setOverdue(boolean overdue) {
        this.overdue = overdue;
    }

    public boolean isToAcquire() {
        return toAcquire;
    }

    public void setToAcquire(boolean toAcquire) {
        this.toAcquire = toAcquire;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        MedicineItem that = (MedicineItem) o;

        if (overdue != that.overdue)
            return false;
        if (toAcquire != that.toAcquire)
            return false;
        if (!id.equals(that.id))
            return false;
        if (!name.equals(that.name))
            return false;
        if (!category.equals(that.category))
            return false;
        if (type != that.type)
            return false;
        if (!amount.equals(that.amount))
            return false;
        if (!dueDate.equals(that.dueDate))
            return false;
        if (photoUrl != null ? !photoUrl.equals(that.photoUrl) : that.photoUrl != null)
            return false;
        if (userNotes != null ? !userNotes.equals(that.userNotes) : that.userNotes != null)
            return false;
        return description != null ? description.equals(that.description) : that.description == null;

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + category.hashCode();
        result = 31 * result + type.hashCode();
        result = 31 * result + amount.hashCode();
        result = 31 * result + dueDate.hashCode();
        result = 31 * result + (photoUrl != null ? photoUrl.hashCode() : 0);
        result = 31 * result + (userNotes != null ? userNotes.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (overdue ? 1 : 0);
        result = 31 * result + (toAcquire ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("MedicineItem{");
        builder.append("name=").append(name);
        builder.append(", category=").append(category);
        builder.append(", type=").append(type);
        builder.append(", amount=").append(amount);
        builder.append(", dueDate=").append(dueDate);
        builder.append(", userNotes=").append(userNotes);
        builder.append(", description=").append(description);
        builder.append(", overdue=").append(overdue);
        builder.append(", toAcquire=").append(toAcquire);
        builder.append("}");

        return builder.toString();
    }
}
