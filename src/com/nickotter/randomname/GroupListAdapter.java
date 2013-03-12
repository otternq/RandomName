package com.nickotter.randomname;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;
import com.nickotter.randomname.Item;

public class GroupListAdapter extends BaseAdapter {
	
	private List<Group> list = null;
	private Context context = null;

	/**
	 * @param args
	 */
	public GroupListAdapter(Context context, List<Group> items) {
		this.list = items;
		this.context = context;

	}

	@Override
	public int getCount() {
		return this.list.size();
	}

	@Override
	public Object getItem(int position) {
		return this.list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return this.list.get(position).getID();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView itemLayout;
        Group item = this.list.get(position);

        itemLayout= (TextView) LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        itemLayout.setText(item.getName());

        return itemLayout;
	}

}
