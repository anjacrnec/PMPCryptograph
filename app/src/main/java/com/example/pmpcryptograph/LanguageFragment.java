package com.example.pmpcryptograph;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;


import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class LanguageFragment extends Fragment {


    int current;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    public LanguageFragment() {

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_language, container, false);

        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        editor=prefs.edit();

        String country=prefs.getString("country","US");
        String lang= prefs.getString("lang","en");

        List<Language> languages=new ArrayList<Language>();
        languages.add(new Language("English (US)",true,R.drawable.usa_flag,"US","en"));
        languages.add(new Language("Макеоднски (МК)",true,R.drawable.macedonia_flag,"MK","mk"));


        for(int i=0;i<languages.size();i++)
        {
            if(languages.get(i).getCountry().equals(country) && languages.get(i).getLang().equals(lang))
                languages.get(i).setChecked(true);
            else
                languages.get(i).setChecked(false);
        }


        LanguageAdapter adapter=new LanguageAdapter(getActivity().getApplicationContext(),languages);
        ListView listView=v.findViewById(R.id.lvLang);
        listView.setAdapter(adapter);

        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
       listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

               for(int i=0;i<languages.size();i++)
               {
                   if(languages.get(i).isChecked())
                       current=i;
                   languages.get(i).setChecked(false);

               }
             languages.get(position).setChecked(true);
               adapter.notifyDataSetChanged();

               Resources res=getContext().getResources();
               AlertDialog dialog=new AlertDialog.Builder(v.getContext())
                       .setTitle(res.getString(R.string.change_language_title))
                       .setMessage(res.getString(R.string.change_language_body))
                       .setPositiveButton(res.getString(R.string.change_language_yes), new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {

                                String country=languages.get(position).getCountry();
                                String lang=languages.get(position).getLang();
                               Locale locale;
                               locale=new Locale(lang,country);
                               Locale.setDefault(locale);
                               Configuration config = new Configuration();
                               config.locale = locale;
                               getResources().updateConfiguration(config,getResources().getDisplayMetrics());
                              editor.putString("country",country);
                               Log.d("drzava",country);
                              editor.putString("lang",lang);
                               Log.d("lang",lang);
                               editor.apply();
                               //Intent intent = getActivity().getIntent();
                             //  getActivity().finish();
                            //   startActivity(intent);
                             Intent intent = new Intent(getActivity().getApplicationContext(), MainActivity.class);
                           intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                             getActivity().finishAffinity();
                           }
                       })
                       .setNegativeButton(res.getString(R.string.change_language_no), new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {
                               languages.get(position).setChecked(false);
                               languages.get(current).setChecked(true);
                               adapter.notifyDataSetChanged();
                           }
                       }).create();
               dialog.show();

           }

       });


        return v;
    }

    public void confirmChangeLanguage(View v)
    {




    }
}
