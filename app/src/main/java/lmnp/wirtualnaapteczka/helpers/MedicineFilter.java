package lmnp.wirtualnaapteczka.helpers;

import android.text.TextUtils;
import lmnp.wirtualnaapteczka.data.entities.Medicine;
import lmnp.wirtualnaapteczka.utils.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MedicineFilter {
    private static final String CASE_INSENSITIVITY_SIGN = "(?i)";

    private MedicineFilter() {
    }

    public static List<Medicine> filterMedicines(String searchValue, List<Medicine> medicines, boolean exactMatches) {
        List<Medicine> filteredMedicines = new ArrayList<>();

        if (TextUtils.isEmpty(searchValue)) {
            filteredMedicines = medicines;
        }

        if (CollectionUtils.isNotEmpty(medicines) && !TextUtils.isEmpty(searchValue)) {
            String patternRegex = prepareRegexPattern(searchValue, exactMatches);
            Pattern pattern = Pattern.compile(patternRegex);
            Matcher matcher;

            for (Medicine medicine : medicines) {
                matcher = pattern.matcher(medicine.getName());

                if (matcher.matches()) {
                    filteredMedicines.add(medicine);
                }
            }
        }

        return filteredMedicines;
    }

    private static String prepareRegexPattern(String searchValue, boolean exactMatches) {
        String pattern;

        if (exactMatches) {
            pattern = CASE_INSENSITIVITY_SIGN + searchValue;
        } else {
            pattern = CASE_INSENSITIVITY_SIGN + ".*" + searchValue + ".*";
        }

        return pattern;
    }
}
