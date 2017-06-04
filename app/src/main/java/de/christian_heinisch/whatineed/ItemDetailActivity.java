package de.christian_heinisch.whatineed;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

//import com.squareup.picasso.Picasso;


public class ItemDetailActivity extends AppCompatActivity {


    String titel;
    String langtext;
    String url;
    ImageView img;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);


        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //




        Intent i = getIntent();

        titel = i.getStringExtra("Titelleiste");
        langtext = i.getStringExtra("Beschreibung");
        url = i.getStringExtra("URL");

        TextView content = (TextView) findViewById(R.id.textDetailContent);
        content.setText(langtext);

        TextView headline = (TextView) findViewById(R.id.textDetailHead);
        headline.setText(titel);

        titelleiste("");


        //img = (ImageView) findViewById(R.id.app_bar_image);

        System.out.println(url);
        titelbild(url);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //

            Intent intent = new Intent(this, MainActivity.class);
            //Werte an DetailActivity übergeben
            intent.putExtra("goback", "from_detail");
            NavUtils.navigateUpTo(this, intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void titelleiste(String title) {

        setTitle(title);

    }

    public void titelbild(String url_load) {

        // Titelbild in imageView speichern
        //ImageView imageView = (ImageView) findViewById(R.id.app_bar_image);

        // Titelbild der Detailseite setzten
        /*Picasso.with(this)
                .load(url_load)
                .placeholder(R.drawable.ic_placeholder) // optional
                .error(R.drawable.ic_error_fallback)         // optional
                .fit()
                .into(imageView);*/


    }


}
