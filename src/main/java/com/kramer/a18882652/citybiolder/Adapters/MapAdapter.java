package com.kramer.a18882652.citybiolder.Adapters;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.kramer.a18882652.citybiolder.Activities.DetailsActivity;
import com.kramer.a18882652.citybiolder.Model.GameDataModel;
import com.kramer.a18882652.citybiolder.Model.MapElement;
import com.kramer.a18882652.citybiolder.Model.Commercial;
import com.kramer.a18882652.citybiolder.Model.DefaultStructure;
import com.kramer.a18882652.citybiolder.Model.Residential;
import com.kramer.a18882652.citybiolder.Model.Road;
import com.kramer.a18882652.citybiolder.R;
import com.kramer.a18882652.citybiolder.Model.Structure;
import com.kramer.a18882652.citybiolder.Model.StructureData;
/* the map adapter handles displaying the elements onto the RecyclerView of the MapFragment

 */
public class MapAdapter extends RecyclerView.Adapter<MapAdapter.GridHolder>
{

    private MapElement[][] elements;
    private Activity activity;
    StructureUpdateListener structureUpdateListener;
    PlayerCashCallBack cashCallBack;
    private int nResidential;
    private  int nCommerical;
    private int nRoads;
    private Fragment fragment;
    private boolean demolishion;
    private boolean detailsMode = false;
    private final Structure defaultStructure = StructureData.getGameData().getDefault();
    private int mapWidth;
    private int mapHeight;

    //This is called to set the adapter into details mode.
        //Then the viewholders know to display details if clicked on
    public void setDetailsMode(boolean detailsMode) {
        this.detailsMode = detailsMode;
    }
    //Same but demo mode
    public void setDemolish(boolean value)
    {
        demolishion = value;
    }


    /* used to set each structure value
        used in this application to set the structure values when a exsiting map is passed in
     */

    public void setStructureValues(int nResidential, int nCommerical, int nRoads)
    {
        this.nResidential = nResidential;
        this.nCommerical = nCommerical;
        this.nRoads = nRoads;
        structureUpdateListener.commercialUpdateListener(nCommerical);
        structureUpdateListener.residentialUpdateListener(nResidential);
        structureUpdateListener.roadsUpdateListener(nRoads);


    }
    /* A grid holder is one structure on the map */

    public class GridHolder extends RecyclerView.ViewHolder
    {
        public ImageView imageView;
        public GridHolder( View view )
        {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.imageView);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int row = getAdapterPosition() % mapWidth;
                    int col = getAdapterPosition() / mapWidth;
                    /* Check if in demolish mode or in details mode
                            if a structure is clicked while in demolish mode - replace structre with default
                            if a strucutre is clicked while in details mode -launch the details screen.
                     */
                    if(!(getElementAtThisLocation().getStructure() instanceof DefaultStructure)) {
                        if (demolishion) {

                            removeFromLocation(row, col);
                            imageView.setImageResource(defaultStructure.getImageID());
                        } else if (detailsMode) {


                                Intent newIntent = DetailsActivity.getIntent(fragment.getContext(), row,col,elements[row][col].getStructure().getClass().getSimpleName(), elements[row][col].getOwnerName());

                            fragment.startActivityForResult(newIntent, 1);


                        }
                    }
                }
            });

            imageView.setOnDragListener(new View.OnDragListener() {


                public boolean onDrag(View v, DragEvent event) {

                    // Defines a variable to store the action type for the incoming event
                    final int action = event.getAction();

                    // Handles each of the expected events
                    switch(action) {


                        //Only default structures can be placed on
                        //So only allowing DefaultStructures to listen to drag events
                        case DragEvent.ACTION_DRAG_STARTED:

                            int row = getAdapterPosition() % mapWidth;
                            int col = getAdapterPosition()/ mapWidth;

                            if(elements[row][col].getStructure() instanceof DefaultStructure)
                                return true;
                            else
                                return false;
                            //If a drag has entered this object - put blue filiter
                                //indicating that it can take an item
                        case DragEvent.ACTION_DRAG_ENTERED:

                            imageView.setColorFilter(Color.BLUE);
                            imageView.invalidate();

                            return true;

                        // When the drag exits this object reset its color filter
                        case DragEvent.ACTION_DRAG_EXITED:

                            imageView.clearColorFilter();
                            imageView.invalidate();

                            return true;
                        case DragEvent.ACTION_DRAG_LOCATION:

                            // Nothing to do so just return true -cant remove this case or DragEven class produces errors
                            return true;
                        //When a drag is released on this view
                        case DragEvent.ACTION_DROP:

                            // Gets the item containing the dragged data
                            ClipData.Item item = event.getClipData().getItemAt(0);
                            ClipData.Item item2 = event.getClipData().getItemAt(1);

                            // Gets the text data from the item.
                            CharSequence  dragData = item.getText();
                            CharSequence  dragData2 = item2.getText();

                            MapElement viewElement = getElementAtThisLocation();

                            if(viewElement.getStructure() == defaultStructure) {
                                Structure newStruct = StructureData.getGameData().getByNameAndId(dragData.toString(), Integer.parseInt(dragData2.toString()));

                                if(newStruct instanceof  Road ||isNextToRoad()  ) {

                                    if (cashCallBack.getCash() >= newStruct.getCost()) {

                                        imageView.setImageResource(newStruct.getImageID());
                                        saveStructureToElement(newStruct);
                                    } else {
                                        Toast.makeText(activity, "Not enough funds ", Toast.LENGTH_LONG).show();

                                    }
                                }

                            }
                            else
                            {
                                Toast.makeText(activity, "Can't build over an existing structure! ", Toast.LENGTH_LONG).show();

                            }
                            return true;

                        case DragEvent.ACTION_DRAG_ENDED:

                            imageView.clearColorFilter();
                            imageView.invalidate();

                            return true;

                        // An unknown action type was received.
                        default:
                            Log.e("DragDrop Example","Unknown action type received by OnDragListener.");
                            break;
                    }

                    return false;
                }
            });





        }
/* bad hack way of checking for next road
     cant be bothered fixing it atm
     / issue my width hight being mixed
     //First eveything in row is listed then for every set of rows listed column is incremented.
        the grid elements going vertical are rows + mapHeight or -mapHeight
 */
        public boolean isNextToRoad()
        {
            getAdapterPosition();
            int row = this.getAdapterPosition() % mapWidth;
            int col = this.getAdapterPosition()/ mapWidth;
            boolean nextToRaod = false;
//check height
                for(int i = row-1; i <=row+1; i++)
                {
                    if((i >= 0 && i < mapWidth))
                    {

                            if (elements[i][col].getStructure() instanceof Road) {
                                nextToRaod = true;

                        }
                    }

            }

                if(row -mapHeight >= 0)
                {
                    if (elements[row-mapHeight][col].getStructure() instanceof Road) {
                        nextToRaod = true;

                    }
                }
                else
                {                  //   50  +    //4-12
                    int newRow = mapWidth+(row-mapHeight);
                    if(col >0) {
                        if (elements[mapWidth + (row - mapHeight)][col - 1].getStructure() instanceof Road) {
                            nextToRaod = true;

                        }
                    }
                }
             if(row +mapHeight < mapWidth)
            {
               // (50 + 8 - 12
                if (elements[mapHeight+(row)][col].getStructure() instanceof Road) {
                    nextToRaod = true;

                }
            }
            else{
                                //50 - 42 +12 (42 + 12)mod 50
                 if (elements[((row+mapHeight) % mapWidth)][col].getStructure() instanceof Road) {
                     nextToRaod = true;

                 }
             }


            return nextToRaod;
        }
//Saving the structure to element
        //do this every time a structure is changed.
        public void saveStructureToElement(Structure structure)
        {

            MapAdapter.this.updateImage(this.getAdapterPosition(), structure);
        }
//Just a call to get the element at the gridHolders location

        public MapElement getElementAtThisLocation()
        {
            int row = this.getAdapterPosition() % mapWidth;
            int col = this.getAdapterPosition()/ mapWidth;
            return elements[row][col];
        }

//Bind/display the map elements image
        //if the eleemnt has a bitmap set display that instead of the strucutre image
        public void bind(MapElement element) {
            if(element.getImage() == null) {
                if (element.getStructure().getImageID() != 0) {
                    imageView.setImageResource(element.getStructure().getImageID());
                }
            }
            else
            {
                imageView.setImageBitmap(element.getImage());
            }
        }
    }
//Update the strucutre of a element at the adapters postion
    //Called when a gridholder wants to save its new structure.
    private void updateImage(int adapterPosition, Structure structure) {

        int row = adapterPosition % mapWidth;
        int col = adapterPosition/ mapWidth;
        updateStructure(row, col,structure);


    }



    public MapAdapter(Activity activity, MapElement[][] elements, Fragment fragment)
    {
        this.elements = elements;
        this.activity = activity;
        this.fragment = fragment;
        this.mapWidth = elements.length;
        this.mapHeight = elements[0].length;
    }


    @Override
    public MapAdapter.GridHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view;

        view = LayoutInflater.from(activity).inflate(R.layout.fragment_grid, parent, false);
        view.getLayoutParams().height = parent.getMeasuredHeight() / mapHeight + 1;
        view.getLayoutParams().width = parent.getMeasuredHeight() / mapHeight + 1;



        return new MapAdapter.GridHolder(view);

    }

    @Override
    public void onBindViewHolder(GridHolder vh, int index)
    {
        int row = index % mapWidth;
        int col = index/ mapWidth;
        vh.bind(elements[row][col]);
    }


    @Override
    public int getItemCount()
    {
        return elements.length * elements[0].length;
    }


    public void removeFromLocation(int x, int y)
    {
        elements[x][y].setStructure(defaultStructure);

    }


    public void setStructureUpdateListener(StructureUpdateListener callBack)
    {
        this.structureUpdateListener = callBack;
    }
    public void setCashCallBack(PlayerCashCallBack callBack)
    {
        this.cashCallBack = callBack;
    }


/* Replaces the old strucutre of a map element with the new one provide
    updates the total amount of the type of structure
    calls the listeners with the update listener methods
 */
    public void updateStructure(int x, int y, Structure structure)
    {
        Structure temp = elements[x][y].getStructure();

        if(temp instanceof Road)
        {
            nRoads--;
            this.structureUpdateListener.roadsUpdateListener(nRoads);
        }
        else if(temp instanceof Residential)
        {
            nResidential--;
            this.structureUpdateListener.residentialUpdateListener(nResidential);

        }
        else if(temp instanceof Commercial)
        {
            nCommerical--;
            this.structureUpdateListener.commercialUpdateListener(nCommerical);

        }

        if(structure instanceof Road)
        {
            nRoads++;
            this.structureUpdateListener.roadsUpdateListener(nRoads);
        }
        else if(structure instanceof  Residential)
        {
            nResidential++;
            this.structureUpdateListener.residentialUpdateListener(nResidential);

        }
        else if(structure instanceof  Commercial)
        {
            nCommerical++;
            this.structureUpdateListener.commercialUpdateListener(nCommerical);
        }
        elements[x][y].setStructure(structure);

    }





/* Update using the intent recived from the Map Fragment */
// The intent can't be recived here so once map fragment recives it - passes the data here to be handled.
    public void updateUsingIntent(int row, int col, String name)
    {
        MapElement element = elements[row][col];
        if(name.length() >0) {
            element.setOwnerName(name);
        }
       element.setBitmap(GameDataModel.getGameData(activity).getTemp());

    }

    public MapElement[][] getElements() {
        return elements;
    }


//Call backs - called to update the listeners so they too can have the information
    //Used mostly by GameActivity

    public interface StructureUpdateListener
    {
        void roadsUpdateListener(int x);
        void commercialUpdateListener(int x);
        void residentialUpdateListener(int x);

    }

    public interface PlayerCashCallBack{
        Integer getCash();
    }


}



