package com.example.lab6_20203554.StfPuzzleSimplified;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class AndroidUtils {
    public static void setImagenJuego (Context context, Uri imageUri, ImageView imageView){
        Glide.with(context).load(imageUri).into(imageView);
    }
}
