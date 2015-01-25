package com.example.hazel.nameagerealm;

import android.app.ListActivity;
import android.content.Context;
import android.database.DataSetObserver;
import android.provider.Contacts;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;


public class DisplayListActivity extends ListActivity {
    ArrayList<Map<String, String>> peopleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_list);

        //sets the arrayList in this activity = to the AL in MainActivty
        peopleList = (ArrayList<Map<String, String>>)getIntent().getSerializableExtra("people");

        setup();
    }

    public void setup() {

        //List adapter makes it so you can interact with our ListView
        setListAdapter(new ListAdapter() {
            @Override
            //TRUE CUZ THEN ITS USABLE
            public boolean areAllItemsEnabled() {
                return true;
            }

            @Override
            public boolean isEnabled(int position) {
                return true;
            }

            @Override
            public void registerDataSetObserver(DataSetObserver observer) {

            }

            @Override
            public void unregisterDataSetObserver(DataSetObserver observer) {

            }

            @Override
            //how many things(people) you're adding to it(ListView) <-rows
            public int getCount() {

                return peopleList.size();
            }

            @Override
            //position of each 'person'
            public Object getItem(int position) {

                return peopleList.get(position);
            }

            @Override
            public long getItemId(int position) {

                return 0;
            }

            @Override
            public boolean hasStableIds() {
                return false;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View rowView = convertView;
                if(rowView == null) {
                    //LayoutInflaters make an XML file into a View object
                    LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    rowView = layoutInflater.inflate(R.layout.rows, parent, false);
                }
                //creates a map containing names and ages, gets their position (so that each one
                //has its own identity) then sets the names/ages to their respective textView
                //then they show up in two lists. same position -> same line
                Map<String, String> people = (Map<String, String>)getItem(position);
                ((TextView)rowView.findViewById(R.id.leftList)).setText(people.get("name"));
                ((TextView)rowView.findViewById(R.id.rightList)).setText(people.get("age"));

                return rowView;
            }

            @Override
            public int getItemViewType(int position) {
                return 0;
            }

            @Override
            public int getViewTypeCount() {
                return 1;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_display_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onListItemClick(ListView listView, View v, int position, long id) {
        super.onListItemClick(listView, v, position, id);
        finish();

    }
}
