package com.projectname.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.projectname.R;
import com.projectname.model.SlidingMenuModel;

import java.util.ArrayList;
import java.util.List;


/****************************************************************************
 * @ClassdName:arrMenuListAdapter
 * @CreatedDate:
 * @ModifiedBy: not yet
 * @ModifiedDate: not yet
 * @purpose:This class is use to Adapter on arrMenuList
 ***************************************************************************/

public class MenuAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<SlidingMenuModel> arrMenuList = null;
    private long mLastClickTime = 0;

    public MenuAdapter(Context context, List<SlidingMenuModel> glcContactsListArr) {

        this.context = context;
        this.arrMenuList = (ArrayList<SlidingMenuModel>) glcContactsListArr;
    }

    @Override
    public int getCount() {

        return arrMenuList.size();
    }

    @Override
    public SlidingMenuModel getItem(int position) {
        return arrMenuList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    /****************************************************************************
     * @Method:-getView
     * @purpose:This method use to Declaration of view & initializeComponent .
     ***************************************************************************/

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Holder holder;

        if (convertView == null) {
            holder = new Holder();
            convertView = LayoutInflater.from(context).inflate(R.layout.row_menu, null);

            holder.tvTitle = (TextView) convertView.findViewById(R.id.row_menu_tvTitle);
            holder.ivIcon = (ImageView) convertView.findViewById(R.id.row_menu_ivIcon);
            holder.llsection = (LinearLayout) convertView.findViewById(R.id.row_menu_llSection);

            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }


        holder.tvTitle.setText(arrMenuList.get(position).getTitle());

        holder.ivIcon.setImageResource(arrMenuList.get(position).getIcon());

        if (position == 2 || position == 4 || position == 6 || position == 7) {
            holder.llsection.setVisibility(View.VISIBLE);
        } else {
            holder.llsection.setVisibility(View.GONE);
        }


        return convertView;
    }


    class Holder {

        private TextView tvTitle;
        private ImageView ivIcon;
        private LinearLayout llsection;

    }


}
