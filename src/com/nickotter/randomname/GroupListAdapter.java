package com.nickotter.randomname;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.ImageView;

import java.util.List;

import com.nickotter.randomname.crudActivities.AddItem;
import com.nickotter.randomname.crudActivities.DeleteGroup;
import com.nickotter.randomname.crudActivities.EditGroup;

public class GroupListAdapter extends BaseAdapter {
	
	protected static final String LOGTAG = null;
	private List<Group> groups = null;
	private MainActivity context = null;
	
    private static LayoutInflater inflater=null;
    
    CRUD databaseCRUD = null;

	/**
	 * @param args
	 */
	public GroupListAdapter(MainActivity context, List<Group> groups) {
		this.groups = groups;
		this.context = context;
		
		inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}

	@Override
	public int getCount() {
		return this.groups.size();
	}

	@Override
	public Object getItem(int position) {
		return this.groups.get(position);
	}

	@Override
	public long getItemId(int position) {
		return this.groups.get(position).getID();
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		
		View vi=convertView;
        if(convertView==null) {
            vi = inflater.inflate(R.layout.group_row, null);
        }

        Group item = this.groups.get(position);
        
        TextView text=(TextView)vi.findViewById(R.id.row_name);
        text.setText(item.getName());
        
        int editImageDrawable = R.drawable.content_edit;
        int deleteImageDrawable = R.drawable.content_discard;
        
        ImageView editImage = (ImageView)vi.findViewById(R.id.row_edit);
        Drawable imageDrawable = this.context.getResources().getDrawable(editImageDrawable);
        editImage.setImageDrawable(imageDrawable);
        editImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            	Log.v(LOGTAG, "Side Drawer: Edit Group selected");
    			Intent iegroup = new Intent(context, EditGroup.class);
    			
    			Group groupEntry = groups.get(position);
    			
    			iegroup.putExtra("groupId", groupEntry.getID());
    			context.startActivity(iegroup);        
            }
        });
        
        ImageView deleteImage =(ImageView)vi.findViewById(R.id.row_delete);
        Drawable imageDrawableDelete = this.context.getResources().getDrawable(deleteImageDrawable);
        deleteImage.setImageDrawable(imageDrawableDelete);
        
        deleteImage.setOnClickListener(new OnClickListener() {
        	
        	@Override
        	public void onClick(View v) {
        		String LOGTAG = "GroupListAdapter Side Drawer: Delete Group Selected";
        		Log.v(LOGTAG, "enter");
        		
        		Intent dgroup = new Intent(context, DeleteGroup.class);
	    		dgroup.putExtra("groupId", groups.get(position).getID());
	    		context.startActivity(dgroup);
        		
        		Log.v(LOGTAG, "exit");
        		
        	}
        	
        });
        
        return vi;
	}

}
