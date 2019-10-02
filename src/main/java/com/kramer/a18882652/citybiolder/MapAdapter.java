package com.kramer.a18882652.citybiolder;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

public class MapAdapter extends RecyclerView.Adapter<MapAdapter.GridHolder>
{

    private MapElement[] elements;
    private Activity activity;
    public static class GridHolder extends RecyclerView.ViewHolder
    {
        public ImageView imageView;
        public GridHolder(LayoutInflater li,
                             ViewGroup parent)
        {
            super( li.inflate(R.layout.fragment_grid,
                    parent, false));
            int size = parent . getMeasuredHeight () / 10  + 1;
            ViewGroup.LayoutParams lp = itemView . getLayoutParams ();
            lp . width = size ;
            lp . height = size ;
            imageView = (ImageView) itemView.findViewById(R.id.imageView);




        }


        public void bind(MapElement element) {
            imageView.setImageResource(element.getStructure().getImageID());
        }
    }

    public MapAdapter(Activity activity, MapElement[] elements)
    {
        this.elements = elements;
        this.activity = activity;
    }

    @Override
    public MapAdapter.GridHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        LayoutInflater li=LayoutInflater.from(activity);
        return new GridHolder(li,parent);
    }

    @Override
    public void onBindViewHolder(GridHolder vh, int index)
    {
        vh.bind(elements[index]);
    }


    @Override
    public int getItemCount()
    {
        return elements.length;
    }
}
