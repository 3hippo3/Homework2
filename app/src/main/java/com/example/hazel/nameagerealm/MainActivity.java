package com.example.hazel.nameagerealm;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hazel.nameagerealm.Realm.Person;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;


public class MainActivity extends ActionBarActivity {
    Realm realm;
    String name;
    int age;
    int ageLim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setup();

        //creates a realm instance (now you have a realm)
        realm = realm.getInstance(this);
    }

    public void setup() {

        //makes a Button object in the class from the button in the XML
        Button submit_button = (Button) findViewById(R.id.submit_button);

        //Button reacts when clicked
        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //takes the name inputted by the user and assigns it to variable name
                EditText nameText = (EditText) findViewById(R.id.name);
                name = nameText.getText().toString();
                //resets the name edit text
                nameText.setText("");

                //same thing but for age (age is defined in the else statement)
                EditText ageText = (EditText) findViewById(R.id.age);

                //if the user forgets to input a name or an age, the if statement catches it and
                //displays an informative toast (message on screen)
                if (nameText.getText().toString().equals("") && ageText.getText().toString().equals("")) {
                    Context context = getApplicationContext();
                    CharSequence text = "Enter a name and an age";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                } else {
                    age = Integer.parseInt(ageText.getText().toString());
                    ageText.setText("");

                    //starting line t make changes in realm
                    realm.beginTransaction();

                    //creates a person object in realm, from the person class
                    Person person = realm.createObject(Person.class);
                    person.setName(name);
                    person.setAge(age);

                    //commits the changes you made, w/out it nothing happens
                    realm.commitTransaction();
                    Log.e("age value", person.toString());
                }
            }
        });

        //defines list button in class
        Button list_button = (Button) findViewById(R.id.list_button);

        list_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText ageLimText = (EditText) findViewById(R.id.ageLimit);

                //query is name of Query, a query looks through the data and finds all
                //the matches to what you queried for
                RealmQuery<Person> query = realm.where(Person.class);
                RealmResults<Person> result;

                //if the user forgets or puts no age limit -> displays all entries
                if (!ageLimText.getText().toString().equals("")) {
                    ageLim = Integer.parseInt(ageLimText.getText().toString());
                    ageLimText.setText("");

                    //queries for all ages <= the age limit
                    query.lessThanOrEqualTo("age", ageLim);
                    result = query.findAll();
                } else{
                    Log.e("else", ageLimText.getText().toString());
                    result = realm.allObjects(Person.class);
                }

                //defines a new array list, you can pass arrayLists, not Maps, through intents
                ArrayList<Map<String, String>> peopleList = new ArrayList<>();

                //for all person(s) (person is redefined as a person) in result it makes a
                // map consisting of a name and an age. Maps contain a key (identifier) and
                // the actual data
                for (Person person : result) {
                    Map<String, String> people = new HashMap<>();
                    people.put("name", person.getName());
                    //the "" is a sneaky way to make age a string (fit in Map parameters)
                    people.put("age", person.getAge() + "");

                    //adds the map to the ArrayList
                    peopleList.add(people);
                }
                //Creates an intent, intents can pass data between Activities (screens, generally
                // they have a class and an XML attached to them)
                //the put extra adds the extra stuff we're sending (ArrayList), identified
                //by a key and the objects name
                //start activity is needed to pass the information
                Intent intent = new Intent(MainActivity.this, DisplayListActivity.class);
                intent.putExtra("people", peopleList);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

}
