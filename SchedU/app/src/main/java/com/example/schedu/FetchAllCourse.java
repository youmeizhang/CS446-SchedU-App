package com.example.schedu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FetchAllCourse extends AsyncTask<DatabaseManager, Void, Void> {
    String data = "";
    String courseList = "";
    String subject = "";
    String catalog_number = "";
    DatabaseManager databaseManager = null;
    final int NUM_TRY_MAX = 5;
    final int NUM_THREAD = 150;
    final int TIME_OUT = 30000; //milliseconds.
    public static int tt = 0;
    public static synchronized void increment() {
        tt++;
    }
    //public static synchronized int inserted = 0;
    //public static int updated = 0;

    String data_parsed = "";

    public FetchAllCourse(DatabaseManager databaseManager) {
        super();
        this.databaseManager = databaseManager;
    }

    @Override
    protected Void doInBackground(DatabaseManager... args) {
        int time = (int) (System.currentTimeMillis());
        int numTry = 0;
        while (doJob() == false) {// try to get the whole course info,
            numTry ++;
            if (numTry == NUM_TRY_MAX) break;
            System.out.println("fetch whole courses, retry " + numTry );
        }

        int diff = ((int) (System.currentTimeMillis())) - time;
        System.out.println("TIME USED: " + diff);
        return null;
    }

    // Get the whole course info.
    private boolean doJob() {
        boolean retval = false;

        HttpURLConnection httpURLConnection = null;
        if(android.os.Debug.isDebuggerConnected())        // these two lines for debugging
            android.os.Debug.waitForDebugger();
        try {
            URL url = new URL("https://api.uwaterloo.ca/v2/courses.json?key=6e9e2a8c5b5114e5b4fe9d30387fec4d");
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(TIME_OUT); // timeout 30 sec
            httpURLConnection.setReadTimeout(TIME_OUT);    // readtimeout 30 sec
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while (line != null) {
                line = bufferedReader.readLine();
                data = data + line;
            }
            //double totalclass = 0;
            JSONObject JO = new JSONObject(data);
            courseList  = JO.getString("data");
            JSONArray JA = new JSONArray(courseList);
            int ct = 0;
            int ttl = JA.length();

            // all course info obtained. Get individual course info by threadpool
            ExecutorService executor = Executors.newFixedThreadPool(NUM_THREAD);
            for (int i = 0; i < JA.length(); i++) {
                JSONObject courseObj = (JSONObject) JA.get(i);
                subject  = courseObj.getString("subject");
                catalog_number  = courseObj.getString("catalog_number");


//                if (subject.equals("CS") || subject.equals("MATH")){
//                //if (subject.equals("MATH")){
//                    ct++;
//                    System.out.println(subject + " " + catalog_number);
//                    Runnable worker = new WorkerThread(subject, catalog_number);
//                    executor.execute(worker);
//                }

                ct++;
                // Get all sections info for this course, and insert/update this info in the database
                Runnable worker = new WorkerThread(subject, catalog_number);
                executor.execute(worker);


                //if (ct == 2000) break;
            }
            System.out.println("FetchAllCourse.java: " + "all tasks submitted");
            executor.shutdown();
            while (!executor.isTerminated()) {
            }
            System.out.println("FetchAllCourse.java: Finished "+ct+" courses.");
            retval = true;
            System.out.println("CS course: " + tt);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (java.net.SocketTimeoutException e) {
            e.printStackTrace();
            System.out.println("FetchAllCourse.java: fetching-data time out");
        }   catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }  finally {
            httpURLConnection.disconnect();
        }

        return retval;

    }


    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
