package tab.androiddev.com.menuu3;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends android.support.v4.app.FragmentActivity {


   public static Font font;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        font=new Font();

        ((Button)findViewById(R.id.button1)).setTypeface(font.get_icons("fonts/ionicons.ttf",this));
        ((Button)findViewById(R.id.button2)).setTypeface(font.get_icons("fonts/ionicons.ttf",this));
        ((Button)findViewById(R.id.button3)).setTypeface(font.get_icons("fonts/ionicons.ttf",this));
        ((Button)findViewById(R.id.button4)).setTypeface(font.get_icons("fonts/ionicons.ttf",this));


        // final ImageButton button1 = (ImageButton) findViewById(R.id.imageButton1);
        final Button button2 = (Button) findViewById(R.id.button2);
        final Button button3 = (Button) findViewById(R.id.button3);
        final Button button1 = (Button) findViewById(R.id.button1);
        final Button button4 = (Button) findViewById(R.id.button4);




        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                button2.setTextColor(Color.parseColor("#ff1d9c00"));
                button1.setTextColor(Color.parseColor("#de6c6c6c"));
                button3.setTextColor(Color.parseColor("#de6c6c6c"));
                button4.setTextColor(Color.parseColor("#de6c6c6c"));
               /* HopitalFragment fragment2 = new HopitalFragment();
                FragmentManager fragmentManager2 = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction2 = fragmentManager2.beginTransaction();
                fragmentTransaction2.add(R.id.container2, fragment2, "Hopitals Location");
                fragmentTransaction2.commit();*/

                // Perform action on click

                Intent activityChangeIntent = new Intent(MainActivity.this, MapsActivity.class);

                // currentContext.startActivity(activityChangeIntent);

                MainActivity.this.startActivity(activityChangeIntent);

            }
        });


        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                button1.setTextColor(Color.parseColor("#ff1d9c00"));
                button2.setTextColor(Color.parseColor("#de6c6c6c"));
                button3.setTextColor(Color.parseColor("#de6c6c6c"));
                button4.setTextColor(Color.parseColor("#de6c6c6c"));

                PharmacieFragment fragment = new PharmacieFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.container2, fragment, "Pharmacie Location");
                fragmentTransaction.commit();

            }
        });


        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                button3.setTextColor(Color.parseColor("#ff1d9c00"));
                button1.setTextColor(Color.parseColor("#de6c6c6c"));
                button2.setTextColor(Color.parseColor("#de6c6c6c"));
                button4.setTextColor(Color.parseColor("#de6c6c6c"));

                PharmacieFragment fragment = new PharmacieFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.container2, fragment, "Pharmacies Location");
                fragmentTransaction.commit();


            }
        });


        button4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                button4.setTextColor(Color.parseColor("#ff1d9c00"));
                button1.setTextColor(Color.parseColor("#de6c6c6c"));
                button3.setTextColor(Color.parseColor("#de6c6c6c"));
                button2.setTextColor(Color.parseColor("#de6c6c6c"));

                PharmacieFragment fragment = new PharmacieFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.container2, fragment, "Pharmacies Location");
                fragmentTransaction.commit();


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
