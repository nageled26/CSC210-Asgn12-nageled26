package com.example.dnage.fortunetellergame;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Scene;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    String[]responses;
    EditText input;
    TextView input2;
    TextView showResponse;
    TextView question;
    TextView tell;
    Button get;
    String answer;

    private SensorManager sm;
    private float acelVal;  //current acceleration value and gravity
    private float acelLast; //last acceleraion value and gravity
    private float shake;    //acceleration value differ from gravity


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sm.registerListener(sensorListener,sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_NORMAL);

        acelVal = SensorManager.GRAVITY_EARTH;
        acelLast = SensorManager.GRAVITY_EARTH;
        shake=0.00f;

        responses=new String[]{"It is certain", "It is decidedly so","Without a doubt","Yes definitely","You may rely on it","As I see it, yes",
                "Most likely","Outlook good","Yes","Signs point to yes","Reply hazy try again","Ask again later", "Better not tell you now",
                "Cannot predict now","Concentrate and ask again","Don't count on it","My reply is no","My sources say no","Outlook not so good",
                "Very doubtful"};
        input=(EditText)findViewById(R.id.input);
        input2=(TextView)findViewById(R.id.input2);
        get=(Button)findViewById(R.id.button);
        showResponse=(TextView)findViewById(R.id.response);
        question=(TextView)findViewById(R.id.question);
        tell=(TextView)findViewById(R.id.shake);
        tell.setAlpha(0.0f);
        start();
        get.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //When the button is clicked
                if(get.getText().equals("Enter")) {
                    question.setAlpha(0.0f);
                    input2.setAlpha(1.0f);
                    input2.setText(input.getText());
                    input.setText("");
                    input.setAlpha(0.0f);
                    get.setEnabled(false);
                    tell.setAlpha(1.0f);
                }
                else{
                    start();
                    get.setText("Enter");
                }
            }
        });

    }

    public void start(){
        input2.setAlpha(0.0f);
        input2.setText("");
        input.setText("");
        input.setAlpha(1.0f);
        question.setAlpha(1.0f);
        showResponse.setText("");
        answer=responses[(int)(Math.random()*responses.length)];
        input2.setAlpha(0.0f);
    }
    private final SensorEventListener sensorListener= new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            float x = sensorEvent.values[0];
            float y  = sensorEvent.values[1];
            float z = sensorEvent.values[2];

            acelLast=acelVal;
            acelVal=(float) Math.sqrt((double)x*x+y*y+z*z);

            float delta=acelVal-acelLast;
            shake=shake*0.9f+delta;

            if(shake>12){
                //Toast toast = Toast.makeText(getApplicationContext(),"Do not share me",Toast.LENGTH_LONG);
                //toast.show();
                showResponse.setText(answer);
                tell.setAlpha(0.0f);
                get.setText("Restart");
                get.setEnabled(true);
            }

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };
}
