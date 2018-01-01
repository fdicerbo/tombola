package io.github.fdicerbo.tombola;

//import android.support.v7.app.AppCompatActivity;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.security.SecureRandom;

public class MainActivity extends Activity {

    int counter = 0;

    String elencoEstratti = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        PixelGridView pixelGrid = new PixelGridView(this);
//        pixelGrid.setNumColumns(10);
//        pixelGrid.setNumRows(9);

//        setContentView(pixelGrid);
        setContentView(R.layout.activity_main);

        TableLayout stk = (TableLayout) findViewById(R.id.contentTableLayout);
        Button referenceButton = (Button) findViewById(R.id.estrazioneButton);

//        int width = (referenceButton.getWidth() - 10) / 10;
        int width = (stk.getWidth() - 10) / 10;

//        TableRow tbrow0 = new TableRow(this);
//        TextView tv0 = new TextView(this);
//        tv0.setText(" Sl.No ");
//        tv0.setTextColor(Color.WHITE);
//        tbrow0.addView(tv0);
//        tbrow0.addView(tv3);
//        stk.addView(tbrow0);
        for (int i = 0; i < 9; i++) {
            TableRow tbrow = new TableRow(this);
//            tbrow.set

            for (int j = 0; j < 10; j++) {
                Button aButton = new Button(this);
                aButton.setEnabled(false);

//                aButton.setWidth(width);
//                aButton.setTextScaleX(0.5f);
                aButton.setText(String.valueOf(i * 10 + j + 1));
                aButton.setId((i * 10 + j + 1));
//                aButton.setWidth(width);
                tbrow.addView(aButton);
            }
            stk.addView(tbrow);
        }

        ScrollView scrv = (ScrollView) findViewById(R.id.mainScrollView);
        scrv.computeScroll();
        scrv.requestLayout();

        Button estrazione = (Button) findViewById(R.id.estrazioneButton);

        estrazione.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                estrazione(v);
            }
        });

        Button resetButton = (Button) findViewById(R.id.resetButton);

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset(view);
            }
        });

        Button logViewerButton = (Button) findViewById(R.id.logButton);
        logViewerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLogText(view);
            }
        });

    }

    private void showLogText(View view) {


        TextView textView = (TextView) findViewById(R.id.mainTextView);
        textView.setText(elencoEstratti);


        switch (textView.getVisibility()) {
            case View.INVISIBLE:
                textView.setVisibility(View.VISIBLE);
                break;
            case View.VISIBLE:
                textView.setVisibility(View.INVISIBLE);
                break;
        }

    }

    private void reset(View v) {

        counter = 0;

        for (int i = 0; i < 90; i++) {
            Button targetButton = (Button) findViewById((i + 1));
            targetButton.setEnabled(false);
        }

        Toast.makeText(
                getApplicationContext(),
                R.string.reset_message,
                Toast.LENGTH_LONG).show();

    }
    


    private void estrazione(View v) {

        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[1];
        short converted = 0;
        boolean flag = false;
        do {

            random.nextBytes(bytes);
            converted = (short) bytes[0];

            if (converted < 0) {
                converted *= -1;
            }
            int target = 0;
            if (counter == 89) {
                int tmpCounter = 0;
                Button targetButton = null;
                for (int i = 0; i < 90; i++) {

                    targetButton = (Button) findViewById((i + 1));
                    if (targetButton.isEnabled() == false) {
                        target = i + 1;
                        counter++;
                        break;
                    }
                }
                targetButton.setEnabled(true);
                Toast.makeText(
                        getApplicationContext(),
                        "ULTIMO Estratto: " + (target),
                        Toast.LENGTH_LONG).show();
                String message = "Estratto: " + target + " counter: " + counter;

                Log.d("tombola", message);

                elencoEstratti += message+"\n";

                flag = true;
            }
            if ((converted > -1) && (converted < 90) && counter < 89) {

                Button targetButton = (Button) findViewById((converted + 1));

                if (targetButton.isEnabled() == false) {
                    targetButton.setEnabled(true);

                    flag = true;
                    counter++;
                    Toast.makeText(
                            getApplicationContext(),
                            "Estratto: " + (converted + 1),
                            Toast.LENGTH_LONG).show();
                    String message = "Estratto: " + (converted + 1) + " counter: " + counter;

                    Log.d("tombola", message);

                    elencoEstratti += message+"\n";


                }

            } else if (counter == 90) {
                Toast.makeText(
                        getApplicationContext(),
                        "il gioco é finito!",
                        Toast.LENGTH_LONG).show();
                flag = true;
            }
        } while (!flag);


    }




    @Override
    public void onStart() {
        super.onStart();


    }

    @Override
    public void onStop() {
        super.onStop();

    }
}
