package lmnp.wirtualnaapteczka.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import lmnp.wirtualnaapteczka.R;
import lmnp.wirtualnaapteczka.adapters.FamilySectionsPagerAdapter;
import lmnp.wirtualnaapteczka.fragments.MedicinesFamilyListTabFragment;
import lmnp.wirtualnaapteczka.utils.AppConstants;
import lmnp.wirtualnaapteczka.utils.VoiceRecognitionUtils;

/**
 * Activity responsible for sharing your medicines with your family and managing your family members.
 *
 * @author Sebastian Nowak
 * @createdAt 10.01.2018
 */
public class FamilyActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family);

        FamilySectionsPagerAdapter mFamilySectionsPagerAdapter = new FamilySectionsPagerAdapter(getSupportFragmentManager(), this);

        ViewPager mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mFamilySectionsPagerAdapter);
        mViewPager.setOffscreenPageLimit(AppConstants.OFF_PAGE_SCREEN_LIMIT);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && data != null) {
            if (requestCode == AppConstants.REQUEST_VOICE_INPUT_MEDICINE_NAME && MedicinesFamilyListTabFragment.searchMedicineDialog != null) {
                VoiceRecognitionUtils.updateRetrievedNameInSearchDialog(MedicinesFamilyListTabFragment.searchMedicineDialog, data);
            }
        }
    }
}
