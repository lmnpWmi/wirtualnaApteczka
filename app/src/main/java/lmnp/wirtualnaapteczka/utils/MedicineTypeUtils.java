package lmnp.wirtualnaapteczka.utils;

import android.content.Context;
import android.text.TextUtils;
import lmnp.wirtualnaapteczka.data.dto.MedicineTypeWithLocalizationTO;
import lmnp.wirtualnaapteczka.data.enums.MedicineQuantitySuffixEnum;
import lmnp.wirtualnaapteczka.data.enums.MedicineTypeEnum;

import java.util.ArrayList;
import java.util.List;

public class MedicineTypeUtils {
    private static final Logger logger = new Logger(MedicineTypeUtils.class);

    private MedicineTypeUtils() {
    }

    public static List<MedicineTypeWithLocalizationTO> prepareListOfLocalizedTypesTOs(Context context) {
        List<MedicineTypeWithLocalizationTO> localizedTypes = new ArrayList<>();

        for (MedicineTypeEnum medicineType : MedicineTypeEnum.values()) {
            MedicineTypeWithLocalizationTO medicineTypeWithLocalization = prepareLocalizedTypeTO(medicineType, context);
            localizedTypes.add(medicineTypeWithLocalization);
        }

        return localizedTypes;
    }

    public static MedicineTypeWithLocalizationTO prepareLocalizedTypeTO(MedicineTypeEnum medicineType, Context context) {
        String localizedType = prepareLocalizedMedicineType(medicineType, context);
        MedicineTypeWithLocalizationTO medicineTypeWithLocalization = new MedicineTypeWithLocalizationTO(medicineType, localizedType);

        return medicineTypeWithLocalization;
    }

    public static String prepareLocalizedMedicineType(MedicineTypeEnum medicineType, Context context) {
        String medicineTypeLocalized = LocalizationUtils.retrieveLocalizationForString(medicineType.name(), context);

        if (TextUtils.isEmpty(medicineTypeLocalized)) {
            logger.logWarn("Could not find translation for enum: " + medicineType.name());
        }

        return medicineTypeLocalized;
    }

    public static String prepareLocalizedTypeSuffix(MedicineTypeEnum medicineType, Context context) {
        MedicineQuantitySuffixEnum quantitySuffix = medicineType.getQuantitySuffix();
        String localizedQuantitySuffix = LocalizationUtils.retrieveLocalizationForString(quantitySuffix.name(), context);

        return localizedQuantitySuffix;
    }
}
