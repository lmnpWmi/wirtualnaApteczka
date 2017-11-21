package lmnp.wirtualnaapteczka.data.dto;

import lmnp.wirtualnaapteczka.data.MedicineTypeEnum;

public class MedicineTypeWithLocalizationTO {
    private MedicineTypeEnum medicineTypeEnum;
    private String localizedTypeName;

    public MedicineTypeWithLocalizationTO(MedicineTypeEnum medicineTypeEnum, String localizedTypeName) {
        this.medicineTypeEnum = medicineTypeEnum;
        this.localizedTypeName = localizedTypeName;
    }

    public MedicineTypeEnum getMedicineTypeEnum() {
        return medicineTypeEnum;
    }

    public String getLocalizedTypeName() {
        return localizedTypeName;
    }

    @Override
    public String toString() {
        return localizedTypeName;
    }
}
