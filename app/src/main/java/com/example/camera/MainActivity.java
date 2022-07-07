package com.example.camera;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    ImageView imgFoto;
    Button btnFoto;

    //static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 0);
        }

        imgFoto = (ImageView) findViewById(R.id.imgFoto);
        btnFoto = (Button) findViewById(R.id.btnFoto);

        btnFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tirarfoto();
                /*Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                if(intent.resolveActivity(getPackageManager()) != null){
                    startActivityForResult(intent, );
                }*/
            }
        });
    }

    private void tirarfoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1 && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap imagem = (Bitmap) extras.get("data");
            imgFoto.setImageBitmap(imagem);
            //Bitmap imagem = (Bitmap) extras.get("data");
            /*Bitmap bitmap = CarregadorDeFoto.carrega("data");
            imgFoto.setImageBitmap(bitmap);*/
            //Bitmap bmRotated = rotateBitmap(imagem, ORIENTATION_FLIP_VERTICAL);

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public static class CarregadorDeFoto {
        public static void carrega(Bundle extras) throws IOException {
            ExifInterface exif = new ExifInterface("data");
            String orientacao = exif.getAttribute(ExifInterface.TAG_ORIENTATION);
            int codigoOrientacao = Integer.parseInt(orientacao);

            switch (codigoOrientacao) {
                // rotaciona 0 graus no sentido horário case
                case ExifInterface.ORIENTATION_NORMAL:
                    break;
                // rotaciona 90 graus no sentido horário case
                case ExifInterface.ORIENTATION_ROTATE_90:
                    break;
                // rotaciona 180 graus no sentido horário case
                case ExifInterface.ORIENTATION_ROTATE_180:
                    break;
                // rotaciona 270 graus no sentido horário
                case ExifInterface.ORIENTATION_ROTATE_270:
                    break;
            }
        }
        private Bitmap abreFotoERotaciona(String caminhoFoto, int angulo) {
            // Abre o bitmap a partir do caminho da foto Bitmap
            Bitmap bitmap = BitmapFactory.decodeFile(caminhoFoto);

            // Prepara a operação de rotação com o ângulo escolhido
            Matrix matrix = new Matrix(); matrix.postRotate(angulo);

            // Cria um novo bitmap a partir do original já com a rotação aplicada
            return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        }
    }
}