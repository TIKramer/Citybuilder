package com.kramer.a18882652.citybiolder.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.app.Activity;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.kramer.a18882652.citybiolder.Fragments.MapFragment;
import com.kramer.a18882652.citybiolder.Model.GameDataModel;
import com.kramer.a18882652.citybiolder.R;

/* the details screen
        This displays the information passed when a user request details of a strucutre.
            User can here change the name and photo of structure.
 */
public class DetailsActivity extends Activity {
    private static final int REQUEST_THUMBNAIL = 1;
    private Intent thumbnailPhotoIntent;
    private ImageButton imageButton;
    private  Bitmap photo;
    private int row;
    private int col;
    private TextView structureTxt;
    private EditText nameDetails;

    private static final String ROW="ROW";
    private static final String COL="COL";
    private static final String STRUCTURE="STRUCTURE";
    private static final String NAME="NAME";
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
         row = intent.getIntExtra(ROW, 0);
         col = intent.getIntExtra(COL, 0);
        String strucutreType = intent.getStringExtra(STRUCTURE);
        String name = intent.getStringExtra(NAME);


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
                Intent returnData = MapFragment.buildDetailResultIntent(getApplicationContext(),nameDetails.getText().toString(),row,col); // The return intent



                setResult(RESULT_OK, returnData);
            }
            });








    }

    public static Intent getIntent(Context c, int row, int col, String structure, String name)
    {
        Intent newIntent = new Intent(c, DetailsActivity.class);
        newIntent.putExtra(ROW, row);
        newIntent.putExtra(COL, col);
        newIntent.putExtra(STRUCTURE, structure);
        newIntent.putExtra(NAME, name);
        return newIntent;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == REQUEST_THUMBNAIL && resultCode == Activity.RESULT_OK)
        {
             photo = (Bitmap) data.getExtras().get("data");
            imageButton.setImageBitmap(photo);
            //I save one image here at a time temproary - cause couldnt figure out how to pass image through intent
            GameDataModel.getGameData(this).setTemp(photo);
        }
    }



}
