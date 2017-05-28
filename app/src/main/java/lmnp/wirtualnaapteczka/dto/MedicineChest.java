package lmnp.wirtualnaapteczka.dto;

import java.util.List;

/**
 * Class that represents single Medical Kit / Medicine Chest. Name will probably have to be changed.
 */

public class MedicineChest {

    private List<MedicineItem> medicines;

    public MedicineChest() {
    }

    public List<MedicineItem> getMedicines() {
        return medicines;
    }

    public void setMedicines(List<MedicineItem> medicines) {
        this.medicines = medicines;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("MedicineChest{");
        builder.append("medicines=").append(medicines);
        builder.append("}");

        return builder.toString();
    }
}
