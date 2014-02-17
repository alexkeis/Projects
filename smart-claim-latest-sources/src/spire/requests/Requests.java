package spire.requests;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;


public class Requests {
	public static final String URL_MY_PROFILE = "http://service.cmt.net.au/ClaimsDataService.svc/SaveClientObject";
	// public static final String URL_TEST =
	// "http://192.168.1.64/test_web/index.php";
	public static final String LANGUAGES = "languages";
	public static final String STUDENT = "student?";
	public static final String TOP10 = "top10/";
	public static final String NEWS = "news/";
	public static final String CHECK_IN = "check_in/";
	public static final String SET_SETTINGS = "set_settings?";
	public static final String SETTINGS = "settings/";
	public static final String SCHOOL_INFO = "school/";
	public static String LastRequestStatus = "";
	public static final String DEFAULT_STUDENT_ID = "158";
	public String current_language, message;
	private static Requests instance;
	private Context ctx;
	private Handler handler;

	private Requests() {
 
	}

	public static Requests getInstance() {
		if (instance == null) {
			instance = new Requests();
		}
		return instance;
	}

	public Context getContext() {
		return ctx;
	}

	public void setContext(Context ctx) {
		this.ctx = ctx;
	}

	public void setTextForMessage(String mes) {
		message = mes;
	}

	public String send(String url,String params) {
		String xml = null;

		try {

			HttpParams httpParameters = new BasicHttpParams();
			int timeoutConnection = 5000;
			HttpConnectionParams.setConnectionTimeout(httpParameters,
					timeoutConnection);
			int timeoutSocket = 5000;
			HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
			DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);
			
			HttpResponse httpResponse;

				HttpPost httpPost = new HttpPost(url);
				httpPost.setHeader("Content-Type", "application/json");
				httpPost.setHeader("Accept", "application/json");
				Log.d("zzz", "post " + params);
				StringEntity se = new StringEntity(params, "UTF-8");
			
				httpPost.setEntity(se);

				httpResponse = httpClient.execute(httpPost);
			
			HttpEntity httpEntity = httpResponse.getEntity();
			xml = EntityUtils.toString(httpEntity, "utf-8");
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();

		}
		Log.d("zzz", "XML " + xml);
		return xml;
	}
	
	public void setProfile( ) {
		
	}
	
	
	
	/*public void getLanguages() {
		XMLParser parser = new XMLParser();
		String xml = parser.getXmlFromUrl(URL_ROOT + LANGUAGES, false, "");
		if (xml == null) {
			Toast.makeText(ctx, message, Toast.LENGTH_LONG).show();
			return;
		}
		Document doc = parser.getDomElement(xml);
		NodeList nl = doc.getElementsByTagName("item");
		for (int i = 0; i < nl.getLength(); i++) {
			Element e = (Element) nl.item(i);
			if (parser.getValue(e, "is_default").equals("true")) {
				Language lang = new Language(parser.getValue(e, "id"),
						parser.getValue(e, "name"), parser.getValue(e, "code"));
				Cache.getInstance().setCurrentLanguage(lang);
				break;
			}
		}
		Log.d("zzzz", "current_language "
				+ Cache.getInstance().getCurrentLanguage());
	}

	public String getStudentId(String email, String school_id) {
		XMLParser parser = new XMLParser();
		ArrayList<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
		params.add(new BasicNameValuePair("fb", email));
		params.add(new BasicNameValuePair("sid", school_id));
		String xml = parser.getXmlFromUrl(
				parser.addParamsToUrl(URL_ROOT + STUDENT, params), false, "");
		if (xml == null) {
			Toast.makeText(ctx, message, Toast.LENGTH_LONG).show();
			return "0";
		}
		Document doc = parser.getDomElement(xml);
		NodeList nl = doc.getElementsByTagName("student");

		Element e = (Element) nl.item(0);
		LastRequestStatus = parser.getValue(e, "status");
		Log.d("zzzz", "getStudentId " + Cache.getInstance().getStudentId()
				+ " " + LastRequestStatus);
		return parser.getValue(e, "id");

	}

	public void getTop10() {
		ArrayList<Top10> top10List = new ArrayList<Top10>();
		XMLParser parser = new XMLParser();
		String xml;
		if (!Cache.getInstance().getStudentId().equals("0"))
			xml = parser.getXmlFromUrl(URL_ROOT + TOP10
					+ Cache.getInstance().getStudentId(), false, "");
		else
			xml = parser.getXmlFromUrl(URL_ROOT + TOP10 + DEFAULT_STUDENT_ID,
					false, "");
		if (xml == null) {
			Toast.makeText(ctx, message, Toast.LENGTH_LONG).show();
			return;
		}
		Document doc = parser.getDomElement(xml);
		NodeList nl = doc.getElementsByTagName("item");
		for (int i = 0; i < nl.getLength(); i++) {
			Element e = (Element) nl.item(i);
			Top10 top10 = new Top10(parser.getValue(e, "student_id"),
					parser.getValue(e, "place"), parser.getValue(e, "points"),
					parser.getValue(e, "first_name"), parser.getValue(e,
							"last_name"), parser.getValue(e,
							"facebook_username"), parser.getValue(e,
							"facebook_username"));
			top10List.add(top10);
		}
		Cache.getInstance().setListTop10(top10List);
		Log.d("zzzz", "top10List " + top10List.toString());
	}

	public void getNews() {
		if (Cache.getInstance().getStudentId().equals("0"))
			return;
		ArrayList<News> newsList = new ArrayList<News>();
		XMLParser parser = new XMLParser();
		String xml = parser.getXmlFromUrl(URL_ROOT + NEWS
				+ Cache.getInstance().getStudentId(), false, "");
		if (xml == null) {
			Toast.makeText(ctx, message, Toast.LENGTH_LONG).show();
			return;
		}
		Document doc = parser.getDomElement(xml);
		NodeList nl;
		nl = doc.getElementsByTagName("school_news");
		Element e = (Element) nl.item(0);
		LastRequestStatus = parser.getValue(e, "status");
		if (LastRequestStatus.equals("0")){
		nl = doc.getElementsByTagName("item");
		for (int i = 0; i < nl.getLength(); i++) {   	
            e = (Element) nl.item(i);  
            String url = Cache.getInstance().parseUrl(parser.getValue(e, "text"));
            String text = Cache.getInstance().parseText(parser.getValue(e, "text"));
            //Log.d("zzzz","url "+url);
           	 News news = new News(parser.getValue(e, "id"), parser.getValue(e, "title"), text, url, parser.getValue(e, "published_on"));
            newsList.add(news);	        	               
        }

		}
		Cache.getInstance().setListNews(newsList);

	}

	public void getSchoolInfo() {

		XMLParser parser = new XMLParser();
		String xml;
		if (!Cache.getInstance().getStudentId().equals("0"))
			xml = parser.getXmlFromUrl(URL_ROOT + SCHOOL_INFO
					+ Cache.getInstance().getStudentId(), false, "");
		else
			xml = parser.getXmlFromUrl(URL_ROOT + SCHOOL_INFO
					+ DEFAULT_STUDENT_ID, false, "");
		if (xml == null) {
			Toast.makeText(ctx, message, Toast.LENGTH_LONG).show();
			return;
		}
		Document doc = parser.getDomElement(xml);
		NodeList nl = doc.getElementsByTagName("school_info");
		Element e = (Element) nl.item(0);
		NodeList nl1 = doc.getElementsByTagName("class");
		Element e1 = (Element) nl1.item(0);
		SchoolInfo si = new SchoolInfo(parser.getValue(e, "id"),
				parser.getValue(e, "school_name"), parser.getValue(e,
						"school_display_name"), parser.getValue(e,
						"school_description"), parser.getValue(e,
						"timetable_url"), parser.getValue(e, "address_line_1"),
				parser.getValue(e, "address_line_2"), parser.getValue(e,
						"telephone"), parser.getValue(e, "longitude"),
				parser.getValue(e, "latitude"), parser.getValue(e,
						"facebook_place_id"), parser.getValue(e,
						"facebook_checkin_message"), parser.getValue(e1, "id"),
				parser.getValue(e1, "name"));
		LastRequestStatus = parser.getValue(e, "status");
		Log.d("zzzz", "SchoolInfo " + si.toString());
		Cache.getInstance().setSchoolInfo(si);

	}

	public String makeCheckIn() {

		XMLParser parser = new XMLParser();
		String xml = parser.getXmlFromUrl(URL_ROOT + CHECK_IN
				+ Cache.getInstance().getStudentId(), false, "");
		if (xml == null) {
			Toast.makeText(ctx, message, Toast.LENGTH_LONG).show();
			return "1";
		}
		Document doc = parser.getDomElement(xml);
		NodeList nl = doc.getElementsByTagName("result");

		Element e = (Element) nl.item(0);
		String status = parser.getValue(e, "status");
		Log.d("zzz", "status " + status);
		return status;
	}

	public String setSettings(String firstName, String lastName, String email) {

		XMLParser parser = new XMLParser();
		List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
		params.add(new BasicNameValuePair("status", "0"));
		params.add(new BasicNameValuePair("id", Cache.getInstance()
				.getStudentId()));
		params.add(new BasicNameValuePair("first_name", firstName));
		params.add(new BasicNameValuePair("last_name", lastName));
		params.add(new BasicNameValuePair("email", email));
		params.add(new BasicNameValuePair("facebook_username", email));
		params.add(new BasicNameValuePair("facebook_password", ""));
		params.add(new BasicNameValuePair("language_id", Cache.getInstance()
				.getCurrentLanguage().id));
		params.add(new BasicNameValuePair("device_token", ""));

		params.add(new BasicNameValuePair("class_id", Cache.getInstance()
				.getSchoolInfo().classId));
		params.add(new BasicNameValuePair("school_id", Cache.getInstance()
				.getSchoolInfo().id));
		params.add(new BasicNameValuePair("gcm_id", Cache.gcm_id));

		// String xml =
		// parser.getXmlFromUrl(parser.addParamsToUrl(URL_ROOT+SETTINGS,
		// params),false, "");
		String xml = parser.getXmlFromUrl(URL_ROOT + SET_SETTINGS, true,
				parser.getXMLParams("settings", params));
		if (xml == null) {
			Toast.makeText(ctx, message, Toast.LENGTH_LONG).show();
			return "";
		}
		Log.d("zzzz", "setSettings XML " + xml);
		Document doc = parser.getDomElement(xml);
		NodeList nl = doc.getElementsByTagName("settings");
		Element e = (Element) nl.item(0);
		LastRequestStatus = parser.getValue(e, "status");
		if (LastRequestStatus.equals("0")) {
			StudentSettings settings = new StudentSettings(parser.getValue(e,
					"id"), parser.getValue(e, "first_name"), parser.getValue(e,
					"last_name"), parser.getValue(e, "email"), parser.getValue(
					e, "facebook_username"), parser.getValue(e,
					"facebook_password"), parser.getValue(e, "language_id"),
					parser.getValue(e, "class_id"), parser.getValue(e,
							"device_token"), parser.getValue(e, "school_id"),
					parser.getValue(e, "app_name"), parser.getValue(e,
							"app_version"), parser.getValue(e, "device_uid"),
					parser.getValue(e, "device_name"), parser.getValue(e,
							"device_model"), parser.getValue(e,
							"device_version"),
					parser.getValue(e, "push_badge"), parser.getValue(e,
							"push_alert"), parser.getValue(e, "push_sound"));
			Cache.getInstance().setStudentSettings(settings);
			Cache.getInstance().setStudentId(parser.getValue(e, "id"));
			Log.d("zzz", "setSettings " + settings.toString());
		}
		return LastRequestStatus;
	}

	public void getSettings() {

		XMLParser parser = new XMLParser();
		String xml;
		if (!Cache.getInstance().getStudentId().equals("0"))
			xml = parser.getXmlFromUrl(URL_ROOT + SETTINGS
					+ Cache.getInstance().getStudentId(), false, "");
		else
			xml = parser.getXmlFromUrl(
					URL_ROOT + SETTINGS + DEFAULT_STUDENT_ID, false, "");
		if (xml == null) {
			Toast.makeText(ctx, message, Toast.LENGTH_LONG).show();
			return;
		}
		Log.d("zzzz", "getSettings XML " + xml);
		Document doc = parser.getDomElement(xml);
		NodeList nl = doc.getElementsByTagName("settings");
		Element e = (Element) nl.item(0);
		LastRequestStatus = parser.getValue(e, "status");
		if (LastRequestStatus.equals("0")) {
			StudentSettings settings = new StudentSettings(parser.getValue(e,
					"id"), parser.getValue(e, "first_name"), parser.getValue(e,
					"last_name"), parser.getValue(e, "email"), parser.getValue(
					e, "facebook_username"), parser.getValue(e,
					"facebook_password"), parser.getValue(e, "language_id"),
					parser.getValue(e, "class_id"), parser.getValue(e,
							"device_token"), parser.getValue(e, "school_id"),
					parser.getValue(e, "app_name"), parser.getValue(e,
							"app_version"), parser.getValue(e, "device_uid"),
					parser.getValue(e, "device_name"), parser.getValue(e,
							"device_model"), parser.getValue(e,
							"device_version"),
					parser.getValue(e, "push_badge"), parser.getValue(e,
							"push_alert"), parser.getValue(e, "push_sound"));
			Cache.getInstance().setStudentSettings(settings);
			Log.d("zzzz", "getSettings " + settings.toString());

		}
	}

	public void showMessage() {
		handler.post(new Runnable() {

			@Override
			public void run() {
				Toast.makeText(ctx, "Besteht keine Verbindung zum Server",
						Toast.LENGTH_LONG).show();

			}
		});

	}

	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}*/
}
