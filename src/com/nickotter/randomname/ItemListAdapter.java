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

public class ItemListAdapter extends BaseAdapter {
	
	private List<Item> list = null;
	private Context context = null;

	/**
	 * @param args
	 */
	public ItemListAdapter(Context context, List<Item> items) {
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
		LinearLayout itemLayout;
        Item item = this.list.get(position);

        itemLayout= (LinearLayout) LayoutInflater.from(context).inflate(R.layout.grid_item, parent, false);

        TextView tvUser = (TextView) itemLayout.findViewById(R.id.big);
        tvUser.setText(item.getName());

        return itemLayout;
	}

}
