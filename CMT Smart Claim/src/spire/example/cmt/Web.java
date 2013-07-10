package spire.example.cmt;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Web extends Activity {
	WebView mWebView;
	public static String uri;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.web);
		mWebView = (WebView) findViewById(R.id.webView);
		    mWebView.getSettings().setJavaScriptEnabled(true);
		    mWebView.setWebViewClient(new HelloWebViewClient());	
		    mWebView.loadUrl(uri); 
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
			mWebView.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);			
	}
}
 class HelloWebViewClient extends WebViewClient 
{
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) 
    {
        view.loadUrl(url);
        return true;
    }
}