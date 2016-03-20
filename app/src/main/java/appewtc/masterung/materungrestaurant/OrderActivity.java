package appewtc.masterung.materungrestaurant;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

public class OrderActivity extends AppCompatActivity {

    //Explicit
    private TextView showOfficerTextView;
    private Spinner deskSpinner;
    private ListView foodListView;

    private String officerString, deskString, foodString, amountString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        //Bind Widget
        bindWidget();

        //Show View
        showView();

        //Create Desk Spinner
        createDeskSpinner();

        //Create Food ListView
        createFoodListView();


    }   // Main Method

    private void createFoodListView() {

        //Read All SQLite
        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                MODE_PRIVATE, null);
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + MyManage.food_table, null);
        cursor.moveToFirst();
        int intCount = cursor.getCount();

        String[] iconStrings = new String[intCount];
        String[] foodStrings = new String[intCount];
        String[] priceStrings = new String[intCount];

        for (int i = 0; i < intCount; i++) {

            iconStrings[i] = cursor.getString(cursor.getColumnIndex(MyManage.column_Source));
            foodStrings[i] = cursor.getString(cursor.getColumnIndex(MyManage.column_Food));
            priceStrings[i] = cursor.getString(cursor.getColumnIndex(MyManage.column_Price));

            cursor.moveToNext();
        }   // for
        cursor.close();

        FoodAdapter foodAdapter = new FoodAdapter(OrderActivity.this, iconStrings,
                foodStrings, priceStrings);
        foodListView.setAdapter(foodAdapter);



    }   //FoodListView

    private void createDeskSpinner() {

        final String[] deskStrings = {"โต้ะ 1", "โต้ะ 2", "โต้ะ 3", "โต้ะ 4", "โต้ะ 5",
                "โต้ะ 6", "โต้ะ 7", "โต้ะ 8", "โต้ะ 9", "โต้ะ 10"};

        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, deskStrings);
        deskSpinner.setAdapter(stringArrayAdapter);

        deskSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                deskString = deskStrings[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                deskString = deskStrings[0];
            }
        });

    }   // deskSpinner

    private void showView() {

        //Receive Value From Intent
        officerString = getIntent().getStringExtra("Officer");
        showOfficerTextView.setText(officerString);

    }

    private void bindWidget() {
        showOfficerTextView = (TextView) findViewById(R.id.textView);
        deskSpinner = (Spinner) findViewById(R.id.spinner);
        foodListView = (ListView) findViewById(R.id.listView);
    }

}   // Main Class
