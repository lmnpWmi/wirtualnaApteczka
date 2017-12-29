package lmnp.wirtualnaapteczka.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import lmnp.wirtualnaapteczka.R;
import lmnp.wirtualnaapteczka.activity.MainActivity;
import lmnp.wirtualnaapteczka.activity.MedicineListActivity;
import lmnp.wirtualnaapteczka.data.entities.Medicine;
import lmnp.wirtualnaapteczka.data.enums.SortingComparatorTypeEnum;
import lmnp.wirtualnaapteczka.listeners.mainactivity.ShowMedicineDetailsOnClickListener;
import lmnp.wirtualnaapteczka.listeners.medicinelistactivity.MedicineItemOnClickListener;
import lmnp.wirtualnaapteczka.listeners.common.SaveDefaultSortingComparatorOnClickListener;
import lmnp.wirtualnaapteczka.services.DbService;
import lmnp.wirtualnaapteczka.session.SessionManager;
import lmnp.wirtualnaapteczka.utils.functionalinterfaces.Consumer;

import java.util.List;

/**
 * Preparator for Alert Dialogs.
 *
 * @author Sebastian Nowak
 * @createdAt 29.12.2017
 */
public class AlertDialogPreparator {
    public static void showSearchMedicineDialog(final MedicineListActivity medicineListActivity) {
        AlertDialog.Builder searchMedicineDialogBuilder = new AlertDialog.Builder(medicineListActivity);
        searchMedicineDialogBuilder.setTitle(R.string.search_medicine_msg);

        final EditText input = new EditText(medicineListActivity);
        input.setSingleLine();
        searchMedicineDialogBuilder.setView(input);

        final DbService dbService = SessionManager.getDbService();

        searchMedicineDialogBuilder.setPositiveButton(R.string.search, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String searchValue = input.getText().toString();
                List<Medicine> medicines = dbService.findAllMedicinesForCurrentUser();
                List<Medicine> filteredMedicines = MedicineFilter.filterMedicines(searchValue, medicines, false);

                medicineListActivity.initializeMedicineList(filteredMedicines);
            }
        });

        searchMedicineDialogBuilder.setNegativeButton(R.string.go_back, null);
        searchMedicineDialogBuilder.show();
    }

    public static void showDeleteMedicineDialog(final Context context, final Medicine medicine, final Consumer invokeAfterActionConsumer) {
        AlertDialog.Builder deleteMedicineDialogBuilder = new AlertDialog.Builder(
                context);
        deleteMedicineDialogBuilder.setTitle(context.getResources().getString(R.string.delete_medicine));
        deleteMedicineDialogBuilder.setMessage(context.getResources().getString(R.string.delete_medicine_msg));
        deleteMedicineDialogBuilder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                DbService dbService = SessionManager.getDbService();
                dbService.deleteMedicine(medicine.getId());

                invokeAfterActionConsumer.accept(context);
            }
        });

        deleteMedicineDialogBuilder.setNegativeButton(R.string.no, null);

        deleteMedicineDialogBuilder.show();
    }

    public static void showEditMedicineAmountDialog(final Context context, final Medicine medicine) {
        AlertDialog.Builder editMedicineAmountDialogBuilder = new AlertDialog.Builder(context);
        editMedicineAmountDialogBuilder.setTitle(R.string.edit_amount);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.number_picker, null);

        final NumberPicker numberPicker = (NumberPicker) view.findViewById(R.id.amount_picker);
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(10000);
        numberPicker.setValue(medicine.getAmount());

        LinearLayout amountPickerPanel = (LinearLayout) view.findViewById(R.id.amount_picker_panel);
        editMedicineAmountDialogBuilder.setView(amountPickerPanel);
        editMedicineAmountDialogBuilder.setPositiveButton(R.string.confirm_btn, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int amountValue = numberPicker.getValue();
                medicine.setAmount(amountValue);

                DbService dbService = SessionManager.getDbService();
                dbService.updateMedicine(medicine);

                MainActivity mainActivity = (MainActivity) context;
                mainActivity.initializeRecentlyUsedMedicinesList();
            }
        });

        editMedicineAmountDialogBuilder.setNegativeButton(R.string.cancel_btn, null);
        AlertDialog editMedicineAmountDialog = editMedicineAmountDialogBuilder.show();

        Button showMedicineDetails = (Button) view.findViewById(R.id.show_medicine_details);
        showMedicineDetails.setOnClickListener(new ShowMedicineDetailsOnClickListener(medicine, editMedicineAmountDialog));
    }

    public static void showSortListDialog(final MedicineListActivity medicineListActivity) {
        AlertDialog.Builder sortListDialogBuilder = new AlertDialog.Builder(medicineListActivity);
        sortListDialogBuilder.setTitle(R.string.sort_medicines);

        LayoutInflater inflater = (LayoutInflater) medicineListActivity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.sort_list, null);

        LinearLayout sortListPanel = (LinearLayout) view.findViewById(R.id.sort_list_panel);
        sortListDialogBuilder.setView(sortListPanel);

        AlertDialog sortListDialog = sortListDialogBuilder.show();

        Button sortByNameAscending = (Button) view.findViewById(R.id.sort_name_a_z_btn);
        Button sortByNameDescending = (Button) view.findViewById(R.id.sort_name_z_a_btn);
        Button sortByCreatedDateAscending = (Button) view.findViewById(R.id.sort_date_created_ascending_btn);
        Button sortByCreatedDateDescending = (Button) view.findViewById(R.id.sort_date_created_descending_btn);
        Button sortByDueDateAscending = (Button) view.findViewById(R.id.sort_due_date_ascending_btn);
        Button sortByDueDateDescending = (Button) view.findViewById(R.id.sort_due_date_descending_btn);
        Button sortByModifiedDateAscending = (Button) view.findViewById(R.id.sort_modified_date_ascending);
        Button sortByModifiedDateDescending = (Button) view.findViewById(R.id.sort_modified_date_descending);

        sortByNameAscending.setOnClickListener(new SaveDefaultSortingComparatorOnClickListener(SortingComparatorTypeEnum.NAME_ASCENDING, sortListDialog, medicineListActivity));
        sortByNameDescending.setOnClickListener(new SaveDefaultSortingComparatorOnClickListener(SortingComparatorTypeEnum.NAME_DESCENDING, sortListDialog, medicineListActivity));
        sortByCreatedDateAscending.setOnClickListener(new SaveDefaultSortingComparatorOnClickListener(SortingComparatorTypeEnum.CREATED_ASCENDING, sortListDialog, medicineListActivity));
        sortByCreatedDateDescending.setOnClickListener(new SaveDefaultSortingComparatorOnClickListener(SortingComparatorTypeEnum.CREATED_DESCENDING, sortListDialog, medicineListActivity));
        sortByDueDateAscending.setOnClickListener(new SaveDefaultSortingComparatorOnClickListener(SortingComparatorTypeEnum.DUE_DATE_ASCENDING, sortListDialog, medicineListActivity));
        sortByDueDateDescending.setOnClickListener(new SaveDefaultSortingComparatorOnClickListener(SortingComparatorTypeEnum.DUE_DATE_DESCENDING, sortListDialog, medicineListActivity));
        sortByModifiedDateAscending.setOnClickListener(new SaveDefaultSortingComparatorOnClickListener(SortingComparatorTypeEnum.MODIFIED_ASCENDING, sortListDialog, medicineListActivity));
        sortByModifiedDateDescending.setOnClickListener(new SaveDefaultSortingComparatorOnClickListener(SortingComparatorTypeEnum.MODIFIED_DESCENDING, sortListDialog, medicineListActivity));
    }
}
