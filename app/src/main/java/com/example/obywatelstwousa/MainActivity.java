package com.example.obywatelstwousa;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {


    int index_Pytanie = 0;
    int index_Odpowiedz = 0;

    static final String STATE_INDEX = "index";
    static final String STATE_BOOLEAN = "display";

    boolean displayAnswer = false;

    String[] pytania;
    String[] odpowiedzi;

    TextView pytanie;
    TextView odpowiedz;
    Button odpowiedz_button;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            int index = savedInstanceState.getInt(STATE_INDEX);
            displayAnswer = savedInstanceState.getBoolean(STATE_BOOLEAN);
            index_Pytanie = index;
            index_Odpowiedz = index;
        }

        pytania = getResources().getStringArray(R.array.pytania);
        odpowiedzi = getResources().getStringArray(R.array.odpowiedzi);

        LinearLayout linearLayout = findViewById(R.id.linear_layout);
        pytanie = findViewById(R.id.pytanie_text_view);
        odpowiedz = findViewById(R.id.odpowiedz_text_view);
        odpowiedz_button = findViewById(R.id.odpowiedz_button);
        odpowiedz_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    displayAnswer();
            }
        });

        displayQuestion();

        if(displayAnswer){
            displayAnswer();
        }

        linearLayout.setOnTouchListener(new OnSwipeTouchListener(MainActivity.this) {

            @Override
            public void onSwipeRight() {
                index_Pytanie--;
                index_Odpowiedz--;

                if (index_Pytanie < 0 && index_Odpowiedz < 0){
                    index_Pytanie = pytania.length - 1;
                    index_Odpowiedz = odpowiedzi.length - 1;
                    hideAnswer();
                    displayQuestion();
                }else{
                    hideAnswer();
                    displayQuestion();
                }
            }

            @Override
            public void onSwipeLeft() {

                index_Pytanie++;
                index_Odpowiedz++;

                if (index_Pytanie >= pytania.length && index_Odpowiedz >= odpowiedzi.length){
                    index_Pytanie = 0;
                    index_Odpowiedz = 0;
                    hideAnswer();
                    displayQuestion();
                }else{
                    hideAnswer();
                    displayQuestion();
                }
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle state) {
        displayAnswer = true;
        state.putInt(STATE_INDEX, index_Pytanie);
        state.putBoolean(STATE_BOOLEAN, displayAnswer);

        super.onSaveInstanceState(state);
    }

    @SuppressLint("SetTextI18n")
    public void displayQuestion(){
        pytanie.setText("Pytanie " + (index_Pytanie + 1) + "\n\n" +pytania[index_Pytanie]);
    }

    public void displayAnswer(){
        odpowiedz.setText(odpowiedzi[index_Odpowiedz]);
    }

    public void hideAnswer(){
        odpowiedz.setText("");
    }
}
