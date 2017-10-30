package com.photoapp.pianomar.myjcodec;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.constraint.solver.ArrayLinkedVariables;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.GridLayout;
import android.widget.ImageView;

import com.waynejo.androidndkgif.*;
import com.waynejo.androidndkgif.GifDecoder;

import org.jcodec.api.SequenceEncoder;
import org.jcodec.api.android.AndroidSequenceEncoder;
import org.jcodec.common.io.NIOUtils;
import org.jcodec.common.io.SeekableByteChannel;
import org.jcodec.common.model.Rational;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static org.jcodec.scale.BitmapUtil.fromBitmap;


public class MainActivity extends AppCompatActivity {
    ArrayList arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        arrayList = new ArrayList<>();
        setContentView(R.layout.activity_main);
        com.waynejo.androidndkgif.GifDecoder gifDecoder = new GifDecoder();
        final GifImageIterator iterator = gifDecoder.loadUsingIterator("/storage/emulated/0/Download/icon.gif");
        gifDecoder.frameNum();
        int time = 1;
        while (iterator.hasNext()) {
            GifImage next = iterator.next();
            if (null != next) {
                arrayList.add(next.bitmap);
                Log.e("To --> Bitmap", time + "");
            }
            time++;
        }
        iterator.close();
        convertToVideo();
    }

    private void convertToVideo() {
        SeekableByteChannel out;
        try {
            out = NIOUtils.writableFileChannel("/storage/emulated/0/Download/15.mp4");
            // for Android use: AndroidSequenceEncoder
            AndroidSequenceEncoder sequenceEncoder = new AndroidSequenceEncoder(out, Rational.R(15, 1));
            for (int i = 0; i < arrayList.size(); i++) {
                sequenceEncoder.encodeImage((Bitmap) arrayList.get(i));
                Log.e("Video", i + "");
            }
            sequenceEncoder.finish();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
