package com.nickotter.randomname;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ImageView;

import java.util.List;
import com.nickotter.randomname.Item;

public class GroupListAdapter extends BaseAdapter {
	
	private List<Group> list = null;
	private Activity context = null;
	
    private static LayoutInflater inflater=null;

	/**
	 * @param args
	 */
	public GroupListAdapter(Activity context, List<Group> items) {
		this.list = items;
		this.context = context;

		
		inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
		
		View vi=convertView;
        if(convertView==null) {
            vi = inflater.inflate(R.layout.group_row, null);
        }

        Group item = this.list.get(position);
        
        TextView text=(TextView)vi.findViewById(R.id.row_name);
        text.setText(item.getName());
        
        int editImageDrawable = R.drawable.content_edit;
        int deleteImageDrawable = R.drawable.content_discard;
        
        ImageView editImage = (ImageView)vi.findViewById(R.id.row_edit);
        Drawable imageDrawable = this.context.getResources().getDrawable(editImageDrawable);
        editImage.setImageDrawable(imageDrawable);
        
        ImageView deleteImage =(ImageView)vi.findViewById(R.id.row_delete);
        Drawable imageDrawableDelete = this.context.getResources().getDrawable(deleteImageDrawable);
        deleteImage.setImageDrawable(imageDrawableDelete);
        
        return vi;
	}

}
