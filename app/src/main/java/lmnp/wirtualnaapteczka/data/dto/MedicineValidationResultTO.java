package lmnp.wirtualnaapteczka.data.dto;

import java.util.ArrayList;
import java.util.List;

public class MedicineValidationResultTO {
    private List<String> errorMessages;

    public MedicineValidationResultTO() {
        this.errorMessages = new ArrayList<>();
    }

    public void addErrorMessage(String errorMessage) {
        errorMessages.add(errorMessage);
    }

    public String prepareErrorMessageOutput() {
        StringBuilder builder = new StringBuilder();
        int lastItemIndex = errorMessages.size() - 1;

        for (int i = 0; i < errorMessages.size(); i++) {
            builder.append(errorMessages.get(i));

            if (i < lastItemIndex) {
                builder.append('\n');
            }
        }

        return builder.toString();
    }

    public boolean isMedicineValid() {
        boolean result = errorMessages.size() == 0;

        return result;
    }
}
