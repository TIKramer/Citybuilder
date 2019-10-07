package com.kramer.a18882652.citybiolder.Adapters;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipDescription;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.kramer.a18882652.citybiolder.R;
import com.kramer.a18882652.citybiolder.Model.Structure;

import java.util.List;

public class SelectorAdapter extends RecyclerView.Adapter<SelectorAdapter.StructureHolder>
{

    private List<? extends Structure> elements;
    private Activity activity;
    private int structureType;
    public class StructureHolder extends RecyclerView.ViewHolder
    {
        View view;
        private String msg;
        public ImageView imageView;
        public StructureHolder(@NonNull final View view )
        {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.strucutreImage);
            this.view = view;
            imageView.setTag("Structure");


            /*
             * On a long click of a strucutre - start a drag
             * Clip data is the data sent to drag listeners
             * Create a shadow that represents what the drag looks like(How it appears under the finger)
             *
             *
             * */
            imageView.setOnLongClickListener(new View.OnLongClickListener() {

                // Defines the one method for the interface, which is called when the View is long-clicked
                public boolean onLongClick(View v) {

                    ClipData.Item item = new ClipData.Item("" + structureType);
                    ClipData.Item item2 = new ClipData.Item("" + getAdapterPosition());

                    String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};

                    ClipData dragData = new ClipData(imageView.getTag().toString(),mimeTypes, item);
                    dragData.addItem(item2);
                    View.DragShadowBuilder myShadow = new View.DragShadowBuilder(imageView);

                    v.startDrag(dragData,myShadow,null,0);
                    return true;


                }
            });

        }





        public void bind(Structure element) {
                imageView.setImageResource(element.getImageID());

        }
    }



    public SelectorAdapter(Activity activity, List<? extends Structure> elements, int structureType)
    {
        this.elements = elements;
        this.activity = activity;
        this.structureType = structureType;
    }

    @Override
    public StructureHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view;

        view = LayoutInflater.from(activity).inflate(R.layout.individual_structure_selector, parent, false);



        return new StructureHolder(view);

    }

    @Override
    public void onBindViewHolder(StructureHolder vh, int index)
    {

        vh.bind(elements.get(index));
    }


    @Override
    public int getItemCount()
    {
        return elements.size();
    }


    public void setData(List<? extends Structure> elements, int type)
    {
        this.structureType = type;
        this.elements = elements;
        notifyDataSetChanged();
    }
}
