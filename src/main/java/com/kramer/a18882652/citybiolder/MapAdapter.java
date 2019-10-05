package com.kramer.a18882652.citybiolder;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipDescription;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

public class MapAdapter extends RecyclerView.Adapter<MapAdapter.GridHolder>
{

    private MapElement[][] elements;
    private Activity activity;
    public class GridHolder extends RecyclerView.ViewHolder
    {
        public ImageView imageView;
        public GridHolder( View view )
        {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.imageView);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Structure structure = StructureData.getGameData().getResidential(2);
                    imageView.setImageResource(structure.getImageID());
                    saveData(structure);

                }
            });

            imageView.setOnDragListener(new View.OnDragListener() {


                public boolean onDrag(View v, DragEvent event) {

                    // Defines a variable to store the action type for the incoming event
                    final int action = event.getAction();

                    // Handles each of the expected events
                    switch(action) {

                        case DragEvent.ACTION_DRAG_STARTED:

                            // Determines if this View can accept the dragged data
                            if (event.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {

                                // As an example of what your application might do,
                                // applies a blue color tint to the View to indicate that it can accept
                                // data.

                                // Invalidate the view to force a redraw in the new tint
                                imageView.invalidate();

                                // returns true to indicate that the View can accept the dragged data.
                                return true;

                            }

                            // Returns false. During the current drag and drop operation, this View will
                            // not receive events again until ACTION_DRAG_ENDED is sent.
                            return true;

                        case DragEvent.ACTION_DRAG_ENTERED:

                            // Applies a green tint to the View. Return true; the return value is ignored.

                            imageView.setColorFilter(Color.BLUE);


                            // Invalidate the view to force a redraw in the new tint
                            imageView.invalidate();

                            return true;

                        case DragEvent.ACTION_DRAG_LOCATION:

                            // Ignore the event
                            return true;

                        case DragEvent.ACTION_DRAG_EXITED:

                            // Re-sets the color tint to blue. Returns true; the return value is ignored.
                            imageView.clearColorFilter();

                            // Invalidate the view to force a redraw in the new tint
                            imageView.invalidate();

                            return true;

                        case DragEvent.ACTION_DROP:

                            // Gets the item containing the dragged data
                            ClipData.Item item = event.getClipData().getItemAt(0);
                            ClipData.Item item2 = event.getClipData().getItemAt(1);

                            // Gets the text data from the item.
                            CharSequence  dragData = item.getText();
                            CharSequence  dragData2 = item2.getText();


                            // Displays a message containing the dragged data.
                            Toast.makeText(activity, "Dragged data is " + dragData.toString() + ": " + dragData2, Toast.LENGTH_LONG).show();

                            // Turns off any color tints
                            imageView.clearColorFilter();
                            Structure newStruct = StructureData.getGameData().getByNameAndId(Integer.parseInt(dragData.toString()), Integer.parseInt(dragData2.toString()));
                            imageView.setImageResource(newStruct.getImageID());
                            saveData(newStruct);
                            // Invalidates the view to force a redraw
                            imageView.invalidate();

                            // Returns true. DragEvent.getResult() will return true.
                            return true;

                        case DragEvent.ACTION_DRAG_ENDED:

                            // Turns off any color tinting
                            imageView.clearColorFilter();

                            // Invalidates the view to force a redraw
                            imageView.invalidate();

                            // Does a getResult(), and displays what happened.
                            if (event.getResult()) {
                              //  Toast.makeText(activity, "The drop was handled.", Toast.LENGTH_LONG).show();

                            } else {
                              //  Toast.makeText(activity, "The drop didn't work.", Toast.LENGTH_LONG).show();

                            }

                            // returns true; the value is ignored.
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

        public void saveData(Structure structure)
        {
            MapAdapter.this.updateImage(this.getAdapterPosition(), structure);
        }




        public void bind(MapElement element) {
            if(element.getStructure().getImageID() != 0) {
                imageView.setImageResource(element.getStructure().getImageID());
            }
        }
    }

    private void updateImage(int adapterPosition, Structure structure) {
        int row = adapterPosition % 15;
        int col = adapterPosition/ 15;
        elements[row][col].setStructure(structure);

    }



    public MapAdapter(Activity activity, MapElement[][] elements)
    {
        this.elements = elements;
        this.activity = activity;
    }

    @Override
    public MapAdapter.GridHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view;

        view = LayoutInflater.from(activity).inflate(R.layout.fragment_grid, parent, false);
        view.getLayoutParams().height = parent.getMeasuredHeight() / 10 +1;
        view.getLayoutParams().height = parent.getMeasuredHeight() / 10 + 1;


        return new MapAdapter.GridHolder(view);

    }

    @Override
    public void onBindViewHolder(GridHolder vh, int index)
    {
        int row = index % 15;
        int col = index/ 15;
        vh.bind(elements[row][col]);
    }


    @Override
    public int getItemCount()
    {
        return elements.length * elements[0].length;
    }


    public void removeFromLocation(int x, int y)
    {
        elements[x][y].setStructure(StructureData.getGameData().getResidential(2));
        notifyDataSetChanged();

    }

}



