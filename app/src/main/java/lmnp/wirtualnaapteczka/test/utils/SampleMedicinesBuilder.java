package lmnp.wirtualnaapteczka.test.utils;

import lmnp.wirtualnaapteczka.data.MedicineTypeEnum;
import lmnp.wirtualnaapteczka.entities.Medicine;

import java.util.ArrayList;
import java.util.List;

public class SampleMedicinesBuilder {

    private static final String SAMPLE_DESCRIPTION = "This is some sample description for test purposes.";

    private List<Medicine> sampleMedicines;

    private SampleMedicinesBuilder() {
        sampleMedicines = new ArrayList<>();
    }

    public static SampleMedicinesBuilder prepareBuilder() {
        return new SampleMedicinesBuilder();
    }

    public SampleMedicinesBuilder addFirstMedicine() {
        Medicine medicine1 = new Medicine();
        medicine1.setId(1L);
        medicine1.setAmount(3L);
        medicine1.setDescription(SAMPLE_DESCRIPTION);
        medicine1.setPhotoUri("/photos/1.png");
        medicine1.setName("Ibuprom Zatoki");
        medicine1.setShareWithFriends(true);
        medicine1.setUserNotes("Brać 3x dziennie");
        medicine1.setType(MedicineTypeEnum.PILL);

        sampleMedicines.add(medicine1);

        return this;
    }

    public SampleMedicinesBuilder addSecondMedicine() {
        Medicine medicine2 = new Medicine();
        medicine2.setId(2L);
        medicine2.setAmount(10L);
        medicine2.setDescription(SAMPLE_DESCRIPTION);
        medicine2.setPhotoUri("/photos/2.png");
        medicine2.setName("Acatar Control");
        medicine2.setShareWithFriends(true);
        medicine2.setUserNotes("Brać 1x dziennie. Max do tygodnia.");
        medicine2.setType(MedicineTypeEnum.SPRAY);

        sampleMedicines.add(medicine2);

        return this;
    }

    public SampleMedicinesBuilder addThirdMedicine() {
        Medicine medicine3 = new Medicine();
        medicine3.setId(3L);
        medicine3.setAmount(0L);
        medicine3.setDescription(SAMPLE_DESCRIPTION);
        medicine3.setPhotoUri("/photos/3.png");
        medicine3.setName("Acyklowir");
        medicine3.setShareWithFriends(false);
        medicine3.setUserNotes("Raz dziennie");
        medicine3.setType(MedicineTypeEnum.OTHER);

        sampleMedicines.add(medicine3);

        return this;
    }

    public SampleMedicinesBuilder addFourthMedicine() {
        Medicine medicine4 = new Medicine();
        medicine4.setId(4L);
        medicine4.setAmount(1L);
        medicine4.setDescription(SAMPLE_DESCRIPTION);
        medicine4.setPhotoUri("/photos/4.png");
        medicine4.setName("Interferon");
        medicine4.setShareWithFriends(true);
        medicine4.setType(MedicineTypeEnum.SACHET);

        sampleMedicines.add(medicine4);

        return this;
    }

    public SampleMedicinesBuilder addFifthMedicine() {
        Medicine medicine5 = new Medicine();
        medicine5.setId(5L);
        medicine5.setAmount(10L);
        medicine5.setDescription(SAMPLE_DESCRIPTION);
        medicine5.setPhotoUri("/photos/5.png");
        medicine5.setName("Rybawiryna");
        medicine5.setShareWithFriends(false);
        medicine5.setType(MedicineTypeEnum.SYRUP);

        sampleMedicines.add(medicine5);

        return this;
    }

    public SampleMedicinesBuilder addAll() {
        addFirstMedicine();
        addSecondMedicine();
        addThirdMedicine();
        addFourthMedicine();
        addFifthMedicine();

        return this;
    }

    public List<Medicine> build() {
        return sampleMedicines;
    }
}
