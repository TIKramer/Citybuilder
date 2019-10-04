package com.kramer.a18882652.citybiolder;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

public class SelectorAdapter extends RecyclerView.Adapter<SelectorAdapter.StructureHolder>
{

    private List<Structure> elements;
    private Activity activity;
    public class StructureHolder extends RecyclerView.ViewHolder
    {
        public ImageView imageView;
        public StructureHolder(View view )
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


        }

        public void saveData(Structure structure)
        {
            SelectorAdapter.this.updateImage(this.getAdapterPosition(), structure);
        }




        public void bind(MapElement element) {
            if(element.getStructure().getImageID() != 0) {
                imageView.setImageResource(element.getStructure().getImageID());
            }
        }
    }

    private void updateImage(int adapterPosition, Structure structure) {


    }



    public SelectorAdapter(Activity activity, List<Structure> elements)
    {
        this.elements = elements;
        this.activity = activity;
    }

    @Override
    public StructureHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view;

        view = LayoutInflater.from(activity).inflate(R.layout.fragment_grid, parent, false);
        view.getLayoutParams().height = parent.getMeasuredHeight() / 15;
        view.getLayoutParams().height = parent.getMeasuredHeight() / 15;


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


    public void setData(List<Structure> eleemnts)
    {
        this.elements = elements;
        notifyDataSetChanged();
    }
}
