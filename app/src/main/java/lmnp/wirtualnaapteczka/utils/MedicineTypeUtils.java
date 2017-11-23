package lmnp.wirtualnaapteczka.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import lmnp.wirtualnaapteczka.data.enums.MedicineTypeEnum;
import lmnp.wirtualnaapteczka.data.dto.MedicineTypeWithLocalizationTO;

import java.util.ArrayList;
import java.util.List;

public class MedicineTypeUtils {

    private MedicineTypeUtils() {
    }

    public static List<MedicineTypeWithLocalizationTO> prepareListOfLocalizedTypesTOs(Context context) {
        List<MedicineTypeWithLocalizationTO> localizedTypes = new ArrayList<>();

        for (MedicineTypeEnum medicineType : MedicineTypeEnum.values()) {
            String localizedType = prepareLocalizedMedicineType(medicineType, context);

            localizedTypes.add(new MedicineTypeWithLocalizationTO(medicineType, localizedType));
        }

        return localizedTypes;
    }

    public static String prepareLocalizedMedicineType(MedicineTypeEnum medicineType, Context context) {
        String medicineTypeLocalized = LocalizationUtils.retrieveLocalizationForString(medicineType.name(), context);

        if (TextUtils.isEmpty(medicineTypeLocalized)) {
            Log.w(MedicineTypeEnum.class.getSimpleName(), "Could not find translation for enum: " + medicineType.name());
        }

        return medicineTypeLocalized;
    }
}
