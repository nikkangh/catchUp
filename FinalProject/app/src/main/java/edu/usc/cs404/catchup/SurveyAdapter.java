package edu.usc.cs404.catchup;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class SurveyAdapter extends BaseAdapter {

    Activity activity;
    List<SurveyItem> items;
    LayoutInflater inflater;

    //short to create constructer using command+n for mac & Alt+Insert for window

    public SurveyAdapter(Activity activity) {
        this.activity = activity;
    }

    public SurveyAdapter(Activity activity, List<SurveyItem> items) {
        this.activity = activity;
        this.items = items;

        inflater = activity.getLayoutInflater();
    }


    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder holder = null;

        if (view == null){

            view = inflater.inflate(R.layout.list_view_item, viewGroup, false);

            holder = new ViewHolder();

            holder.tvUserName = (TextView)view.findViewById(R.id.option);
            holder.ivCheckBox = (ImageView) view.findViewById(R.id.iv_check_box);

            view.setTag(holder);
        }else
            holder = (ViewHolder)view.getTag();

        SurveyItem model = items.get(i);

        holder.tvUserName.setText(model.getDesc());

        if (model.isSelected())
            holder.ivCheckBox.setBackgroundResource(R.drawable.checked);

        else
            holder.ivCheckBox.setBackgroundResource(R.drawable.check);

        return view;
    }

    public void updateRecords(List<SurveyItem> items){
        this.items = items;

        notifyDataSetChanged();
    }

    class ViewHolder{

        TextView tvUserName;
        ImageView ivCheckBox;

    }
}