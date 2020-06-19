package com.example.pmpcryptograph;

import java.util.List;

public class Language {

    private String name;
    private boolean checked;
    private int flag;
    private String country;
    private String lang;

    public Language(String name, boolean checked,int flag, String country, String lang)
    {
        this.name=name;
        this.checked=checked;
        this.flag=flag;
        this.country=country;
        this.lang=lang;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int icon) {
        this.flag= flag;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }




}
