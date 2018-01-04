package lmnp.wirtualnaapteczka.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.webkit.WebView;
import lmnp.wirtualnaapteczka.R;
import lmnp.wirtualnaapteczka.utils.AppConstants;

/**
 * Activity for displaying websites.
 *
 * @author Sebastian Nowak
 * @createdAt 04.01.2018
 */
public class WebViewActivity extends AppCompatActivity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        String urlLink = getIntent().getStringExtra(AppConstants.URL_LINK);

        webView = (WebView) findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);

        if (!TextUtils.isEmpty(urlLink))
            webView.loadUrl(urlLink);
    }
}
