package lmnp.wirtualnaapteczka.listeners.common;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import lmnp.wirtualnaapteczka.activity.MedicineListActivity;
import lmnp.wirtualnaapteczka.data.entities.Medicine;
import lmnp.wirtualnaapteczka.data.enums.SortingComparatorTypeEnum;
import lmnp.wirtualnaapteczka.services.DbService;
import lmnp.wirtualnaapteczka.session.SessionManager;
import lmnp.wirtualnaapteczka.utils.AppConstants;

import java.util.List;

/**
 * Sets chosen comparator in the Shared Preferences.
 *
 * @author Sebastian Nowak
 * @createdAt 29.12.2017
 */
public class SaveDefaultSortingComparatorOnClickListener implements View.OnClickListener {
    private SortingComparatorTypeEnum sortingComparatorTypeEnum;
    private AlertDialog sortListDialog;
    private MedicineListActivity medicineListActivity;

    public SaveDefaultSortingComparatorOnClickListener(SortingComparatorTypeEnum sortingComparatorTypeEnum, AlertDialog sortListDialog, MedicineListActivity medicineListActivity) {
        this.sortingComparatorTypeEnum = sortingComparatorTypeEnum;
        this.sortListDialog = sortListDialog;
        this.medicineListActivity = medicineListActivity;
    }

    @Override
    public void onClick(View v) {
        SharedPreferences sharedPreferences = v.getContext().getSharedPreferences(AppConstants.APP_SETTINGS, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();

        edit.putString(AppConstants.DEFAULT_COMPARATOR, sortingComparatorTypeEnum.name());
        edit.commit();

        sortListDialog.dismiss();

        DbService dbService = SessionManager.getDbService();
        List<Medicine> allMedicinesForCurrentUser = dbService.findAllMedicinesForCurrentUser();
        medicineListActivity.initializeMedicineList(allMedicinesForCurrentUser);
    }
}
