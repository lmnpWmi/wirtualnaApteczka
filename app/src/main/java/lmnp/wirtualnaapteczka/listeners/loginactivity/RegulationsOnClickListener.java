package lmnp.wirtualnaapteczka.listeners.loginactivity;

import android.content.Intent;
import android.view.View;
import lmnp.wirtualnaapteczka.activity.WebViewActivity;
import lmnp.wirtualnaapteczka.utils.AppConstants;

public class RegulationsOnClickListener implements View.OnClickListener {

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(v.getContext(), WebViewActivity.class);
        String fullLinkToViewRegulations = AppConstants.GOOGLE_DOCS_READER_URL + AppConstants.REGULATIONS_URL;
        intent.putExtra(AppConstants.URL_LINK, fullLinkToViewRegulations);

        v.getContext().startActivity(intent);
    }
}
