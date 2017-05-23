package example.bogdan.proiect_mds;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class InGameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_game);


    }

    public void onButtonTap(View v){
        Bundle b = getIntent().getExtras();
        int cutiePostala = -1; // or other values
        if(b != null)
            cutiePostala = b.getInt("key");


        Toast myToast = Toast.makeText(getApplicationContext(),
                                        cutiePostala+" Test", Toast.LENGTH_LONG);
        myToast.show();
    }
}
