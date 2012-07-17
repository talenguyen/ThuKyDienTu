package myapp.thukydientu.view;

import myapp.thukydientu.R;
import myapp.thukydientu.model.IConstants;
import myapp.thukydientu.util.FileUtils;
import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends TabActivity {
	
    public static final String TAB_SCHEDULE_ID = "TAB_SCHEDULE";
    public static final String TAB_TODO_ID = "TAB_TODO";
    public static final String TAB_FILE_MANAGER_ID = "TAB_FILE_MANAGER";
    
    public static String sFilePath = "";
    public static MainActivity sInstance;
    
    public ImageView scheduleSync;
    public ImageView todoSync;
	
    private LocalBroadcastManager mLocalBroadcastManager;
    private BroadcastReceiver mReceiver;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    
        
        setContentView(R.layout.main);
        
        sInstance = this;
        
        createTabLayout();
        
        /*******************************************/
        /*	Broadcast Receiver		   */
        /*******************************************/
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);
        IntentFilter filter = new IntentFilter();
        filter.addAction(IConstants.Service.DOWNLOAD_ACTION_STARTED);
        filter.addAction(IConstants.Service.DOWNLOAD_ACTION_FINISHED);
        filter.addAction(IConstants.Service.DOWNLOAD_ACTION_CANCELLED);
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(final Context context, Intent intent) {
            	if(intent.getAction().equals(IConstants.Service.DOWNLOAD_ACTION_STARTED)){
            		Toast.makeText(context, "Download started!", Toast.LENGTH_SHORT).show();
            	}
            	if(intent.getAction().equals(IConstants.Service.DOWNLOAD_ACTION_FINISHED)){
            		Toast.makeText(context, "Download finished!", Toast.LENGTH_SHORT).show();
            		Log.d("status","Download finished");           		
            		AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            		builder.setTitle("Thành Công!")
            				.setMessage("Đã tải thành công tài liệu: " + sFilePath)
            				.setNeutralButton("Mở", new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which) {
									FileUtils.openFile(MainActivity.this, sFilePath);
								}
							})
							.create()
							.show();
            	}
            	if(intent.getAction().equals(IConstants.Service.DOWNLOAD_ACTION_CANCELLED)){
            		Toast.makeText(context, "Download cancelled!", Toast.LENGTH_SHORT).show();
            		Log.d("status","Download cancelled");           		
            	}
            }
        };
        mLocalBroadcastManager.registerReceiver(mReceiver, filter);
    }
    
    public void createTabLayout()
    {
	    final TabHost tabHost = getTabHost();  // The activity TabHost
	    TabHost.TabSpec spec;  // Resusable TabSpec for each tab
	    Intent intent;  // Reusable Intent for each tab

	    // Create an Intent to launch an Activity for the tab (to be reused)

	    View schedule = getLayoutInflater().inflate(R.layout.tab_schedule, null);
	    scheduleSync = (ImageView) schedule.findViewById(R.id.sync);
	    
	    // Do the same for the other tabs
	    intent = new Intent().setClass(this, ScheduleListActivity.class);
	    spec = tabHost.newTabSpec(TAB_SCHEDULE_ID).setIndicator(schedule)
	                  .setContent(intent);
	    tabHost.addTab(spec);

	    View todo = getLayoutInflater().inflate(R.layout.tab_todo, null);
	    todoSync = (ImageView) todo.findViewById(R.id.sync);
	    
	    intent = new Intent().setClass(this, TodoListActivity.class);
	    spec = tabHost.newTabSpec(TAB_TODO_ID).setIndicator(todo)
	                  .setContent(intent);
	    tabHost.addTab(spec);
	    
	    View file = getLayoutInflater().inflate(R.layout.tab_file_manager, null);
	    intent = new Intent().setClass(this, FileManagerActivity.class);
	    spec = tabHost.newTabSpec(TAB_FILE_MANAGER_ID).setIndicator(file)
	                  .setContent(intent);
	    tabHost.addTab(spec);

	    tabHost.setCurrentTabByTag(TAB_SCHEDULE_ID);
	    
	    tabHost.setOnTabChangedListener(new OnTabChangeListener() {
	        
	        @Override
	        public void onTabChanged(String tabId) {
	            TextView title = (TextView) findViewById(R.id.title);
	            
	            if (tabId.equals(TAB_SCHEDULE_ID)) 
	        	title.setText(R.string.schedule);
	            
	            if (tabId.equals(TAB_TODO_ID))
	        	title.setText(R.string.todo);
	            
	            if (tabId.equals(TAB_FILE_MANAGER_ID))
	        	title.setText(R.string.file_manager);
	        	
	        }
	    });
    }
}