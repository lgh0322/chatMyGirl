package com.vaca.chatmygirl.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.vaca.chatmygirl.R;
import com.vaca.chatmygirl.bean.SortModel;
import com.vaca.chatmygirl.stickylistheaders.StickyListHeadersAdapter;

import java.util.List;
import java.util.Locale;

public class SortAdapter extends BaseAdapter implements StickyListHeadersAdapter, SectionIndexer {
    boolean isGroup = false;
    private List<SortModel> list = null;
    private Context mContext;

    public SortAdapter(Context mContext, List<SortModel> list, boolean isGroup) {
        this.mContext = mContext;
        this.list = list;
        this.isGroup = isGroup;
    }

    /**
     * 当ListView数据发生变化时,调用此方法来更新ListView
     *
     * @param list
     */
    public void updateListView(List<SortModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public int getCount() {
        return this.list.size();
    }

    public Object getItem(int position) {
        return list.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup arg2) {
        ViewHolder viewHolder = null;
        final SortModel mContent = list.get(position);
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.item_contacts, null);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }


//        viewHolder.text.setText(this.list.get(position).getName());

        return view;

    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        HeaderViewHolder holder;

        if (convertView == null) {
            holder = new HeaderViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.contacts_header, parent, false);
            holder.text = (TextView) convertView.findViewById(R.id.header);
            convertView.setTag(holder);
        } else {
            holder = (HeaderViewHolder) convertView.getTag();
        }

        // set header text as first char in name
        CharSequence headerChar = list.get(position).getSortLetters();
        holder.text.setText(headerChar);

        return convertView;
    }


    /**
     * 根据ListView的当前位置获取分类的首字母的Char ascii值
     */
    @Override
    public int getSectionForPosition(int position) {
        return list.get(position).getSortLetters().charAt(0);
    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    @Override
    public int getPositionForSection(int section) {
        for (int i = 0; i < getCount(); i++) {
            String sortStr = list.get(i).getSortLetters();
            char firstChar = sortStr.toUpperCase(Locale.getDefault()).charAt(0);
            if (firstChar == section) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public Object[] getSections() {
        return list.toArray();
    }


    @Override
    public long getHeaderId(int position) {
        // TODO Auto-generated method stub
        return list.get(position).getSortLetters().charAt(0);
    }

    class HeaderViewHolder {
        TextView text;
    }

    class ViewHolder {
        View view;
        TextView text;
        ImageView profile_image;
        TextView status;
    }

}