package lmnp.wirtualnaapteczka.data.entities;

import java.util.List;

public class Pharmacy {
    private String id;
    private List<Medicine> medicines;

    public Pharmacy() {
    }

    public Pharmacy(List<Medicine> medicines) {
        this.medicines = medicines;
    }

    public Pharmacy(String id, List<Medicine> medicines) {
        this.id = id;
        this.medicines = medicines;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Medicine> getMedicines() {
        return medicines;
    }

    public void setMedicines(List<Medicine> medicines) {
        this.medicines = medicines;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pharmacy pharmacy = (Pharmacy) o;

        if (id != null ? !id.equals(pharmacy.id) : pharmacy.id != null) return false;
        return medicines != null ? medicines.equals(pharmacy.medicines) : pharmacy.medicines == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (medicines != null ? medicines.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Pharmacy{" +
                "id='" + id + '\'' +
                ", medicines=" + medicines +
                '}';
    }
}
