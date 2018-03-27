package com.example.connor.hellyeah;
/*Connor Birnie
S1630777
CBIRNI200

This class handles the grabbing and parsing of Current Incidents
very similar to PlannedParser

*/


import android.os.AsyncTask;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;


import java.io.IOException;
import java.io.InputStream;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.ArrayList;


public class CurrentParser extends AsyncTask {
//declare variables
    URL url;
    ArrayList<String> headlines = new ArrayList();
    String title = "";
    String Desc = "";
    String Rez = "";
    ArrayList<String> result = new ArrayList<>();


    @Override
    protected Object doInBackground(Object[] objects) {


        try {
            url = new URL("http://trafficscotland.org/rss/feeds/currentincidents.aspx");//url

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(false);
            XmlPullParser pull = factory.newPullParser();

            pull.setInput(getInputStream(url), "UTF_8");
            boolean inner = false;

            // works in the same way as PlannedParser
            int eventType = pull.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                        //search for correct tags
                    if (pull.getName().equalsIgnoreCase("item")) {
                        inner = true;
                    } else if (pull.getName().equalsIgnoreCase("title")) {
                        if (inner)
                            title = (pull.nextText());
                    } else if (pull.getName().equalsIgnoreCase("description")) {
                        if (inner)
                            Desc = (pull.nextText());
                        Rez = title + "\n"+"____________________________"+"\n" + Desc + "\n";
                        result.add(Rez);
                    }
                } else if (eventType == XmlPullParser.END_TAG && pull.getName().equalsIgnoreCase("item")) {
                    inner = false;
                }

                eventType = pull.next(); //Move to Next itemm in XML
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();//used for testing
        }

        result.set(0, "Current Incidents - Long Hold for Info");//info bar
        return result;



    }


    public InputStream getInputStream(URL url) {
        try {
            return url.openConnection().getInputStream();
        } catch (IOException e) {
            return null;
        }
    }

    public ArrayList<String> grab()
    {
        return result;
    }

}


