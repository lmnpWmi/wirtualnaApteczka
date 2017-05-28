package lmnp.wirtualnaapteczka.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Avaiable medicine types.
 */

public enum MedicineTypeEnum {
    PILL("Tabletka"),
    SYRUP("Syrop"),
    SACHET("Saszetka"),
    PIECE("Sztuka"),
    OTHER("Inna");

    private static final Map<String, MedicineTypeEnum> map = new HashMap<>();

    static {
        for (MedicineTypeEnum medicineType : values()) {
            map.put(medicineType.getName(), medicineType);
        }
    }

    private String name;

    MedicineTypeEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static MedicineTypeEnum of(String typeName) {
        MedicineTypeEnum medicineType = map.get(typeName);
        if (medicineType == null) {
            throw new IllegalArgumentException("Invalid name for MedicineTypeEnum Enum");
        }

        return medicineType;
    }

    public static List<String> retrieveNames() {
        List<String> typeNames = new ArrayList<>();
        for (MedicineTypeEnum medicineType : values()) {
            typeNames.add(medicineType.getName());
        }

        return typeNames;
    }
}
