package com.spotyplayer.ui;

import android.media.MediaPlayer;
import android.media.audiofx.Visualizer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.spotyplayer.R;

public class PlayerActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private Visualizer visualizer;
    private AudioVisualizerView visualizerView;
    private TextView titleText, artistText;
    private ImageView albumArt, btnPlayPause, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        titleText = findViewById(R.id.playerTitle);
        artistText = findViewById(R.id.playerArtist);
        albumArt = findViewById(R.id.playerAlbumArt);
        btnPlayPause = findViewById(R.id.btnPlayPause);
        btnBack = findViewById(R.id.btnBack);
        visualizerView = findViewById(R.id.audioVisualizer);

        String path = getIntent().getStringExtra("song_path");
        String title = getIntent().getStringExtra("song_title");
        String artist = getIntent().getStringExtra("song_artist");
        String artUri = getIntent().getStringExtra("album_art");

        titleText.setText(title);
        artistText.setText(artist);

        Glide.with(this).load(artUri).placeholder(R.drawable.ic_music_note).into(albumArt);

        playSong(path);

        btnBack.setOnClickListener(v -> onBackPressed());
        
        btnPlayPause.setOnClickListener(v -> {
            if (mediaPlayer != null) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    btnPlayPause.setImageResource(R.drawable.ic_music_note);
                } else {
                    mediaPlayer.start();
                    btnPlayPause.setImageResource(R.drawable.ic_music_note);
                }
            }
        });
    }

    private void playSong(String path) {
        try {
            mediaPlayer = MediaPlayer.create(this, Uri.parse(path));
            if (mediaPlayer != null) {
                mediaPlayer.start();
                setupVisualizer();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupVisualizer() {
        try {
            visualizer = new Visualizer(mediaPlayer.getAudioSessionId());
            visualizer.setCaptureSize(Visualizer.getCaptureSizeRange()[1]);
            visualizer.setDataCaptureListener(new Visualizer.OnDataCaptureListener() {
                @Override
                public void onWaveFormDataCapture(Visualizer visualizer, byte[] waveform, int samplingRate) {
                    if (visualizerView != null) {
                        visualizerView.updateVisualizer(waveform);
                    }
                }

                @Override
                public void onFftDataCapture(Visualizer visualizer, byte[] fft, int samplingRate) {}
            }, Visualizer.getMaxCaptureRate() / 2, true, false);
            visualizer.setEnabled(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (visualizer != null) {
            visualizer.release();
            visualizer = null;
        }
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}