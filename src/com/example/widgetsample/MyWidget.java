package com.example.widgetsample;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;

public class MyWidget extends AppWidgetProvider {
    @Override
    public void onUpdate(Context context,AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        RemoteViews updateViews = new RemoteViews(context.getPackageName(), R.layout.main);
        updateViews.setTextViewText(R.id.now,  
        		new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
        appWidgetManager.updateAppWidget(appWidgetIds, updateViews);
    }
}