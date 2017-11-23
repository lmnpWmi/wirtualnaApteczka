package lmnp.wirtualnaapteczka.data.enums;

public enum MedicineTypeEnum {
    PILL(MedicineQuantitySuffix.PIECES),
    SYRUP(MedicineQuantitySuffix.MILLILITER),
    SACHET(MedicineQuantitySuffix.PIECES),
    SPRAY(MedicineQuantitySuffix.MILLILITER),
    OTHER(MedicineQuantitySuffix.PIECES);

    private MedicineQuantitySuffix quantitySuffix;

    MedicineTypeEnum(MedicineQuantitySuffix quantitySuffix) {
        this.quantitySuffix = quantitySuffix;
    }

    public MedicineQuantitySuffix getQuantitySuffix() {
        return quantitySuffix;
    }
}
