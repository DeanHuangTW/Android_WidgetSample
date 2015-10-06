package com.example.widgetsample;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

public class MyWidget extends AppWidgetProvider {
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
           
            appWidgetManager.updateAppWidget(appWidgetIds[i], rv);

    	}
    }
}