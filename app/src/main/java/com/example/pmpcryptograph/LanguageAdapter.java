package com.example.pmpcryptograph;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class LanguageAdapter extends BaseAdapter {

    Context context;
    private List<Language> languages;

    public LanguageAdapter(Context context, List<Language> languages){

        this.context = context;
        this.languages=languages;
    }

    @Override
    public int getCount() {
        return languages.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        final LanguageAdapter.ViewHolder viewHolder;

        final View result;

        if (convertView == null) {

            viewHolder = new LanguageAdapter.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.language_item, parent, false);

            viewHolder.rbLang = (RadioButton) convertView.findViewById(R.id.rbLang);
            viewHolder.flag=(ImageView) convertView.findViewById(R.id.ivLang);
            viewHolder.country=convertView.findViewById(R.id.txtCountry);
            viewHolder.lang=convertView.findViewById(R.id.txtLang);
            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (LanguageAdapter.ViewHolder) convertView.getTag();
            result=convertView;
        }


        viewHolder.rbLang.setChecked(languages.get(position).isChecked());
        viewHolder.rbLang.setText(languages.get(position).getName());
        viewHolder.flag.setImageResource(languages.get(position).getFlag());
        viewHolder.country.setText(languages.get(position).getCountry());
        viewHolder.lang.setText(languages.get(position).getLang());





        return convertView;
    }


    private static  class ViewHolder {

        RadioButton rbLang;
        ImageView flag;
        TextView country;
        TextView lang;


    }

}
