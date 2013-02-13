package com.nickotter.randomname;
 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.navdrawer.SimpleSideDrawer;

import android.app.ExpandableListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;
import android.app.Activity;
import android.view.Menu;
import android.view.View.OnClickListener;
 
public class DrawerTestMainActivity extends Activity {

	
    private SimpleSideDrawer mNav;
    
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_example_activity_main);
        mNav = new SimpleSideDrawer(this);
        mNav.setBehindContentView(R.layout.drawer_example_activity_behind);
        findViewById(R.id.btn).setOnClickListener(new OnClickListener() {
            @Override 
            public void onClick(View v) {
                mNav.toggleDrawer();
            }
        });
    }
   /* public void onCreate(Bundle savedInstanceState) {
        try{
             super.onCreate(savedInstanceState);
             setContentView(R.layout.drawer_example_activity_main);
 
        SimpleExpandableListAdapter expListAdapter =
            new SimpleExpandableListAdapter(
                    this,
                    createGroupList(),              // Creating group List.
                    R.layout.group_row,             // Group item layout XML.
                    new String[] { "Group Item" },  // the key of group item.
                    new int[] { R.id.row_name },    // ID of each group item.-Data under the key goes into this TextView.
                    createChildList(),              // childData describes second-level entries.
                    R.layout.child_row,             // Layout for sub-level entries(second level).
                    new String[] {"Sub Item"},      // Keys in childData maps to display.
                    new int[] { R.id.grp_child }     // Data under the keys above go into these TextViews.
                );
            setListAdapter( expListAdapter );       // setting the adapter in the list.
 
        }catch(Exception e){
            System.out.println("Errrr +++ " + e.getMessage());
        }
    }
 
    //Creating the Hashmap for the row
    @SuppressWarnings("unchecked")
    private List<HashMap<String, String>> createGroupList() {
          ArrayList<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
          for( int i = 0 ; i < 15 ; ++i ) { // 15 groups........
            HashMap<String, String> m = new HashMap<String, String>();
            m.put( "Group Item","Group Item " + i ); // the key and it's value.
            result.add( m );
          }
          return (List<HashMap<String, String>>)result;
    }
 
    // creatin the HashMap for the children
    @SuppressWarnings("unchecked")
    private List<ArrayList<HashMap<String, String>>> createChildList() {
 
        ArrayList<ArrayList<HashMap<String, String>>> result = new ArrayList<ArrayList<HashMap<String, String>>>();
        for( int i = 0 ; i < 15 ; ++i ) { // this -15 is the number of groups(Here it's fifteen)
          // each group need each HashMap-Here for each group we have 3 subgroups 
          ArrayList<HashMap<String, String>> secList = new ArrayList<HashMap<String, String>>();
          for( int n = 0 ; n < 3 ; n++ ) {
            HashMap<String, String> child = new HashMap<String, String>();
            child.put( "Sub Item", "Sub Item " + n );
            secList.add( child );
          }
         result.add( secList );
        }
        return result;
    }
    public void  onContentChanged  () {
        System.out.println("onContentChanged");
        super.onContentChanged();
    }
    // This function is called on each child click 
    public boolean onChildClick( ExpandableListView parent, View v, int groupPosition,int childPosition,long id) {
        System.out.println("Inside onChildClick at groupPosition = " + groupPosition +" Child clicked at position " + childPosition);
        return true;
    }
 
    // This function is called on expansion of the group
    public void  onGroupExpand  (int groupPosition) {
        try{
             System.out.println("Group exapanding Listener => groupPosition = " + groupPosition);
        }catch(Exception e){
            System.out.println(" groupPosition Errrr +++ " + e.getMessage());
        }
    }
    */
}