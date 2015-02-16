package project.vichita.testmediaplayer;

import android.annotation.TargetApi;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends ActionBarActivity {

    Button playBtn,halfBtn,oneBtn,oneHalfBtn,twoBtn;
    boolean isPlaying = false;
    float rate;
    SoundPool soundPool;
    static int streamId;
    int soundId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        playBtn = (Button) findViewById(R.id.play);
        halfBtn = (Button) findViewById(R.id.halfx);
        oneBtn = (Button) findViewById(R.id.onex);
        oneHalfBtn = (Button) findViewById(R.id.onehalfx);
        twoBtn = (Button) findViewById(R.id.twox);

        rate = 1f;

        if (Build.VERSION.SDK_INT >= 21){
            soundPool = new  SoundPool.Builder().build();
        }else{
            soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC,0);

        }


        soundId = soundPool.load(this,R.raw.redshoes,1);

        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                streamId = soundPool.play(soundId,1,1,1,-1,rate);
//                soundPool.setLoop(streamId,-1);
                isPlaying = true;
                playBtn.setText("Pause");
            }
        });

        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play();
            }
        });

        halfBtn.setOnClickListener(new RateOnClick());
        oneBtn.setOnClickListener(new RateOnClick());
        oneHalfBtn.setOnClickListener(new RateOnClick());
        twoBtn.setOnClickListener(new RateOnClick());
    }

    private class RateOnClick implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.halfx)
                rate = 0.5f;
            else if (v.getId() == R.id.onex)
                rate = 1f;
            else if (v.getId() == R.id.onehalfx)
                rate = 1.5f;
            else if (v.getId() == R.id.twox)
                rate = 2f;

            changeRate();
        }
    }

    private void play(){
        if (isPlaying){
            Log.d("Audio","Pause stream id:"+streamId);
            soundPool.pause(streamId);
            isPlaying = false;
            playBtn.setText("Play");
        }else{
            soundPool.resume(streamId);
            isPlaying = true;
            playBtn.setText("Pause");
        }
    }

    private void changeRate(){
        soundPool.setRate(streamId,rate);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        soundPool.release();
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
