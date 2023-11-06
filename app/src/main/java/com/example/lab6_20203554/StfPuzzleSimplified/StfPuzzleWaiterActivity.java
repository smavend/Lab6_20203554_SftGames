package com.example.lab6_20203554.StfPuzzleSimplified;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.example.lab6_20203554.R;
import com.example.lab6_20203554.databinding.ActivityStfPuzzleWaiterBinding;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StfPuzzleWaiterActivity extends AppCompatActivity {
    ActivityStfPuzzleWaiterBinding binding;
    Intent intent;
    Intent intent1;
    String imagePath;
    String [] direcciones;
    int successfulUploads;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStfPuzzleWaiterBinding.inflate(getLayoutInflater());

        binding.button3x3.setClickable(true);
        binding.button4x4.setClickable(true);
        binding.button5x5.setClickable(true);

        setContentView(binding.getRoot());
        intent = getIntent();
        Uri imagenCompleta = intent.getParcelableExtra("uriImg");
        AndroidUtils.setImagenJuego(this,imagenCompleta, binding.imageView);

        binding.button3x3.setOnClickListener(view ->{
            Toast.makeText(this, "Cargando nuevo juego", Toast.LENGTH_SHORT).show();
            direcciones = new String[9];
            cortarYGuardarStorage(3, imagenCompleta);
        });
        binding.button4x4.setOnClickListener(view ->{
            Toast.makeText(this, "Cargando nuevo juego", Toast.LENGTH_SHORT).show();
            direcciones = new String[16];
            cortarYGuardarStorage(4, imagenCompleta);
        });
        binding.button5x5.setOnClickListener(view ->{
            Toast.makeText(this, "Cargando nuevo juego", Toast.LENGTH_SHORT).show();
            direcciones = new String[25];
            cortarYGuardarStorage(5, imagenCompleta);
        });
    }

    private void cortarYGuardarStorage(int gridSize, Uri imagenCompleta) {
        binding.button3x3.setClickable(false);
        binding.button4x4.setClickable(false);
        binding.button5x5.setClickable(false);

        try {
            Bitmap originalBitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imagenCompleta));
            int width = originalBitmap.getWidth();
            int height = originalBitmap.getHeight();
            int squareSize = width / gridSize;

            successfulUploads = 0;

            for (int i = 0; i < gridSize; i++) {
                for (int j = 0; j < gridSize; j++) {
                    Bitmap squareBitmap = Bitmap.createBitmap(originalBitmap, j * squareSize, i * squareSize, squareSize, squareSize);

                    File cachePath = new File(this.getCacheDir(), "images");
                    cachePath.mkdirs(); // Crea la carpeta si no existe

                    // Crea un archivo temporal en la carpeta de caché
                    File outputFile = new File(cachePath, "img"+i+""+j+".png");
                    FileOutputStream outputStream = new FileOutputStream(outputFile);
                    squareBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                    outputStream.flush();
                    outputStream.close();

                    imagePath = outputFile.getAbsolutePath();


                    Uri squareUri = Uri.fromFile(outputFile);
                    StorageReference fileRef = FirebaseUtils.getActualRef(Build.USER).child("img" + gridSize + "x" + gridSize).child("img" + i + "" + j + ".png");
                    fileRef.putFile(squareUri)
                            .addOnSuccessListener(taskSnapshot -> {
                                successfulUploads++;
                                if (successfulUploads == gridSize * gridSize) {
                                    if (gridSize == 3){
                                        intent1 = new Intent(StfPuzzleWaiterActivity.this, StfPuzzle3x3Activity.class);

                                    } else if (gridSize == 4){
                                        intent1 = new Intent(StfPuzzleWaiterActivity.this, StfPuzzle4x4Activity.class);
                                    } else {
                                        intent1 = new Intent(StfPuzzleWaiterActivity.this, StfPuzzle5x5Activity.class);
                                    }
                                    intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent1);
                                }
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(this, "No se pudo iniciar el juego. Inténtalo más tarde", Toast.LENGTH_SHORT).show();
                            });
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}