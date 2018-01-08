package lmnp.wirtualnaapteczka.listeners.common;

import android.app.AlertDialog;
import android.view.View;
import lmnp.wirtualnaapteczka.data.enums.SortingComparatorTypeEnum;
import lmnp.wirtualnaapteczka.services.DbService;
import lmnp.wirtualnaapteczka.session.SessionManager;

/**
 * Sets chosen comparator in the Shared Preferences.
 *
 * @author Sebastian Nowak
 * @createdAt 29.12.2017
 */
public class SaveDefaultSortingComparatorOnClickListener implements View.OnClickListener {
    private SortingComparatorTypeEnum sortingComparatorTypeEnum;
    private AlertDialog sortListDialog;

    public SaveDefaultSortingComparatorOnClickListener(SortingComparatorTypeEnum sortingComparatorTypeEnum, AlertDialog sortListDialog) {
        this.sortingComparatorTypeEnum = sortingComparatorTypeEnum;
        this.sortListDialog = sortListDialog;
    }

    @Override
    public void onClick(View v) {
        DbService dbService = SessionManager.getDbService();
        dbService.updateDefaultComparatorInUserPreferences(sortingComparatorTypeEnum);

        sortListDialog.dismiss();
    }
}
