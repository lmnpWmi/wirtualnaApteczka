package lmnp.wirtualnaapteczka.helpers;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import lmnp.wirtualnaapteczka.R;
import lmnp.wirtualnaapteczka.activity.MedicineListActivity;
import lmnp.wirtualnaapteczka.fragments.MedicinesFamilyListTabFragment;
import lmnp.wirtualnaapteczka.listeners.common.LaunchVoiceRecognitionOnClickListener;
import lmnp.wirtualnaapteczka.services.DbService;
import lmnp.wirtualnaapteczka.session.SessionManager;
import lmnp.wirtualnaapteczka.utils.AppConstants;

/**
 * Helper class for search medicine alert dialog.
 * Requires a separate helper, because it is necessary to set a value from voice recognition using intent result activity.
 *
 * @author Sebastian Nowak
 * @createdAt 02.01.2018
 */
public class SearchMedicineDialogHelper {
    private EditText searchMedicineEdit;

    public void initializeSearchMedicineDialog(final MedicineListActivity medicineListActivity) {
        AlertDialog.Builder searchMedicineDialogBuilder = new AlertDialog.Builder(medicineListActivity);
        searchMedicineDialogBuilder.setTitle(R.string.search_medicine_msg);

        LayoutInflater inflater = (LayoutInflater) medicineListActivity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.search_medicine, null);

        ImageView voiceInputMedicineNameSearchBtn = (ImageView) view.findViewById(R.id.voice_input_medicine_name_search_btn);
        voiceInputMedicineNameSearchBtn.setOnClickListener(new LaunchVoiceRecognitionOnClickListener(AppConstants.REQUEST_VOICE_INPUT_MEDICINE_NAME));

        searchMedicineEdit = (EditText) view.findViewById(R.id.search_medicine_name_edit);
        searchMedicineEdit.setSingleLine();

        searchMedicineDialogBuilder.setView(view);

        searchMedicineDialogBuilder.setPositiveButton(R.string.search, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String searchValue = searchMedicineEdit.getText().toString();

                DbService dbService = SessionManager.getDbService();
                dbService.updateSearchValueInSession(searchValue);
            }
        });

        searchMedicineDialogBuilder.setNegativeButton(R.string.go_back, null);
        searchMedicineDialogBuilder.show();
    }

    public void initializeSearchFamilyMedicineDialog(final MedicinesFamilyListTabFragment medicinesFamilyListTabFragment) {
        AlertDialog.Builder searchMedicineDialogBuilder = new AlertDialog.Builder(medicinesFamilyListTabFragment.getContext());
        searchMedicineDialogBuilder.setTitle(R.string.search_medicine_msg);

        LayoutInflater inflater = (LayoutInflater) medicinesFamilyListTabFragment.getActivity().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.search_medicine, null);

        ImageView voiceInputMedicineNameSearchBtn = (ImageView) view.findViewById(R.id.voice_input_medicine_name_search_btn);
        voiceInputMedicineNameSearchBtn.setOnClickListener(new LaunchVoiceRecognitionOnClickListener(AppConstants.REQUEST_VOICE_INPUT_MEDICINE_NAME));

        searchMedicineEdit = (EditText) view.findViewById(R.id.search_medicine_name_edit);
        searchMedicineEdit.setSingleLine();

        searchMedicineDialogBuilder.setView(view);

        searchMedicineDialogBuilder.setPositiveButton(R.string.search, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String searchValue = searchMedicineEdit.getText().toString();

                DbService dbService = SessionManager.getDbService();
                dbService.updateSearchValueFamilyInSession(searchValue);
            }
        });

        searchMedicineDialogBuilder.setNegativeButton(R.string.go_back, null);
        searchMedicineDialogBuilder.show();
    }

    public EditText getSearchMedicineEditText() {
        return searchMedicineEdit;
    }
}
