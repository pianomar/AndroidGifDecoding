package com.photoapp.pianomar.myjcodec;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GifDecoder gifDecoder = new GifDecoder();
        ArrayList arrayList = new ArrayList<>();
        InputStream stream = null;
        try {
            stream = getAssets().open("piggy.gif");
        } catch (IOException e) {
            e.printStackTrace();
        }

        gifDecoder.read(stream, 0);

        for(int index = 0;index < gifDecoder.getFrameCount(); index++) {
            Bitmap bitmap;
            bitmap = gifDecoder.getNextFrame();
            arrayList.add(bitmap);
        }
    }
}
