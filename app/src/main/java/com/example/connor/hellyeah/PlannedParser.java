package com.example.connor.hellyeah;

/*Connor Birnie
S1630777
CBIRNI200

This class handles the grabbing and parsing of Planned Roadworks

*/

import android.app.Activity;
import android.os.AsyncTask;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;


import java.io.IOException;
import java.io.InputStream;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class PlannedParser extends AsyncTask {
    URL url;
    String title = "";
    String Desc = "";
    String Rez = "";
    ArrayList<String> result = new ArrayList<>();

    @Override
    protected Object doInBackground(Object[] objects) {
        //variables and pullparser



        try {
            url = new URL("http://trafficscotland.org/rss/feeds/plannedroadworks.aspx");//url

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(false);
            XmlPullParser pull = factory.newPullParser();
            pull.setInput(getInputStream(url), "UTF_8");//setting up pullparser

            boolean initem = false;
            int eventType = pull.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                        //searching the XML for specific tags and adding to ArrayList and Variables
                    if (pull.getName().equalsIgnoreCase("item")) {
                        initem = true;
                    } else if (pull.getName().equalsIgnoreCase("title")) {
                        if (initem)
                            title = (pull.nextText());
                    } else if (pull.getName().equalsIgnoreCase("description")) {
                        if (initem)
                            Desc = (pull.nextText());
                        Rez = title + "\n" + "____________________________" + "\n" + Desc + "\n";
                        result.add(Rez);
                    }
                } else if (eventType == XmlPullParser.END_TAG && pull.getName().equalsIgnoreCase("item")) {
                    initem = false;
                }

                eventType = pull.next(); //go to next Item in XML
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        result.set(0, "Planned RoadWorks - Long Hold for Info");//displaying info panel at top to help users understand
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
        //this is used to pass ArrayList to MainActivity

        return result;
    }

}


