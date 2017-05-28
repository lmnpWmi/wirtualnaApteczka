package lmnp.wirtualnaapteczka.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Class that is serialized to json and stored inside SharedPreferences for offline configuration
 * and data storage.
 */

public class OfflineConfiguration implements Serializable {

    public static Long serialVersionUID = 1L;
    public static final String SESSION_ITEM_NAME = "configuration";

    public static final String EMPTY_JSON = "{\"medicineChest\":{\"medicines\":[]},\"medicineCategories\":[]}";

    private List<String> medicineCategories;
    private MedicineChest medicineChest;

    public OfflineConfiguration() {
    }

    public List<String> getMedicineCategories() {
        return medicineCategories;
    }

    public void setMedicineCategories(List<String> medicineCategories) {
        this.medicineCategories = medicineCategories;
    }

    public MedicineChest getMedicineChest() {
        return medicineChest;
    }

    public void setMedicineChest(MedicineChest medicineChest) {
        this.medicineChest = medicineChest;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("OfflineConfiguration{");
        builder.append("medicineCategories=").append(medicineCategories);
        builder.append(", medicineChest=").append(medicineChest);
        builder.append("}");

        return builder.toString();
    }
}
