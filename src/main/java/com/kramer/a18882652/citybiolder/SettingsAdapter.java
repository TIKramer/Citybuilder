package com.kramer.a18882652.citybiolder;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SettingsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private final int TYPE_SAVE =4;

    private Map<String, Setting> settings;
    private Activity activity;
    List<String> keys;
    public class SettingSaveVHolder extends RecyclerView.ViewHolder
    {
        public TextView errorLog;
        public Button saveBtn;



        public SettingSaveVHolder(@NonNull View itemView)
        {
            super(itemView);
            errorLog = (TextView) itemView.findViewById(R.id.errorLog);
            saveBtn = (Button) itemView.findViewById(R.id.saveBtn);

            saveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    GameDataModel model = GameDataModel.getGameData(activity);
                  model.setSettings(settings);
                }
            });

        }



        public void bind(String error) {
            errorLog.setText(error);
        }





    }

    public class SettingStringVHolder extends RecyclerView.ViewHolder
    {
        public TextView settingName;
        public EditText settingValue;



        public SettingStringVHolder(@NonNull View itemView)
        {
            super(itemView);

            settingName = (TextView) itemView.findViewById(R.id.settingName);
            settingValue = (EditText) itemView.findViewById(R.id.editText);





        }



        public void bind(Setting setting) {
            settingName.setText(setting.getName());
            if(!setting.getData().toString().isEmpty()) {
                settingValue.setText(setting.getData().toString());
            }

            if(setting.isLock())
            {
                settingValue.setEnabled(true);
            }
            settingValue.setOnFocusChangeListener(new View.OnFocusChangeListener() {

                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    /* When focus is lost check that the text field
                     * has valid values.
                     */
                    if (!hasFocus) {
                        saveData();
                    }

                }
            });
        }

        public void saveData()
        {
            SettingsAdapter.this.updateSetting(this.getAdapterPosition(), settingValue.getText());
        }




    }
    public class SettingFloatVHolder extends RecyclerView.ViewHolder
    {
        public TextView settingName;
        public EditText settingValue;

        public SettingFloatVHolder(@NonNull View itemView)
        {
            super(itemView);

            settingName = (TextView) itemView.findViewById(R.id.settingName);
            settingValue = (EditText) itemView.findViewById(R.id.editText);

        }



        public void bind(Setting setting) {
            settingName.setText(setting.getName());
            settingValue.setText(setting.getData().toString());

            if(setting.isLock())
            {
                settingValue.setEnabled(false);
            }
            settingValue.setOnFocusChangeListener(new View.OnFocusChangeListener() {

                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    /* When focus is lost check that the text field
                     * has valid values.
                     */
                    if (!hasFocus) {
                        saveData();
                    }

                }
            });

        }

        public void saveData()
        {
            SettingsAdapter.this.updateSetting(this.getAdapterPosition(), settingValue.getText());
        }



    }

    public class SettingIntergerVHolder extends RecyclerView.ViewHolder
    {
        public TextView settingName;
        public TextView settingValue;

        public SettingIntergerVHolder(@NonNull View itemView)
        {
            super(itemView);

            settingName = (TextView) itemView.findViewById(R.id.settingName);
            settingValue = (EditText) itemView.findViewById(R.id.editText);




        }


        public void bind(Setting setting) {
            settingName.setText(setting.getName());
            settingValue.setText(setting.getData().toString());

            if (setting.isLock()) {
                settingValue.setEnabled(false);
            }
            settingValue.setOnFocusChangeListener(new View.OnFocusChangeListener() {

                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    /* When focus is lost check that the text field
                     * has valid values.
                     */
                    if (!hasFocus) {
                        saveData();
                    }

                }
            });
        }


        public void saveData()
        {
            SettingsAdapter.this.updateSetting(this.getAdapterPosition(), settingValue.getText());
        }




    }
    public SettingsAdapter(Activity activity, Map<String, Setting> settings)
    {
        this.settings = settings;
        this.activity = activity;
        keys = new ArrayList<>(settings.keySet());
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType)    {
        View view;
        if (viewType == Setting.STRING) { // for call layout
            view = LayoutInflater.from(activity).inflate(R.layout.fragment_setting_string, viewGroup, false);
            view.getLayoutParams().height = viewGroup.getMeasuredHeight() / 8;

            return new SettingStringVHolder(view);


        } else if (viewType == Setting.FLOAT){ // for email layout
            view = LayoutInflater.from(activity).inflate(R.layout.fragment_setting_double, viewGroup, false);
            view.getLayoutParams().height = viewGroup.getMeasuredHeight() / 8;

            return new SettingFloatVHolder(view);
        }
        else if (viewType == Setting.INTERGER){ // for email layout
            view = LayoutInflater.from(activity).inflate(R.layout.fragment_setting_int, viewGroup, false);
            view.getLayoutParams().height = viewGroup.getMeasuredHeight() / 8;

            return new SettingIntergerVHolder(view);
        }
        else if(viewType == TYPE_SAVE)
        {
            view = LayoutInflater.from(activity).inflate(R.layout.fragmanet_setting_save, viewGroup, false);
            view.getLayoutParams().height = viewGroup.getMeasuredHeight() / 8;

            return new SettingSaveVHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        int viewType = viewHolder.getItemViewType();

        if (viewType == Setting.STRING) { // for call layout
           // ((SettingStringVHolder) viewHolder).saveData();
            ((SettingStringVHolder) viewHolder).bind(settings.get(keys.get(i)));

        } else if (viewType == Setting.FLOAT){ // for email layout
            //((SettingFloatVHolder) viewHolder).saveData();
            ((SettingFloatVHolder) viewHolder).bind(settings.get(keys.get(i)));


        }
        else if (viewType == Setting.INTERGER){ // for email layout
            //((SettingIntergerVHolder) viewHolder).saveData();
            ((SettingIntergerVHolder) viewHolder).bind(settings.get(keys.get(i)));


        }
        else if(viewType == TYPE_SAVE)
        {
            ((SettingSaveVHolder) viewHolder).bind("");
        }



    }


    public void updateSetting(int index, Object data)
    {

        settings.get(keys.get(index)).setData(data);
       // this.notifyItemChanged(index);
    }


    @Override
    public int getItemCount()
    {
        return settings.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
       int type = -1;
        if(position < keys.size()) {
           type = settings.get(keys.get(position)).getType();
       }
       else
       {
           type = TYPE_SAVE;
       }
       return  type;

    }
}
