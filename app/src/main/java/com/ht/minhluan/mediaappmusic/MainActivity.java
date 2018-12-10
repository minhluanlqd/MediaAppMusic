package com.ht.minhluan.mediaappmusic;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView txtTitle, txtTimeStart, txtTimeEnd;
    SeekBar skSong;
    ImageButton btnPrev, btnPlay, btnStop, btnNext;
    ImageView imgDisc;
    ArrayList<Song>arraySong;
    int position = 0;
    MediaPlayer mediaPlayer;
    Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        func();
        addSong();

        animation = AnimationUtils.loadAnimation(this,R.anim.disc_rotate);
        startMediaPlayer();

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    btnPlay.setImageResource(R.drawable.play);
                }else {
                    mediaPlayer.start();
                    btnPlay.setImageResource(R.drawable.pause);
                }
                setTimeEnd();
                UpdateTimeSong();
                imgDisc.startAnimation(animation);
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.stop();
                btnPlay.setImageResource(R.drawable.play);
                startMediaPlayer();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                position++;
                if (position > arraySong.size()-1){
                    position = 0;
                }
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                }
                startMediaPlayer();
                mediaPlayer.start();
                btnPlay.setImageResource(R.drawable.pause);
                setTimeEnd();
                UpdateTimeSong();
            }
        });
        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                position--;
                if (position < 0){
                    position = arraySong.size()-1;
                }
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                }
                startMediaPlayer();
                mediaPlayer.start();
                btnPlay.setImageResource(R.drawable.pause);
                setTimeEnd();
                UpdateTimeSong();
            }
        });
        skSong.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(skSong.getProgress());
            }
        });
    }

    private void UpdateTimeSong(){
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
                txtTimeStart.setText(simpleDateFormat.format(mediaPlayer.getCurrentPosition()));
                skSong.setProgress(mediaPlayer.getCurrentPosition());

                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        position++;
                        if (position > arraySong.size()-1){
                            position = 0;
                        }
                        if (mediaPlayer.isPlaying()){
                            mediaPlayer.stop();
                        }
                        startMediaPlayer();
                        mediaPlayer.start();
                        btnPlay.setImageResource(R.drawable.pause);
                        setTimeEnd();
                        UpdateTimeSong();
                    }
                });
                handler.postDelayed(this,1000);
            }
        },100);
    }
    private void setTimeEnd(){
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        txtTimeEnd.setText(simpleDateFormat.format(mediaPlayer.getDuration()));
        skSong.setMax(mediaPlayer.getDuration());
    }

    private void startMediaPlayer(){
        mediaPlayer = MediaPlayer.create(MainActivity.this,arraySong.get(position).getFile());
        txtTitle.setText(arraySong.get(position).getTitle());
    }

    private void addSong(){
        arraySong = new ArrayList<>();
        arraySong.add(new Song("Anh là của em",R.raw.anh_la_cua_em));
        arraySong.add(new Song("Chiều nay không có mưa bay",R.raw.chieu_nay_khong_co_mua_bay));
        arraySong.add(new Song("Cô gái ngày hôm qua",R.raw.co_gai_ngay_hom_qua));
        arraySong.add(new Song("Đi để trở về",R.raw.di_de_tro_ve));
        arraySong.add(new Song("Happy Ending",R.raw.happy_ending));
        arraySong.add(new Song("Lạc nhau phải có muôn đời",R.raw.lac_nhau_phai_co_muon_doi));
        arraySong.add(new Song("Lạc trôi",R.raw.lac_troi));
        arraySong.add(new Song("Nơi này có anh",R.raw.noi_nay_co_anh));
        arraySong.add(new Song("Sau tất cả",R.raw.sau_tat_ca));
        arraySong.add(new Song("Yêu và yêu",R.raw.yeu_va_yeu));
    }

    private void func(){
        txtTitle = findViewById(R.id.textViewName);
        txtTimeStart = findViewById(R.id.textViewStart);
        txtTimeEnd = findViewById(R.id.textViewEnd);
        skSong = findViewById(R.id.seekBar);
        btnPrev = findViewById(R.id.imageButtonPre);
        btnPlay = findViewById(R.id.imageButtonPlay);
        btnStop = findViewById(R.id.imageButtonStop);
        btnNext = findViewById(R.id.imageButtonNext);
        imgDisc = findViewById(R.id.imageView);
    }
}
