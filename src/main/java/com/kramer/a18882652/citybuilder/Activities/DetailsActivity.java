package com.kramer.a18882652.citybuilder.Activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.kramer.a18882652.citybuilder.Model.GameDataModel;
import com.kramer.a18882652.citybuilder.R;


public class DetailsActivity extends Activity {
    private static final int REQUEST_THUMBNAIL = 1;
    private Intent thumbnailPhotoIntent;
    private ImageButton imageButton;
    private  Bitmap photo;
    private int row;
    private int col;
    private TextView structureTxt;
    private EditText nameDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_details);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = (int) (dm.widthPixels * 0.80);
        int height = (int) (dm.heightPixels *0.80);

        getWindow().setLayout(width,height);



        Button saveBtn = (Button) findViewById(R.id.saveBtn);
        TextView xText = (TextView) findViewById(R.id.xTxt) ;
         nameDetails =  findViewById(R.id.nameInput) ;

        TextView yText = (TextView) findViewById(R.id.xTxt2) ;
         structureTxt = (TextView) findViewById(R.id.structureTxt) ;
         imageButton = (ImageButton) findViewById(R.id.imageButton);
        //Get the values from the intent that called this one
        Intent intent = getIntent();
         row = intent.getIntExtra("row", 0);
         col = intent.getIntExtra("col", 0);
        String strucutreType = intent.getStringExtra("structure");
        String name = intent.getStringExtra("name");


        xText.setText("Row : " + row);
        yText.setText("Col : " + col);

        structureTxt.setText(strucutreType);
        nameDetails.setText(name);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                thumbnailPhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                startActivityForResult(thumbnailPhotoIntent, REQUEST_THUMBNAIL);





            }
        });
//When you click save set result to be sent back
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnData = new Intent(); // The return intent

                returnData.putExtra("name", nameDetails.getText().toString()); //change this value
                returnData.putExtra("image", photo);
                returnData.putExtra("row", row);
                returnData.putExtra("col", col);


                setResult(RESULT_OK, returnData);
            }
            });








    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == REQUEST_THUMBNAIL && resultCode == Activity.RESULT_OK)
        {
             photo = (Bitmap) data.getExtras().get("data");
            imageButton.setImageBitmap(photo);
            GameDataModel.getGameData(this).setTemp(photo);
        }
    }



}
