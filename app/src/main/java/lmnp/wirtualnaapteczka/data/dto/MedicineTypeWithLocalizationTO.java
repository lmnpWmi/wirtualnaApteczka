package lmnp.wirtualnaapteczka.data.dto;

import lmnp.wirtualnaapteczka.data.enums.MedicineTypeEnum;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MedicineTypeWithLocalizationTO that = (MedicineTypeWithLocalizationTO) o;

        if (medicineTypeEnum != that.medicineTypeEnum) return false;
        return localizedTypeName != null ? localizedTypeName.equals(that.localizedTypeName) : that.localizedTypeName == null;
    }

    @Override
    public int hashCode() {
        int result = medicineTypeEnum != null ? medicineTypeEnum.hashCode() : 0;
        result = 31 * result + (localizedTypeName != null ? localizedTypeName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return localizedTypeName;
    }
}
