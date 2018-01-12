package lmnp.wirtualnaapteczka.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import lmnp.wirtualnaapteczka.R;
import lmnp.wirtualnaapteczka.fragments.ContactsTabFragment;
import lmnp.wirtualnaapteczka.fragments.InvitationsTabFragment;
import lmnp.wirtualnaapteczka.fragments.MedicinesFamilyListTabFragment;

public class FamilySectionsPagerAdapter extends FragmentPagerAdapter {

    private static final int FIRST_TAB_ID = 0;
    private static final int SECOND_TAB_ID = 1;
    private static final int THIRD_TAB_ID = 2;
    private static final int TOTAL_TABS_COUNT = 3;

    private Context context;

    public FamilySectionsPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment activeFragment;

        switch (position) {
            case FIRST_TAB_ID:
//                activeFragment = new MedicinesFamilyListTabFragment();
                activeFragment = null;
                break;
            case SECOND_TAB_ID:
                activeFragment = new ContactsTabFragment();
                break;
            case THIRD_TAB_ID:
                activeFragment = new InvitationsTabFragment();
                break;
            default:
                activeFragment = null;
        }

        return activeFragment;
    }

    @Override
    public int getCount() {
        return TOTAL_TABS_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String pageTitle = null;

        switch (position) {
            case FIRST_TAB_ID:
                pageTitle = context.getString(R.string.medicines_my_family);
                break;
            case SECOND_TAB_ID:
                pageTitle = context.getString(R.string.contacts);
                break;
            case THIRD_TAB_ID:
                pageTitle = context.getString(R.string.invitations);
                break;
        }

        return pageTitle;
    }


}