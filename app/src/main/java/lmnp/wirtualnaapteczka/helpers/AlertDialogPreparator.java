package lmnp.wirtualnaapteczka.helpers;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import com.google.firebase.auth.FirebaseAuth;
import lmnp.wirtualnaapteczka.R;
import lmnp.wirtualnaapteczka.activity.AddMedicineActivity;
import lmnp.wirtualnaapteczka.activity.LogInActivity;
import lmnp.wirtualnaapteczka.activity.MedicineListActivity;
import lmnp.wirtualnaapteczka.data.dto.PhotoDescriptionTO;
import lmnp.wirtualnaapteczka.data.entities.Medicine;
import lmnp.wirtualnaapteczka.data.enums.SortingComparatorTypeEnum;
import lmnp.wirtualnaapteczka.listeners.common.PreviewPhotoWithMedicineOnClickListener;
import lmnp.wirtualnaapteczka.listeners.common.SaveDefaultSortingComparatorOnClickListener;
import lmnp.wirtualnaapteczka.listeners.mainactivity.ShowMedicineDetailsOnClickListener;
import lmnp.wirtualnaapteczka.services.DbService;
import lmnp.wirtualnaapteczka.session.SessionManager;
import lmnp.wirtualnaapteczka.utils.AppConstants;
import lmnp.wirtualnaapteczka.utils.CollectionUtils;
import lmnp.wirtualnaapteczka.utils.PhotoUtils;
import lmnp.wirtualnaapteczka.utils.functionalinterfaces.Consumer;

import java.util.List;
import java.util.Map;

/**
 * Preparator for Alert Dialogs.
 *
 * @author Sebastian Nowak
 * @createdAt 29.12.2017
 */
public class AlertDialogPreparator {
    public static void showSendFamilyMemberInvitationDialog(final Context context) {
        AlertDialog.Builder showFamilyMemberInvitationDialogBuilder = new AlertDialog.Builder(context);
        showFamilyMemberInvitationDialogBuilder.setTitle(R.string.send_invitation_dialog_title);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.email_for_family_invitation, null);

        final EditText emailEditText = (EditText) view.findViewById(R.id.email_family);

        showFamilyMemberInvitationDialogBuilder.setPositiveButton(R.string.send_invitation_btn_msg, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String targetUserEmail = emailEditText.getText().toString();
                int toastMessageId;

                if (!TextUtils.isEmpty(targetUserEmail)) {
                    Map<String, String> emailToUserIdMap = SessionManager.getEmailToUserIdMap();
                    String targetUserId = emailToUserIdMap.get(targetUserEmail.trim());

                    if (!TextUtils.isEmpty(targetUserId)) {
                        DbService dbService = SessionManager.getDbService();
                        dbService.createFamilyMemberInvitationForUser(targetUserId);

                        toastMessageId = R.string.user_has_been_invited;
                    } else {
                        toastMessageId = R.string.user_with_given_email_not_exists;
                    }
                } else {
                    toastMessageId = R.string.invalid_email_msg;
                }

                Toast.makeText(context, toastMessageId, Toast.LENGTH_SHORT).show();
            }
        });

        showFamilyMemberInvitationDialogBuilder.setNegativeButton(R.string.cancel_btn, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        showFamilyMemberInvitationDialogBuilder.setView(view);
        showFamilyMemberInvitationDialogBuilder.show();
    }

    public static void showDeleteMedicineDialog(final Context context, final Medicine medicine, final Consumer<Context> invokeAfterActionConsumer) {
        AlertDialog.Builder deleteMedicineDialogBuilder = new AlertDialog.Builder(
                context);
        deleteMedicineDialogBuilder.setTitle(context.getResources().getString(R.string.delete_medicine));
        deleteMedicineDialogBuilder.setMessage(context.getResources().getString(R.string.delete_medicine_msg));
        deleteMedicineDialogBuilder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                PhotoDescriptionTO photoDescriptionTO = medicine.getPhotoDescriptionTO();
                List<String> oldPhotoUrisToDelete = photoDescriptionTO.getOldPhotoUrisToDelete();

                deleteMedicinePhotos(oldPhotoUrisToDelete);

                DbService dbService = SessionManager.getDbService();
                dbService.deleteMedicine(medicine.getId());

                if (invokeAfterActionConsumer != null) {
                    invokeAfterActionConsumer.accept(context);
                }
            }

            private void deleteMedicinePhotos(List<String> oldPhotoUrisToDelete) {
                if (CollectionUtils.isNotEmpty(oldPhotoUrisToDelete)) {
                    for (String thumbnailUriToDelete : oldPhotoUrisToDelete) {
                        if (!TextUtils.isEmpty(thumbnailUriToDelete)) {
                            PhotoUtils.deleteThumbnailFile(thumbnailUriToDelete);
                        }
                    }
                }
            }
        });

        deleteMedicineDialogBuilder.setNegativeButton(R.string.no, null);

        deleteMedicineDialogBuilder.show();
    }

    public static void displayLogoutPopup(final Context context) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle(R.string.log_out_popup_msg);

        dialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                FirebaseAuth firebaseAuth = SessionManager.getFirebaseAuth();
                firebaseAuth.signOut();

                SharedPreferences sharedPreferences = context.getSharedPreferences(AppConstants.APP_SETTINGS, Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = sharedPreferences.edit();
                edit.putBoolean(AppConstants.LOGGED_IN, Boolean.FALSE);
                edit.putString(AppConstants.PASSWORD, null);
                edit.commit();

                Intent intent = new Intent(context, LogInActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        dialog.setNegativeButton(R.string.no, null);
        dialog.show();
    }

    public static void showEditMedicineAmountDialog(final Context context, final Medicine medicine, final Class<? extends AppCompatActivity> invokingClass) {
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
                dbService.saveOrUpdateMedicine(medicine);
            }
        });

        editMedicineAmountDialogBuilder.setNegativeButton(R.string.cancel_btn, null);
        AlertDialog editMedicineAmountDialog = editMedicineAmountDialogBuilder.show();

        Button showMedicineDetails = (Button) view.findViewById(R.id.show_medicine_details);
        showMedicineDetails.setOnClickListener(new ShowMedicineDetailsOnClickListener(medicine, editMedicineAmountDialog, invokingClass));
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

        sortByNameAscending.setOnClickListener(new SaveDefaultSortingComparatorOnClickListener(SortingComparatorTypeEnum.NAME_ASCENDING, sortListDialog));
        sortByNameDescending.setOnClickListener(new SaveDefaultSortingComparatorOnClickListener(SortingComparatorTypeEnum.NAME_DESCENDING, sortListDialog));
        sortByCreatedDateAscending.setOnClickListener(new SaveDefaultSortingComparatorOnClickListener(SortingComparatorTypeEnum.CREATED_ASCENDING, sortListDialog));
        sortByCreatedDateDescending.setOnClickListener(new SaveDefaultSortingComparatorOnClickListener(SortingComparatorTypeEnum.CREATED_DESCENDING, sortListDialog));
        sortByDueDateAscending.setOnClickListener(new SaveDefaultSortingComparatorOnClickListener(SortingComparatorTypeEnum.DUE_DATE_ASCENDING, sortListDialog));
        sortByDueDateDescending.setOnClickListener(new SaveDefaultSortingComparatorOnClickListener(SortingComparatorTypeEnum.DUE_DATE_DESCENDING, sortListDialog));
        sortByModifiedDateAscending.setOnClickListener(new SaveDefaultSortingComparatorOnClickListener(SortingComparatorTypeEnum.MODIFIED_ASCENDING, sortListDialog));
        sortByModifiedDateDescending.setOnClickListener(new SaveDefaultSortingComparatorOnClickListener(SortingComparatorTypeEnum.MODIFIED_DESCENDING, sortListDialog));
    }

    public static void showTakeNewPictureOrViewExistingDialog(final AddMedicineActivity addMedicineActivity, final Medicine medicine, final Class<? extends AppCompatActivity> addMedicineInvokingClass) {
        AlertDialog.Builder takeNewPictureOrViewExistingDialogBuilder = new AlertDialog.Builder(addMedicineActivity);
        takeNewPictureOrViewExistingDialogBuilder.setTitle(addMedicineActivity.getResources().getString(R.string.choose_action));
        takeNewPictureOrViewExistingDialogBuilder.setPositiveButton(R.string.view_medicine_photo, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                PreviewPhotoWithMedicineOnClickListener previewPhotoWithMedicineOnClickListener = new PreviewPhotoWithMedicineOnClickListener(medicine, AddMedicineActivity.class, addMedicineInvokingClass);
                previewPhotoWithMedicineOnClickListener.onClick(addMedicineActivity.getCurrentFocus());
            }
        });

        takeNewPictureOrViewExistingDialogBuilder.setNegativeButton(R.string.take_new_photo, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                PhotoUtils.takePicture(addMedicineActivity, medicine);
            }
        });

        takeNewPictureOrViewExistingDialogBuilder.show();
    }
}
