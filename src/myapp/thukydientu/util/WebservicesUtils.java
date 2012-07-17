package myapp.thukydientu.util;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import myapp.thukydientu.database.ScheduleTable;
import myapp.thukydientu.database.TodoTable;
import myapp.thukydientu.model.IConstants;
import myapp.thukydientu.model.MyFile;
import myapp.thukydientu.model.Schedule;
import myapp.thukydientu.model.Todo;
import myapp.thukydientu.model.IConstants.ShareLocation;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.util.Log;

public class WebservicesUtils {

	private static final String NAMESPACE = "http://tempuri.org/";
	private static final String WEB_SERVICES_URL = "http://kanzenvietnam.co.cc/tester/weservice/server.php";

	// query constants
	private static final String REGISTER_METHOD = "register";
	private static final String LOGIN_METHOD = "checkLogin";
	private static final String CHANGE_PASS_METHOD = "changePass";
	private static final String UPDATE_FILE_METHOD = "updateFile";
	private static final String GET_FILE_METHOD = "getFile";
	private static final String DEL_FILE_METHOD = "delFile";
	private static final String GET_TODO_METHOD = "getTodo";
	private static final String ADD_TODO_METHOD = "addTodo";
	private static final String SYNC_TODO_METHOD = "sync_todo_app";
	private static final String UPDATE_TODO_METHOD = "updateTodo";
	private static final String DEL_TODO_METHOD = "delTodo";
	private static final String GET_SCHEDULE_MEDHOD = "getSchedule";
	private static final String ADD_SCHEDULE_METHOD = "addSchedule";
	private static final String SYNC_SCHEDULE_METHOD = "sync_schedule_app";
	private static final String UPDATE_SCHEDULE_METHOD = "updateSchedule";
	private static final String SYNC_METHOD = "sync_web_service";
	private static final String SHARE_LOCATION_METHOD = "addLatitude";
	private static final String REGISTER_ACTION = "http://tempuri.org/register";
	private static final String LOGIN_ACTION = "http://tempuri.org/checkLogin";
	private static final String CHANGE_PASS_ACTION = "http://tempuri.org/changePass";
	private static final String UPDATE_FILE_ACTION = "http://tempuri.org/updateFile";
	private static final String GET_FILE_ACTION = "http://tempuri.org/getFile";
	private static final String DEL_FILE_ACTION = "http://tempuri.org/delFile";
	private static final String GET_TODO_ACTION = "http://tempuri.org/getTodo";
	private static final String ADD_TODO_ACTION = "http://tempuri.org/addTodo";
	private static final String SYNC_TODO_ACTION = "http://tempuri.org/sync_todo_app";
	private static final String UPDATE_TODO_ACTION = "http://tempuri.org/updateTodo";
	private static final String DEL_TODO_ACTION = "http://tempuri.org/delTodo";
	private static final String GET_SCHEDULE_ACTION = "http://tempuri.org/getSchedule";
	private static final String ADD_SCHEDULE_ACTION = "http://tempuri.org/addSchedule";
	private static final String SYNC_SCHEDULE_ACTION = "http://tempuri.org/sync_schedule_app";
	private static final String UPDATE_SCHEDULE_ACTION = "http://tempuri.org/updateSchedule";
	private static final String SYNC_ACTION = "http://tempuri.org/sync_web_service";
	private static final String SHARE_LOCATION_ACTION = "http://tempuri.org/addLatitude";

	private static String callWebServices(String action, SoapObject request) {

		String result = "";

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = false;
		envelope.setOutputSoapObject(request);
		envelope.encodingStyle = "UTF-8";

		HttpTransportSE androidHttpTransport = new HttpTransportSE(
				WEB_SERVICES_URL);
		androidHttpTransport.debug = true;

		try {
			androidHttpTransport.call(action, envelope);
			result = (String) envelope.getResponse();
		} catch (Exception e) {
			e.printStackTrace();
			result = "";
		}
		Log.d("call web service", "action: " + action + "\nresult" + result);
		return result;
	}

	public static String register(String username, String password,
			String fullname, String email, String phone, String address,
			String datebirth, int gender, String workplace, String job) {

		// make request
		SoapObject request = new SoapObject(NAMESPACE, REGISTER_METHOD);
		request.addProperty(IConstants.User.USER_NAME, username);
		request.addProperty(IConstants.User.PASSWORD, password);
		request.addProperty(IConstants.User.FULLNAME, fullname);
		request.addProperty(IConstants.User.EMAIL, email);
		request.addProperty(IConstants.User.PHONE_NUMBER, phone);
		request.addProperty(IConstants.User.ADDRESS, address);
		request.addProperty(IConstants.User.DATE_OF_BIRTH, datebirth);
		request.addProperty(IConstants.User.GENDER, gender);
		request.addProperty(IConstants.User.WORKPLACE, workplace);
		request.addProperty(IConstants.User.JOB, job);

		return callWebServices(REGISTER_ACTION, request);
	}

	public static String login(String username, String password) {

		// make request
		SoapObject request = new SoapObject(NAMESPACE, LOGIN_METHOD);
		request.addProperty(IConstants.User.USER_NAME, username);
		request.addProperty(IConstants.User.PASSWORD, password);

		return callWebServices(LOGIN_ACTION, request);
	}

	public static String changePassword(int userId, String oldPassword,
			String newPassword) {

		// make request
		SoapObject request = new SoapObject(NAMESPACE, CHANGE_PASS_METHOD);
		request.addProperty(IConstants.User.ID, userId);
		request.addProperty("old_password", oldPassword);
		request.addProperty("new_password", newPassword);

		return callWebServices(CHANGE_PASS_ACTION, request);
	}

	public static String updateFile(int userId, int fileId, String fileName,
			String description, String category, String type, boolean isprivate) {

		// make request
		SoapObject request = new SoapObject(NAMESPACE, UPDATE_FILE_METHOD);
		request.addProperty(IConstants.User.ID, userId);
		request.addProperty(IConstants.File.ID, fileId);
		request.addProperty(IConstants.File.NAME, fileName);
		request.addProperty(IConstants.File.DESCRIPTION, description);
		request.addProperty(IConstants.File.CATEGORY, category);
		request.addProperty(IConstants.File.FORMAT, type);
		request.addProperty(IConstants.File.MODE, isprivate);

		return callWebServices(UPDATE_FILE_ACTION, request);
	}

	public static String addLocation(int userId, double latitude,
			double longitude, boolean isPrivate, String created) {

		Log.d("addLocation", ShareLocation.USER_ID + ":" + userId);
		Log.d("addLocation", ShareLocation.LATITUDE + ":" + latitude);
		Log.d("addLocation", ShareLocation.LONGITUDE + ":" + longitude);
		Log.d("addLocation", ShareLocation.MODE + ":" + (isPrivate ? 1 : 0));
		Log.d("addLocation", ShareLocation.CREATED + ":" + created);
		// make request
		SoapObject request = new SoapObject(NAMESPACE, SHARE_LOCATION_METHOD);
		request.addProperty(ShareLocation.USER_ID, userId);
		request.addProperty(ShareLocation.LATITUDE, String.valueOf(latitude));
		request.addProperty(ShareLocation.LONGITUDE, String.valueOf(longitude));
		request.addProperty(ShareLocation.MODE, isPrivate ? 1 : 0);
		request.addProperty(ShareLocation.CREATED, created);

		return callWebServices(SHARE_LOCATION_ACTION, request);
	}

	public static String getFile(int userId, int fileId) {

		// make request
		SoapObject request = new SoapObject(NAMESPACE, GET_FILE_METHOD);
		request.addProperty(IConstants.User.ID, userId);
		request.addProperty(IConstants.File.ID, fileId);

		return callWebServices(GET_FILE_ACTION, request);
	}

	public static String delFile(int userId, int fileId) {

		// make request
		SoapObject request = new SoapObject(NAMESPACE, DEL_FILE_METHOD);
		request.addProperty(IConstants.User.ID, userId);
		request.addProperty(IConstants.File.ID, fileId);

		return callWebServices(DEL_FILE_ACTION, request);
	}

	public static void addFile(int userId, MyFile file)
			throws UnsupportedEncodingException {
		HttpUriRequest request = new HttpPost(
				"http://kanzenvietnam.co.cc/tester/upload.php");
		MultipartEntity form = new MultipartEntity();
		// disable expect-continue handshake (lighttpd doesn't support it)
		HttpClient client = new DefaultHttpClient();
		client.getParams().setBooleanParameter("http.protocol.expect-continue",
				false);
		final String description = file.getDescription() == null ? "" : file
				.getDescription();
		final String category = file.getCategory() == null ? "" : file
				.getCategory();
		final String isPrivate = file.getIsPrivate() == null ? "" : file
				.getIsPrivate();
		form.addPart(IConstants.User.ID, new StringBody(userId + ""));
		form.addPart(IConstants.File.DESCRIPTION, new StringBody(description));
		form.addPart("category", new StringBody(category));
		form.addPart("isprivate", new StringBody(isPrivate));
		form.addPart("upload", new FileBody(new File(file.getLink())));
		((HttpEntityEnclosingRequestBase) request).setEntity(form);
		try {
			client.execute(request);
		} catch (ClientProtocolException e) {
		} catch (IOException ee) {
		}
	}

	public static String getTodo(int userId, int todoId) {
		// make request
		SoapObject request = new SoapObject(NAMESPACE, GET_TODO_METHOD);
		request.addProperty(IConstants.User.ID, userId);
		request.addProperty(IConstants.WebServices.TODO_ID, todoId);

		return callWebServices(GET_TODO_ACTION, request);
	}

	public static String addTodo(int userId, String dateStart, String dateEnd,
			String timeFrom, String timeUntil, String title, String work,
			int alarm, String dateSet, String modified) {
		// make request
		SoapObject request = new SoapObject(NAMESPACE, ADD_TODO_METHOD);
		request.addProperty(IConstants.User.ID, userId);
		request.addProperty(TodoTable.DATE_START, dateStart);
		request.addProperty(TodoTable.DATE_END, dateEnd);
		request.addProperty(TodoTable.TIME_FROM, timeFrom);
		request.addProperty(TodoTable.TIME_UNTIL, timeUntil);
		request.addProperty(TodoTable.TITLE, title);
		request.addProperty(TodoTable.WORK, work);
		request.addProperty(TodoTable.ALARM, alarm);
		request.addProperty(TodoTable.DATE_SET, dateSet);
		request.addProperty(TodoTable.MODIFIED, modified);

		return callWebServices(ADD_TODO_ACTION, request);
	}

	public static String sync_schedule_app(int userId, Schedule schedule) {
		Log.d("sync_schedule_app", "userId: " + userId);
		Log.d("sync_schedule_app", "dateName: " + schedule.getDayName());
		Log.d("sync_schedule_app", "time: " + schedule.getTime());
		Log.d("sync_schedule_app", "subject: " + schedule.getSubject());
		Log.d("sync_schedule_app", "class: " + schedule.getClassName());
		Log.d("sync_schedule_app", "school: " + schedule.getSchoolName());
		Log.d("sync_schedule_app", "dateSet: " + schedule.getDateSet());
		Log.d("sync_schedule_app", "modified: " + schedule.getModified());
		Log.d("sync_schedule_app", "deleted: " + schedule.getDeleted());
		Log.d("sync_schedule_app", "schanged: " + schedule.getChanged());

		SoapObject request = new SoapObject(NAMESPACE, SYNC_SCHEDULE_METHOD);

		request.addProperty(IConstants.User.ID, userId);
		request.addProperty(ScheduleTable.DATE_NAME, schedule.getDayName());
		request.addProperty(ScheduleTable.TIME, schedule.getTime());
		request.addProperty(ScheduleTable.SUBJECT, schedule.getSubject());
		request.addProperty(ScheduleTable.CLASS, schedule.getClassName());
		request.addProperty(ScheduleTable.SCHOOL, schedule.getSchoolName());
		request.addProperty(ScheduleTable.CREATED, schedule.getDateSet());
		request.addProperty(ScheduleTable.MODIFIED, schedule.getModified());
		request.addProperty(ScheduleTable.DELETED, schedule.getDeleted());
		request.addProperty(ScheduleTable.CHANGED, schedule.getChanged());

		return callWebServices(SYNC_SCHEDULE_ACTION, request);
	}

	public static String sync_todo_app(int userId, Todo todo) {

		Log.d("sync_todo_app", "userId: " + userId);
		Log.d("sync_todo_app", "dateStart: " + todo.getDateStart());
		Log.d("sync_todo_app", "dateEnd: " + todo.getDateEnd());
		Log.d("sync_todo_app", "timeFrom: " + todo.getTimeFrom());
		Log.d("sync_todo_app", "timeUntil: " + todo.getTimeUntil());
		Log.d("sync_todo_app", "title: " + todo.getTitle());
		Log.d("sync_todo_app", "work: " + todo.getWork());
		Log.d("sync_todo_app", "alarm: " + todo.getAlarm());
		Log.d("sync_todo_app", "dateSet: " + todo.getDateSet());
		Log.d("sync_todo_app", "modified: " + todo.getModified());
		Log.d("sync_todo_app", "changed: " + todo.getChanged());
		Log.d("sync_todo_app", "deleted: " + todo.getDeleted());

		SoapObject request = new SoapObject(NAMESPACE, SYNC_TODO_METHOD);

		request.addProperty(IConstants.User.ID, userId);
		request.addProperty(TodoTable.DATE_START, todo.getDateStart());
		request.addProperty(TodoTable.DATE_END, todo.getDateEnd());
		request.addProperty(TodoTable.TIME_FROM, todo.getTimeFrom());
		request.addProperty(TodoTable.TIME_UNTIL, todo.getTimeUntil());
		request.addProperty(TodoTable.TITLE, todo.getTitle());
		request.addProperty(TodoTable.WORK, todo.getWork());
		request.addProperty(TodoTable.ALARM, todo.getAlarm());
		request.addProperty(TodoTable.DATE_SET, todo.getDateSet());
		request.addProperty(TodoTable.MODIFIED, todo.getModified());
		request.addProperty(TodoTable.CHANGED, todo.getChanged());
		request.addProperty(TodoTable.DELETED, todo.getDeleted());

		return callWebServices(SYNC_TODO_ACTION, request);
	}

	public static String delTodo(int userId, int todoId) {
		// make request
		SoapObject request = new SoapObject(NAMESPACE, DEL_TODO_METHOD);
		request.addProperty(IConstants.User.ID, userId);
		request.addProperty(IConstants.WebServices.TODO_ID, todoId);

		return callWebServices(DEL_TODO_ACTION, request);
	}

	public static String updateTodo(int userId, int todoId, String dateStart,
			String dateEnd, String timeFrom, String timeUntil, String title,
			String work, int alarm, String dateSet, String modified) {
		// make request
		SoapObject request = new SoapObject(NAMESPACE, UPDATE_TODO_METHOD);
		request.addProperty(IConstants.User.ID, userId);
		request.addProperty(IConstants.WebServices.TODO_ID, todoId);
		request.addProperty(TodoTable.DATE_START, dateStart);
		request.addProperty(TodoTable.DATE_END, dateEnd);
		request.addProperty(TodoTable.TIME_FROM, timeFrom);
		request.addProperty(TodoTable.TIME_UNTIL, timeUntil);
		request.addProperty(TodoTable.TITLE, title);
		request.addProperty(TodoTable.WORK, work);
		request.addProperty(TodoTable.ALARM, alarm);
		request.addProperty(TodoTable.DATE_SET, dateSet);
		request.addProperty(TodoTable.MODIFIED, modified);

		return callWebServices(UPDATE_TODO_ACTION, request);
	}

	public static String getSchedule(int userId, int scheduleId) {
		// make request
		SoapObject request = new SoapObject(NAMESPACE, GET_SCHEDULE_MEDHOD);
		request.addProperty(IConstants.User.ID, userId);
		request.addProperty(IConstants.WebServices.SCHEDULE_ID, scheduleId);

		return callWebServices(GET_SCHEDULE_ACTION, request);
	}

	public static String addSchedule(int userId, int dateName, String time,
			String subject, String className, String school) {
		// make request
		SoapObject request = new SoapObject(NAMESPACE, ADD_SCHEDULE_METHOD);
		request.addProperty(IConstants.User.ID, userId);
		request.addProperty(ScheduleTable.DATE_NAME, dateName);
		request.addProperty(ScheduleTable.TIME, time);
		request.addProperty(ScheduleTable.SUBJECT, subject);
		request.addProperty(ScheduleTable.CLASS, className);
		request.addProperty(ScheduleTable.SCHOOL, school);

		return callWebServices(ADD_SCHEDULE_ACTION, request);
	}

	public static String updateSchedule(int userId, int id, int datename,
			String time, String subject, String className, String school) {
		// make request
		SoapObject request = new SoapObject(NAMESPACE, UPDATE_SCHEDULE_METHOD);
		request.addProperty(IConstants.User.ID, userId);
		request.addProperty(IConstants.WebServices.SCHEDULE_ID, id);
		request.addProperty(ScheduleTable.DATE_NAME, datename);
		request.addProperty(ScheduleTable.TIME, time);
		request.addProperty(ScheduleTable.SUBJECT, subject);
		request.addProperty(ScheduleTable.SCHOOL, school);

		return callWebServices(UPDATE_SCHEDULE_ACTION, request);
	}

	public static String sync(int userId, String tablename) {
		// make request
		SoapObject request = new SoapObject(NAMESPACE, SYNC_METHOD);
		request.addProperty(IConstants.User.ID, userId);
		request.addProperty(IConstants.WebServices.TABLE_NAME, tablename);

		return callWebServices(SYNC_ACTION, request);
	}
}
