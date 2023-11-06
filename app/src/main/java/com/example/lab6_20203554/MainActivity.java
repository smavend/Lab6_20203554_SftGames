package com.example.lab6_20203554;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.lab6_20203554.StfPuzzleSimplified.AndroidUtils;
import com.example.lab6_20203554.StfPuzzleSimplified.FirebaseUtils;
import com.example.lab6_20203554.StfPuzzleSimplified.StfPuzzle3x3Activity;
import com.example.lab6_20203554.StfPuzzleSimplified.StfPuzzle4x4Activity;
import com.example.lab6_20203554.StfPuzzleSimplified.StfPuzzle5x5Activity;
import com.example.lab6_20203554.StfPuzzleSimplified.StfPuzzleWaiterActivity;
import com.example.lab6_20203554.databinding.ActivityMainBinding;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.io.File;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    Uri uriImgJuego;
    Intent intent;
    Intent intent1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.slideCard.setOnClickListener(view ->{
            File imageFile = new File(this.getCacheDir(), "images");
            File imageFile5 = new File(this.getCacheDir(), "images/img44.png");
            File imageFile4 = new File(this.getCacheDir(), "images/img33.png");
            File imageFile3 = new File(this.getCacheDir(), "images/img22.png");
            if (imageFile.exists()) {
                File imageFile1 = new File(this.getCacheDir(), "images/img44.png");
                if (imageFile5.exists()){
                    intent1 = new Intent(MainActivity.this, StfPuzzle5x5Activity.class);
                } else if (imageFile4.exists()) {
                    intent1 = new Intent(MainActivity.this, StfPuzzle4x4Activity.class);
                } else {
                    intent1 = new Intent(MainActivity.this, StfPuzzle3x3Activity.class);
                }
                startActivity(intent1);
            }
            MaterialAlertDialogBuilder alertDialog = new MaterialAlertDialogBuilder(this);
            alertDialog.setTitle("Cargar imagen");
            alertDialog.setMessage("Deberá seleccionar una imagen para comenzar el juego.");
            alertDialog.setPositiveButton("Seleccionar", (dialogInterface, i) -> ImagePicker.with(MainActivity.this)
                    .cropSquare()
                    .compress(512)
                    .maxResultSize(512,512)
                    .createIntent(intent -> {
                        imgPerfilLauncher.launch(intent);
                        return null;
                    }));
            alertDialog.setNegativeButton("Volver al menú", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            alertDialog.show();
            //Intent intent = new Intent(MainActivity.this, StfPuzzle3x3Activity.class);
            //startActivity(intent);
        });
    }
    ActivityResultLauncher<Intent> imgPerfilLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if(result.getResultCode()== Activity.RESULT_OK){
            Intent data = result.getData();
            if(data!=null && data.getData()!=null){
                uriImgJuego = data.getData();
                intent = new Intent(MainActivity.this, StfPuzzleWaiterActivity.class);
                intent.putExtra("uriImg", uriImgJuego);
                startActivity(intent);
            }
        }
    });
}