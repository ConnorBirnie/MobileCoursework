package com.example.connor.hellyeah;

/*Connor Birnie
S1630777
CBIRNI200

This is the main Activity where all main code is stored

*/

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SearchView;
import android.widget.Toast;
import android.widget.ViewFlipper;
import java.util.ArrayList;


import android.support.v7.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    //variables

    RadioButton rb, rb2;
    ViewFlipper ViewFlip;
    Button meme;
    ListView List2, List1;
    ArrayList<String> listItems;
    EditText editText;
    ArrayAdapter<String> adapter;
    String[] items;
    SearchView search;
    ActionBar ab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //grabbing elements from View

        rb = (RadioButton) findViewById(R.id.radio0);
        rb2 = (RadioButton) findViewById(R.id.radio1);
        ViewFlip = (ViewFlipper) findViewById(R.id.ViewFlipper01);
        List2 = (ListView) findViewById(R.id.jeff);
        List2.setTextFilterEnabled(true);
        List1 = (ListView) findViewById(R.id.hello);
        List1.setTextFilterEnabled(true);
        search = (SearchView) findViewById(R.id.searchView1);




        search.setQueryHint("Search For A Road");//hint for searchbar
        search.setBackgroundColor(Color.GRAY);
        ab = getSupportActionBar();
        ab.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#8D908D")));//grabbing ActionBar


        rb.setOnClickListener(radio_listener);//Onclicker for both RadioButtons
        rb2.setOnClickListener(radio_listener);


        ArrayList<String> curr = new ArrayList<>();
        CurrentParser RSSREAD = new CurrentParser();
        RSSREAD.execute();
        curr = RSSREAD.grab();//Setting up new ArrayList to hold the Parsed XML from Current Incidents

        ArrayList<String> plan = new ArrayList<>();//Setting up new ArrayList to hold the Parsed XML from RoadWorks
        PlannedParser RSSREAD1 = new PlannedParser();
        RSSREAD1.execute();
        plan = RSSREAD1.grab();

        final ArrayAdapter Current = new ArrayAdapter(this, android.R.layout.simple_list_item_1, curr);//adding the parsed information to an ArrayAdapter
        final ArrayAdapter Planned = new ArrayAdapter(this, android.R.layout.simple_list_item_1, plan);


        List1.setAdapter(Current);//setting the ListView = to the Parsed information to display
        List2.setAdapter(Planned);













        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {//here is the Search Function which searches the listView

            @Override
            public boolean onQueryTextSubmit(String text) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String text) {//when Text is entered into the searchView searchfunction is applied text= usersinput
                Current.getFilter().filter(text);//current
                Planned.getFilter().filter(text);//planned
                return false;
            }
        });


        List1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {//Setting Up onHolditem on the Current ListView to display a link to the Traffic Scotland Twitter Feed
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                Toast.makeText(getApplication(), "Showing Twitter Feed",
                        Toast.LENGTH_SHORT).show();//toast message to tell user what is happenng

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {//using a delay of 2 seconds
                        Intent browserIntent = new Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://twitter.com/trafficscotland"));
                        startActivity(browserIntent);

                    }
                }, 2000);



                return false;
            }
        });




        List2.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {//on hold listener on Planned ListView which opens Google map to show where the fault is
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplication(), "Google Maps",
                        Toast.LENGTH_SHORT).show();//toast message to tell user what is happening

                String uri = "geo:0,0?q="+"57.2086980231499"+","+"-2.08258657901011"+"&z=16"; //HardCoded In Should be seperate for each ListItem geoCoord
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(intent);//opening new Activity

                return false;
            }
        });


    }








    private View.OnClickListener radio_listener = new View.OnClickListener() {// This allows for the View to be changed with the use of Radio Buttons
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.radio0:
                    ViewFlip.setDisplayedChild(0);//displays ListView1

                    break;
                case R.id.radio1:
                    ViewFlip.setDisplayedChild(1);//Displays ListView2


                    break;
            }
        }
    };



}



