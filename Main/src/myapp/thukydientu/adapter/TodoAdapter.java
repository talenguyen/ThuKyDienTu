package myapp.thukydientu.adapter;

import myapp.thukydientu.R;
import myapp.thukydientu.database.TodoTable;
import myapp.thukydientu.model.IConstants;
import myapp.thukydientu.model.Todo;
import myapp.thukydientu.provider.TKDTProvider;
import myapp.thukydientu.util.TimeUtils;
import myapp.thukydientu.util.TodoUtils;
import myapp.thukydientu.view.TodoAddActivity;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.AsyncQueryHandler;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class TodoAdapter extends CursorAdapter {
	
	private static final class QueryHandler extends AsyncQueryHandler {
	    private TodoAdapter mAdapter;

	    public QueryHandler(Context context, TodoAdapter adapter) {
	        super(context.getContentResolver());
	        this.mAdapter = adapter;
	    }

	    @Override
	    protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
	    	mAdapter.changeCursor(cursor);
	    }
	}
	
	private class ViewHolder {
		Button edit;
		Button delete;
		TextView date;
		TextView time;
		TextView event;
		TextView description;
		TextView end;
	}
	
	private Context mContext;
	
	public TodoAdapter(Context context, int userId) {
		super(context, null);
		
		mContext = context;
		
		QueryHandler queryHandler = new QueryHandler(context, this);
//		queryHandler.startQuery(
//				userId, 
//				null, 
//				event.CONTENT_URI, 
//				event.PROJECTION, 
//				event.CALENDAR_ID + "=" + event.CALENDAR, 
//				null, 
//				event.DATE_START + " ASC");
		queryHandler.startQuery(
				userId, 
				null, 
				TKDTProvider.TODO_CONTENT_URI, 
				TodoTable.PROJECTION, 
				null, 
				null, 
				TodoTable.DATE_START + " ASC");
	}
	
	public ViewHolder getViewHolder(View view) {
		ViewHolder holder = (ViewHolder) view.getTag();
		if (holder == null) {
			holder = new ViewHolder();
			holder.edit = (Button) view.findViewById(R.id.edit);
			holder.delete = (Button) view.findViewById(R.id.delete);
			holder.date = (TextView) view.findViewById(R.id.date);
			holder.time = (TextView) view.findViewById(R.id.time);
			holder.event = (TextView) view.findViewById(R.id.event);
			holder.description = (TextView) view.findViewById(R.id.description);
			holder.end = (TextView) view.findViewById(R.id.end);
		}
		return holder;
	}
	
	@Override
	public void bindView(View view, final Context context, Cursor cursor) {
		ViewHolder holder = (ViewHolder) view.getTag();
		
		final Todo todo = new Todo();
		TodoUtils.bindTodoData(todo, cursor);
		
		final long startTime = TimeUtils.toTimeInMilisecond(todo.getDateStart(), todo.getTimeFrom());
		
		final long currentTime = System.currentTimeMillis();
		if (startTime < currentTime)
			view.setBackgroundResource(R.drawable.list_item_out_of_date);
		else 
			view.setBackgroundResource(R.drawable.list_item);
		
		holder.date.setText(TimeUtils.getDateLable(mContext, startTime));
		holder.time.setText(TimeUtils.getTimeLable(mContext, startTime));

		holder.event.setText(todo.getTitle());
		holder.description.setText(todo.getWork());
		
		final long endTime = TimeUtils.toTimeInMilisecond(todo.getDateEnd(), todo.getTimeUntil());
		
		holder.end.setText(TimeUtils.getTimeLable(mContext, endTime) + " " + TimeUtils.getDateLable(mContext, endTime));
		
		holder.edit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent addTodoIntent = new Intent(mContext, TodoAddActivity.class);
				Bundle bundle = new Bundle();
				bundle.putLong(IConstants._ID, todo.getId());
				addTodoIntent.putExtras(bundle);
				((Activity) mContext).startActivityForResult(addTodoIntent, 1);
			}
		});
		
		holder.delete.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				builder
					.setIcon(android.R.drawable.ic_dialog_alert)
					.setTitle("Xác nhận!")
					.setMessage("Xóa thời khóa biểu?")
					.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							TodoUtils.delete((Activity) context, todo);
						}
					})
					.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
						}
					});
				builder.create().show();
			}
		});
	}
	
	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.todo_item, null);
		
		ViewHolder holder = getViewHolder(view);
		view.setTag(holder);
		
		return view;
	}

}
