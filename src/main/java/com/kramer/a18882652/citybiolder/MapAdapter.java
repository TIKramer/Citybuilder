package com.kramer.a18882652.citybiolder;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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
        view.getLayoutParams().height = parent.getMeasuredHeight() / 15;
        view.getLayoutParams().height = parent.getMeasuredHeight() / 15;


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
}