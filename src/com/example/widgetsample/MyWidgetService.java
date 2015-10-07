package com.example.widgetsample;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

public class MyWidgetService extends RemoteViewsService{

	@Override
	public RemoteViewsFactory onGetViewFactory(Intent intent) {
		Log.v("Dean", "onGetViewFactory");
		return new StackRemoteViewsFactory(this.getApplicationContext(), intent);
	}

}

class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
	private Context mContext;
    private int mAppWidgetId;
    
    private ArrayList<HashMap<String, String>> data ;
    
	public StackRemoteViewsFactory(Context context, Intent intent) {
		Log.v("Dean", "StackRemoteViewsFactory");
		 mContext = context;
		 mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
				 AppWidgetManager.INVALID_APPWIDGET_ID);	       
	}
	 
	@Override
	public void onCreate() {
		Log.v("Dean", "onCreate");
		//�bonCreate��lvalue
		data = new ArrayList<HashMap<String, String>>();
        
        for (int i = 1; i < 5; i++) {
            HashMap<String, String> map = new HashMap<String, String>(); 
            map.put("title", String.valueOf(i));
            map.put("value", String.valueOf(i*111));
            data.add(map);
        }
	}	

	@Override
	public RemoteViews getViewAt(int position) {
		Log.v("Dean", "getViewAt");
		//��listView���C��item�]��, position�O����index
		
		HashMap<String, String> map = (HashMap<String, String>) data.get(position);
		RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.listview_item);
		
		remoteViews.setTextViewText(R.id.textView_title, (String)map.get("title"));
		remoteViews.setTextViewText(R.id.textView_value, (String)map.get("value"));
		
		// �IListView��Title��Ĳ�oMyWidget.onReceive()
		// �I��L�a�褣�|������
        Intent fillInIntent = new Intent();
        remoteViews.setOnClickFillInIntent(R.id.textView_title, fillInIntent);
		
		return remoteViews;
	}

	@Override
	public void onDataSetChanged() {
		Log.v("Dean", "onDataSetChanged");
		
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getCount() {
		// getViewAt()�|�ھ�getCount()���ƥبӨM�w���榸��
		return data.size();
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	@Override
	public int getViewTypeCount() {
		return 1;
	}
	
	@Override
	public RemoteViews getLoadingView() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}
	
}