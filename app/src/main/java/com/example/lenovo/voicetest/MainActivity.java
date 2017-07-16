package com.example.lenovo.voicetest;

import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity{
    private Button btnReconizer;
    private static final int VOICE_RECOGNITION_REQUEST_CODE = 1234;
    private TextView sentence;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnReconizer = (Button) this.findViewById(R.id.btn_speak);
        btnReconizer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                sentence = (TextView) findViewById(R.id.textView);
                sentence.setText("");

                try {
                    //start an activity
                    Intent intent =
                            new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

                    //free form and web search model
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

                    //set a prompt
                    intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Recoginizing...");

                    //change the language to mandarin.
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.CHINA.toString());

                    //start activity
                    startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Can't find any voice equipments", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        sentence = (TextView) findViewById(R.id.textView);
        sentence.setText("");
        boolean flag = false;
        //get data from google voice
        if (requestCode == VOICE_RECOGNITION_REQUEST_CODE && resultCode == RESULT_OK) {
            //obtain phrases and sentences you just said
            ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

            String resultString = "";
            for (int i = 0; i < results.size(); i++) {
                resultString += results.get(i) + "//";
                if (results.get(i).contains("hello world")) {
                    flag = true;
                }
            }

            sentence.setText(resultString);

            if (flag == true) {
                Toast.makeText(this, resultString, Toast.LENGTH_LONG).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
