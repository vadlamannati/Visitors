package com.example.bharadwaj.visitors;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class MainActivity extends AppCompatActivity {

    private final static String LOG_TAG = MainActivity.class.getSimpleName();
    private static final String NO_VISITORS = "No Visitors";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.v(LOG_TAG, "Entering onCreate method");

        Set<Visitor> visitorList;
        JSONObject visitorsJsonResponseObject = null;
        JSONObject venueJsonObject = null;
        JSONArray visitorsJsonArray = null;
        Log.v(LOG_TAG, "");
        int openTime = 0;
        int closeTime = 0;

        AssetManager assetManager = this.getAssets();
        StringBuilder stringBuilder = new StringBuilder();
        try {
            InputStream inputStream = assetManager.open("people-here.json");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            //Log.v(LOG_TAG, "Input stream : " + stringBuilder);
        } catch (IOException e) {
            e.printStackTrace();
        }

        visitorList = new TreeSet<>(new VisitorComparator());

        try {
            visitorsJsonResponseObject = new JSONObject(stringBuilder.toString());
            venueJsonObject = visitorsJsonResponseObject.getJSONObject("venue");

            openTime = Integer.parseInt(venueJsonObject.getString("openTime"));
            closeTime = Integer.parseInt(venueJsonObject.getString("closeTime"));
            Log.v(LOG_TAG, "Open time : " + openTime);
            Log.v(LOG_TAG, "Close time : " + closeTime);

            visitorsJsonArray = venueJsonObject.getJSONArray("visitors");


            Log.v(LOG_TAG, "Visitors Array length : " + visitorsJsonArray.length());
            for (int i = 0; i < visitorsJsonArray.length(); i++) {

                JSONObject visitorResults = visitorsJsonArray.getJSONObject(i);

                String name = visitorResults.getString("name");
                long arriveTime = Long.parseLong(visitorResults.getString("arriveTime"));
                long leaveTime = Long.parseLong(visitorResults.getString("leaveTime"));

                /*Log.v(LOG_TAG, "Name : " + name);
                Log.v(LOG_TAG, "Arrived at : " + arriveTime);
                Log.v(LOG_TAG, "Left at : " + leaveTime);
*/
                Visitor visitor = new Visitor(name, arriveTime, leaveTime);
                visitorList.add(visitor);
                Log.v(LOG_TAG, "Visitor list size : " + visitorList.size());
            }

            printVisitors(visitorList);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Set<Visitor> emptySlots = new TreeSet<>(new VisitorComparator());
        Iterator<Visitor> visitoristIterator = visitorList.iterator();
        Visitor visitor1 = null;
        Visitor visitor2 = null;
        Visitor visitorBuffer = null;
        if (visitoristIterator.hasNext()) visitor1 = visitoristIterator.next();

        //Checking open time to arrival time of first Visitor
        if (openTime < visitor1.getmArriveTime())
            emptySlots.add(new Visitor(NO_VISITORS, openTime, visitor1.getmArriveTime()));
        while (visitoristIterator.hasNext()) {
            visitor2 = visitoristIterator.next();
            //tempLeaveTime will be updated when algorithm finds a greater leaving timestamp.
            long tempLeaveTime = visitor1.getmLeaveTime();
            while (tempLeaveTime >= visitor2.getmArriveTime())    {
                if (tempLeaveTime < visitor2.getmLeaveTime()){
                    tempLeaveTime = visitor2.getmLeaveTime();
                }
                visitorBuffer = visitor2;
                visitor2 = visitoristIterator.next();
            }
            if (visitorBuffer.getmLeaveTime() < visitor2.getmArriveTime()) {
                emptySlots.add(new Visitor(NO_VISITORS, tempLeaveTime, visitor2.getmArriveTime()));
            }
            visitor1 = visitor2;
        }
        //Checking leaving time of last Visitor to close time
        if (visitor1.getmLeaveTime() < closeTime)
            emptySlots.add(new Visitor(NO_VISITORS, visitor1.getmLeaveTime(), closeTime));

        printVisitors(emptySlots);

        Log.v(LOG_TAG, "Leaving onCreate method");

    }

    void printVisitors(Set<Visitor> visitors) {
        Log.v(LOG_TAG, "Visitors Array length : " + visitors.size());
        for (Visitor visitor : visitors) {
            Log.v(LOG_TAG, visitor.getmName() + " arrived on : " + visitor.getmArriveTime() + " and left on : " + visitor.getmLeaveTime());
        }
    }
}
