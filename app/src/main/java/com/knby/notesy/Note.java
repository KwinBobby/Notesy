package com.knby.notesy;

/**
 * Created by Kevin on 10/12/2016.
 */

public class Note {

    private String Title;
    private String Description;
    private String ColourCode;

    public String getColourCode() {
        return ColourCode;
    }

    public void setColourCode(String colourCode) {
        ColourCode = colourCode;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }



}
