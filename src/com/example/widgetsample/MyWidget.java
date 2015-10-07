package com.example.widgetsample;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

public class MyWidget extends AppWidgetProvider {
	public static final String TOAST_ACTION = "com.example.android.stackwidget.TOAST_ACTION";
	
	@Override
    public void onReceive(Context context, Intent intent) {
		Log.v("Dean", "onReceive");
        AppWidgetManager mgr = AppWidgetManager.getInstance(context);
        if (intent.getAction().equals(TOAST_ACTION)) {
            int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
            Toast.makeText(context, "Touched view ", Toast.LENGTH_SHORT).show();
        }
        super.onReceive(context, intent);
    }
	
    @Override
    public void onUpdate(Context context,AppWidgetManager appWidgetManager, int[] appWidgetIds) {
    	
    	for (int i = 0; i < appWidgetIds.length; i++) {
    		// 建立一個intent,繼承RemoteViewsService,return RemoteViewsFactory
            final Intent intent = new Intent(context, MyWidgetService.class);
            intent.putExtra (AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
            RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.main) ;  
        
            // 处理Listview需要用SetRemoteAdapter，和intent返回的值
            int sdk = android.os.Build.VERSION.SDK_INT;
            if(sdk < 14) {
            	rv.setRemoteAdapter(appWidgetIds[i], R.id.listView1, intent);
            } else {
            	rv.setRemoteAdapter(R.id.listView1, intent);
            } 
            // listview沒資料時顯示R.id.now,有資料時R.id.now會被隱藏
            //rv.setEmptyView(R.id.listView1, R.id.now);
            rv.setTextViewText(R.id.now,  
            		new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));

            // 廣播測試
            Intent toastIntent = new Intent(context, MyWidget.class);
            toastIntent.setAction(MyWidget.TOAST_ACTION);
            toastIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
            PendingIntent toastPendingIntent = PendingIntent.getBroadcast(context, 0, toastIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
            rv.setPendingIntentTemplate(R.id.listView1, toastPendingIntent);
            
            appWidgetManager.updateAppWidget(appWidgetIds[i], rv);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds[i], R.id.listView1);

    	}
    }
}