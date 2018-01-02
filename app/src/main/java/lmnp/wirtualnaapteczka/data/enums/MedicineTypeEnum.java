package lmnp.wirtualnaapteczka.data.enums;

/**
 * Represents available types of medicines.
 *
 * @author Sebastian Nowak
 * @createdAt 02.01.2018
 */
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
