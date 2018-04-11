package edu.usc.cs404.catchup;

/**
 * Created by jamestseng on 3/24/18.
 */

public class SurveyItem {

    boolean isSelected;
    String desc;

    //now create constructor and getter setter method using shortcut like command+n for mac & Alt+Insert for window.
    public SurveyItem() {

    }

    public SurveyItem(boolean isSelected, String desc) {
        this.isSelected = isSelected;
        this.desc = desc;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String userName) {
        this.desc = userName;
    }

    @Override
    public boolean equals(Object o){

        if(o instanceof SurveyItem){

            SurveyItem i = (SurveyItem) o;
            if((i.isSelected==this.isSelected)&&(i.desc.equals(this.desc)))
            {
                return true;
            }
            else return false;

        }

        return false;
    }
}