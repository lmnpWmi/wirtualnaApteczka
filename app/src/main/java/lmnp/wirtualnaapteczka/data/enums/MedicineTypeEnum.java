package lmnp.wirtualnaapteczka.data.enums;

/**
 * Represents available types of medicines.
 *
 * @author Sebastian Nowak
 * @createdAt 02.01.2018
 */
public enum MedicineTypeEnum {
    PILL(MedicineQuantitySuffixEnum.PIECES),
    SYRUP(MedicineQuantitySuffixEnum.MILLILITER),
    SACHET(MedicineQuantitySuffixEnum.PIECES),
    SPRAY(MedicineQuantitySuffixEnum.MILLILITER),
    OTHER(MedicineQuantitySuffixEnum.PIECES);

    private MedicineQuantitySuffixEnum quantitySuffix;

    MedicineTypeEnum(MedicineQuantitySuffixEnum quantitySuffix) {
        this.quantitySuffix = quantitySuffix;
    }

    public MedicineQuantitySuffixEnum getQuantitySuffix() {
        return quantitySuffix;
    }
}
