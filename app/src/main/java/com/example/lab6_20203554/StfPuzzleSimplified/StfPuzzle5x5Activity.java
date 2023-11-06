package com.example.lab6_20203554.StfPuzzleSimplified;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.lab6_20203554.MainActivity;
import com.example.lab6_20203554.R;
import com.example.lab6_20203554.databinding.ActivityStfPuzzle4x4Binding;
import com.example.lab6_20203554.databinding.ActivityStfPuzzle5x5Binding;

import java.io.File;
import java.util.Random;

public class StfPuzzle5x5Activity extends AppCompatActivity {
    ActivityStfPuzzle5x5Binding binding;
    private int dimension = 5;

    private int emptyX = dimension-1;
    private int emptyY = dimension-1;
    private Button[][] buttons;
    private int[] tiles;
    String userName = Build.USER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStfPuzzle5x5Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.buttonnn1.setBackgroundColor(getResources().getColor(R.color.transparent));
        binding.buttonnn2.setBackgroundColor(getResources().getColor(R.color.transparent));
        binding.buttonnn3.setBackgroundColor(getResources().getColor(R.color.transparent));
        binding.buttonnn4.setBackgroundColor(getResources().getColor(R.color.transparent));
        binding.buttonnn5.setBackgroundColor(getResources().getColor(R.color.transparent));
        binding.buttonnn6.setBackgroundColor(getResources().getColor(R.color.transparent));
        binding.buttonnn7.setBackgroundColor(getResources().getColor(R.color.transparent));
        binding.buttonnn8.setBackgroundColor(getResources().getColor(R.color.transparent));
        binding.buttonnn9.setBackgroundColor(getResources().getColor(R.color.transparent));
        binding.buttonnn10.setBackgroundColor(getResources().getColor(R.color.transparent));
        binding.buttonnn11.setBackgroundColor(getResources().getColor(R.color.transparent));
        binding.buttonnn12.setBackgroundColor(getResources().getColor(R.color.transparent));
        binding.buttonnn13.setBackgroundColor(getResources().getColor(R.color.transparent));
        binding.buttonnn14.setBackgroundColor(getResources().getColor(R.color.transparent));
        binding.buttonnn15.setBackgroundColor(getResources().getColor(R.color.transparent));
        binding.buttonnn16.setBackgroundColor(getResources().getColor(R.color.transparent));
        binding.buttonnn17.setBackgroundColor(getResources().getColor(R.color.transparent));
        binding.buttonnn18.setBackgroundColor(getResources().getColor(R.color.transparent));
        binding.buttonnn19.setBackgroundColor(getResources().getColor(R.color.transparent));
        binding.buttonnn20.setBackgroundColor(getResources().getColor(R.color.transparent));
        binding.buttonnn21.setBackgroundColor(getResources().getColor(R.color.transparent));
        binding.buttonnn22.setBackgroundColor(getResources().getColor(R.color.transparent));
        binding.buttonnn23.setBackgroundColor(getResources().getColor(R.color.transparent));
        binding.buttonnn24.setBackgroundColor(getResources().getColor(R.color.transparent));
        binding.buttonnn25.setBackgroundColor(getResources().getColor(R.color.transparent));

        buttons = new Button[dimension][dimension];

        for (int i = 0; i<binding.tablero5.getChildCount();i++){
            buttons[i/dimension][i%dimension] = (Button) binding.tablero5.getChildAt(i);
            loadAndSetImage(i/dimension, i%dimension,i/dimension,i%dimension);
        }

        tiles = new int[dimension*dimension];
        for (int i = 0; i < binding.tablero5.getChildCount()-1; i++){
            tiles[i] = i+1;
        }

        generateNumbers();

        emptyX = dimension-1;
        emptyY = dimension-1;
        for (int i = 0; i < binding.tablero5.getChildCount() - 1; i++){
            loadAndSetImage((tiles[i]-1)/dimension, (tiles[i]-1)%dimension, i/dimension, i%dimension);
            buttons[i/dimension][i%dimension].setText(String.valueOf(tiles[i]));
        }
        buttons[emptyX][emptyY].setText("");
        buttons[emptyX][emptyY].setBackgroundColor(getResources().getColor(R.color.transparent));
    }
    private void generateNumbers() {
        int r = dimension*dimension-1;
        Random random = new Random();
        while (r>1){
            int randomNum = random.nextInt(r--);
            int temp = tiles[randomNum];
            tiles[randomNum] = tiles[r];
            tiles[r] = temp;
        }
        if (!isSolvable()){
            generateNumbers();
        }
    }

    private boolean isSolvable() {
        int countInversions = 0;
        for (int i = 0; i < dimension*dimension-1; i++){
            for (int j = 0; j < i; j++){
                if (tiles[j]>tiles[i]){
                    countInversions++;
                }
            }
        }
        return countInversions % 2 == 0;
    }

    public void buttonClick(View view) throws InterruptedException {
        Button button = (Button) view;
        int x = button.getTag().toString().charAt(0)-'0';
        int y = button.getTag().toString().charAt(1)-'0';
        Log.d("msg-test", x + " " + y);
        String texto;

        if ((Math.abs(emptyX-x) == 1 && emptyY == y) || (Math.abs(emptyY - y) == 1 && emptyX == x)){
            texto = button.getText().toString();
            buttons[emptyX][emptyY].setText(texto);

            loadAndSetImage((Integer.parseInt(texto)-1)/dimension, (Integer.parseInt(texto)-1)%dimension, emptyX, emptyY);
            button.setText("");
            button.setBackgroundColor(getResources().getColor(R.color.transparent));
            emptyX = x;
            emptyY = y;
            checkWin();
        }
    }

    private void checkWin() throws InterruptedException {
        boolean isWin = false;
        if (emptyX == dimension-1 && emptyY == dimension-1){
            for (int i=0; i<binding.tablero5.getChildCount() - 1; i++){
                Log.d("msg-test","intento win"+i+" "+dimension);
                if (buttons[i/dimension][i%dimension].getText().toString().equals(String.valueOf(i+1))){
                    isWin = true;
                }
                else {
                    isWin = false;
                    break;
                }
            }
        }
        if (isWin){
            Toast.makeText(this, "Felicidades, has ganado el juego. En breve volverás al menú", Toast.LENGTH_SHORT).show();
            Log.d("msg-test","ha ganado");
            for (int i = 0; i<binding.tablero5.getChildCount();i++){
                buttons[i/dimension][i%dimension].setClickable(false);
            }
            clearCache();

            // Retrasa la acción de regresar a la otra actividad
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(StfPuzzle5x5Activity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    finish();
                }
            }, 2000);
        }
    }
    private void clearCache() {
        File cacheDir = getCacheDir();
        File[] cacheFiles = cacheDir.listFiles();
        if (cacheFiles != null) {
            for (File cacheFile : cacheFiles) {
                cacheFile.delete();
            }
        }
    }

    private void loadAndSetImage(int x, int y, int xx, int yy) {
        /*
        FirebaseUtils.getActualRef(userName)
                .child("img3x3/img" + x + "" + y + ".png")
                .getDownloadUrl()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        Uri imageUri = task.getResult();
                        Glide.with(StfPuzzle3x3Activity.this)
                                .load(imageUri)
                                .apply(new RequestOptions()
                                        .diskCacheStrategy(DiskCacheStrategy.NONE))
                                .into(new SimpleTarget<Drawable>() {
                                    @Override
                                    public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                                        buttons[xx][yy].setBackground(resource);
                                    }
                                });
                    } else {
                        Log.d("msg-test", "Falla al obtener la URL de descarga de la imagen");
                    }
                });

         */
        File imageFile = new File(this.getCacheDir(), "images/img"+x+""+y+".png");
        if (imageFile.exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
            Drawable drawable = new BitmapDrawable(getResources(), bitmap);
            buttons[xx][yy].setBackground(drawable);
        }
    }
}