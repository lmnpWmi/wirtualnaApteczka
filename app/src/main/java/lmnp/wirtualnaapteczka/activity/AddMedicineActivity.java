package lmnp.wirtualnaapteczka.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.*;
import lmnp.wirtualnaapteczka.R;
import lmnp.wirtualnaapteczka.data.dto.MedicineTypeWithLocalizationTO;
import lmnp.wirtualnaapteczka.data.dto.PhotoDescriptionTO;
import lmnp.wirtualnaapteczka.data.entities.Medicine;
import lmnp.wirtualnaapteczka.helpers.AlertDialogPreparator;
import lmnp.wirtualnaapteczka.listeners.addmedicineactivity.AddPhotoOnClickListener;
import lmnp.wirtualnaapteczka.listeners.addmedicineactivity.CalendarOnClickListener;
import lmnp.wirtualnaapteczka.listeners.addmedicineactivity.MedicineTypeOnItemSelectedListener;
import lmnp.wirtualnaapteczka.listeners.addmedicineactivity.SaveNewMedicineOnClickListener;
import lmnp.wirtualnaapteczka.listeners.common.LaunchVoiceRecognitionOnClickListener;
import lmnp.wirtualnaapteczka.session.SessionManager;
import lmnp.wirtualnaapteczka.utils.AppConstants;
import lmnp.wirtualnaapteczka.utils.MedicineTypeUtils;
import lmnp.wirtualnaapteczka.utils.PhotoUtils;
import lmnp.wirtualnaapteczka.utils.functionalinterfaces.Consumer;

import java.io.File;
import java.text.DateFormat;
import java.util.List;

/**
 * Activity responsible for handling events on the layout for adding new medicines.
 *
 * @author Sebastian Nowak
 * @createdAt 27.12.2017
 */
public class AddMedicineActivity extends AppCompatActivity {
    private boolean editingExistingMedicine;
    private Medicine currentMedicine;

    private Class<? extends AppCompatActivity> invokingClass;

    private EditText nameEdit;
    private EditText amountEdit;
    private EditText notesEdit;
    private TextView quantitySuffixLabel;
    private TextView dueDateCalendar;
    private FloatingActionButton saveMedicineBtn;
    private Spinner medicineTypeSpinner;
    private CheckBox shareMedicineWithFriends;
    private ImageButton addMedicinePhotoBtn;
    private ImageButton voiceInputMedicineNameBtn;
    private ImageButton voiceInputMedicineNotesBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SessionManager.clearSearchValueInUserSession();

        if (savedInstanceState != null) {
            currentMedicine = (Medicine) savedInstanceState.getSerializable(AppConstants.MEDICINE);
            invokingClass = (Class<? extends AppCompatActivity>) savedInstanceState.getSerializable(AppConstants.INVOKING_CLASS);
        }

        setContentView(R.layout.activity_add);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        initializeViewComponents();
        initializeActivityState();

        initializeMedicineTypeSpinner();
        initializeListeners();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (editingExistingMedicine) {
            getMenuInflater().inflate(R.menu.menu_delete, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean result;

        switch (item.getItemId()) {
            case R.id.delete_med_options_item:
                Consumer<Context> invokeAfterMedicineDeleted = new Consumer<Context>() {
                    @Override
                    public void accept(Context context) {
                        Intent intent = new Intent(context, MedicineListActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        context.startActivity(intent);
                    }
                };

                AlertDialogPreparator.showDeleteMedicineDialog(AddMedicineActivity.this, currentMedicine, invokeAfterMedicineDeleted);
                result = true;
                break;
            default:
                result = super.onOptionsItemSelected(item);
        }

        return result;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case AppConstants.REQUEST_IMAGE_CAPTURE:
                    try {
                        PhotoDescriptionTO photoDescriptionTO = currentMedicine.getPhotoDescriptionTO();

                        File fullSizePhoto = new File(photoDescriptionTO.getFullSizePhotoUri());
                        File smallSizePhoto = PhotoUtils.prepareSmallSizePhotoFile(fullSizePhoto);
                        String smallSizePhotoAbsolutePath = smallSizePhoto.getAbsolutePath();
                        photoDescriptionTO.setSmallSizePhotoUri(smallSizePhotoAbsolutePath);

                        setMedicineThumbnail(photoDescriptionTO);
                    } catch (Exception e) {
                        // Had to remove logs, becasue of google play
                    }
                    break;
                case AppConstants.REQUEST_VOICE_INPUT_MEDICINE_NAME:
                    String potentialMedicineName = retrievePotentialMedicineNameFromVoiceRecognizedData(data);

                    if (!TextUtils.isEmpty(potentialMedicineName)) {
                        nameEdit.setText(potentialMedicineName);
                    }
                    break;
                case AppConstants.REQUEST_VOICE_INPUT_MEDICINE_NOTES:
                    String potentialMedicineNotes = retrievePotentialMedicineNameFromVoiceRecognizedData(data);

                    if (!TextUtils.isEmpty(potentialMedicineNotes)) {
                        notesEdit.setText(potentialMedicineNotes);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(AppConstants.MEDICINE, currentMedicine);
        outState.putSerializable(AppConstants.INVOKING_CLASS, invokingClass);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), invokingClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        getApplicationContext().startActivity(intent);
    }

    private void initializeActivityState() {
        if (currentMedicine == null) {
            currentMedicine = (Medicine) getIntent().getSerializableExtra(AppConstants.MEDICINE);
        }

        if (invokingClass == null) {
            invokingClass = (Class<? extends AppCompatActivity>) getIntent().getSerializableExtra(AppConstants.INVOKING_CLASS);
        }

        editingExistingMedicine = currentMedicine != null;

        if (editingExistingMedicine) {
            updateComponentsValuesUsingExistingMedicine();
        } else {
            currentMedicine = new Medicine();
        }
    }

    private void initializeViewComponents() {
        nameEdit = (EditText) findViewById(R.id.medicine_name);
        nameEdit.setSelected(false);
        amountEdit = (EditText) findViewById(R.id.medicine_amount);
        amountEdit.setSelected(false);
        notesEdit = (EditText) findViewById(R.id.notes_text);
        notesEdit.setSelected(false);

        quantitySuffixLabel = (TextView) findViewById(R.id.medicine_quantity_suffix_label);
        dueDateCalendar = (TextView) findViewById(R.id.medicine_due_date_calendar);
        saveMedicineBtn = (FloatingActionButton) findViewById(R.id.save_new_medicine_btn);
        medicineTypeSpinner = (Spinner) findViewById(R.id.medicine_type_spinner);
        shareMedicineWithFriends = (CheckBox) findViewById(R.id.share_medicine_checkbox);

        addMedicinePhotoBtn = (ImageButton) findViewById(R.id.add_medicine_photo);
        voiceInputMedicineNameBtn = (ImageButton) findViewById(R.id.voice_input_medicine_name_btn);
        voiceInputMedicineNotesBtn = (ImageButton) findViewById(R.id.voice_input_medicine_notes_btn);
    }

    private void updateComponentsValuesUsingExistingMedicine() {
        nameEdit.setText(currentMedicine.getName());
        notesEdit.setText(currentMedicine.getUserNotes());
        shareMedicineWithFriends.setChecked(currentMedicine.isShareWithFriends());

        if (currentMedicine.getDueDate() != null) {
            DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getApplicationContext());
            String formattedDate = dateFormat.format(currentMedicine.getDueDate());

            dueDateCalendar.setText(formattedDate);
        }

        if (currentMedicine.getAmount() != null) {
            amountEdit.setText(String.valueOf(currentMedicine.getAmount()));
        }

        PhotoDescriptionTO photoDescriptionTO = currentMedicine.getPhotoDescriptionTO();

        if (!photoDescriptionTO.isEmpty()) {
            setMedicineThumbnail(photoDescriptionTO);
        }
    }

    private void initializeMedicineTypeSpinner() {
        List<MedicineTypeWithLocalizationTO> localizedTypesTOs = MedicineTypeUtils.prepareListOfLocalizedTypesTOs(getApplicationContext());
        ArrayAdapter<MedicineTypeWithLocalizationTO> medicineTypesAdapter = new ArrayAdapter<>(this, R.layout.dropdown_element, localizedTypesTOs);
        medicineTypeSpinner.setAdapter(medicineTypesAdapter);

        if (currentMedicine.getType() != null) {
            updateMedicineTypeInSpinner(medicineTypesAdapter);
        }

        medicineTypeSpinner.setOnItemSelectedListener(new MedicineTypeOnItemSelectedListener(currentMedicine, medicineTypesAdapter, quantitySuffixLabel, getApplicationContext()));
    }

    private void initializeListeners() {
        dueDateCalendar.setOnClickListener(new CalendarOnClickListener(getSupportFragmentManager(), currentMedicine));
        addMedicinePhotoBtn.setOnClickListener(new AddPhotoOnClickListener(this, currentMedicine, invokingClass));
        saveMedicineBtn.setOnClickListener(new SaveNewMedicineOnClickListener(currentMedicine, editingExistingMedicine, this));
        voiceInputMedicineNameBtn.setOnClickListener(new LaunchVoiceRecognitionOnClickListener(AppConstants.REQUEST_VOICE_INPUT_MEDICINE_NAME));
        voiceInputMedicineNotesBtn.setOnClickListener(new LaunchVoiceRecognitionOnClickListener(AppConstants.REQUEST_VOICE_INPUT_MEDICINE_NOTES));
    }

    private void setMedicineThumbnail(PhotoDescriptionTO photoDescriptionTO) {
        boolean arePhotosPhysicallyPresentOnDevice = PhotoUtils.arePhotosPhysicallyPresentOnDevice(photoDescriptionTO);

        if (arePhotosPhysicallyPresentOnDevice) {
            Bitmap thumbnailBitmap = PhotoUtils.prepareBitmap(photoDescriptionTO.getSmallSizePhotoUri(), addMedicinePhotoBtn);

            if (thumbnailBitmap != null) {
                addMedicinePhotoBtn.setImageBitmap(thumbnailBitmap);
            }
        }
    }

    private void updateMedicineTypeInSpinner(ArrayAdapter<MedicineTypeWithLocalizationTO> medicineTypesAdapter) {
        MedicineTypeWithLocalizationTO medicineTypeWithLocalizationTO = MedicineTypeUtils.prepareLocalizedTypeTO(currentMedicine.getType(), this);
        int position = medicineTypesAdapter.getPosition(medicineTypeWithLocalizationTO);

        medicineTypeSpinner.setSelection(position);
    }

    private String retrievePotentialMedicineNameFromVoiceRecognizedData(Intent data) {
        List<String> voiceRecognizedData = data
                .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

        String potentialMedicineName = voiceRecognizedData.get(0);
        return potentialMedicineName;
    }
}
