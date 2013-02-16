package com.nickotter.randomname;
 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.navdrawer.SimpleSideDrawer;

import android.app.ExpandableListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;
import android.app.Activity;
import android.view.Menu;
import android.view.View.OnClickListener;
 
public class DrawerTestMainActivity extends Activity 
{

	
    private SimpleSideDrawer mNav;
    
    CRUD databaseCRUD = null;
    
    
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
    	
    	
    	//load activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_example_activity_main);
        
        
        //Setup Database
        //Log.v(LOGTAG, "Deleting DATABASE_NAME="+ Sqlite.DATABASE_NAME);
		this.deleteDatabase(Sqlite.DATABASE_NAME);
		//Log.v(LOGTAG, "Initializing CRUD object");
		databaseCRUD = new CRUD(this);
		//Log.v(LOGTAG, "opening database connection in CRUD object");
		databaseCRUD.open();
		
		//Setup Sidedrawer
        mNav = new SimpleSideDrawer(this);
        mNav.setBehindContentView(R.layout.drawer_example_activity_behind);
        
        //Open drawer key listener
        findViewById(R.id.btn).setOnClickListener
        (new OnClickListener() 
        	{
            	@Override 
            	public void onClick(View v) 
            	{
            		mNav.toggleDrawer();
            	};
        	}   
        );
        
        //Exclusion toggle listener
        findViewById(R.id.toggleExclusion).setOnClickListener
        (new OnClickListener() 
        	{
            	@Override 
            	public void onClick(View v) 
            	{
            		//databaseCRUD.exclusionShift();
            	};
        	}   
        );
        
        //Verbalizer toggle listener
        findViewById(R.id.toggleVerbalize).setOnClickListener
        (new OnClickListener() 
        	{
            	@Override 
            	public void onClick(View v) 
            	{
            		//databaseCRUD.verbalShift();
            	};
        	}   
        );
        
        //Shake randomizer toggle listener
        findViewById(R.id.toggleShaker).setOnClickListener
        (new OnClickListener() 
        	{
            	@Override 
            	public void onClick(View v) 
            	{
            		//databaseCRUD.shakeShift();
            	};
        	}   
        );
        
        //Create expandable groups/lists
        createGroupList();
        
        
        //Prototype for the expanded list
        /*SimpleExpandableListAdapter expandGroupList = new SimpleExpandableListAdapter(
         * 		drawer_example_activity_behind, //associated activity
         * 		createGroupList(),				//hash map for groups
         * 		R.layout.group_row,				//Group XML 
         * 		databaseCRUD.getGroups(),		//Get group string id
         * 		databaseCRUD.getGroupIDs(), 	//get groups integer id's
         * 		createChildList(),				//hash map for lists(children) 
         * 		R.layout.child_row,				//List(child) XML 
         * 		databaseCRUD.getLists(group),	//String id's of lists 
         * 		databaseCRUD.getListIDs(group),	//integer id's of lists 
         * );
         */
        
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
 */
    //Creating rows (groups)
    @SuppressWarnings("unchecked")
    //    private List<HashMap<String, String>> createGroupList()
    private void createGroupList() 
    {
          Group g1 = new Group("CS 480");
          Group g2 = new Group("CS 481");
          Group g3 = new Group("CS 482");
    	
          databaseCRUD.add_group(g1);
          databaseCRUD.add_group(g2);
          databaseCRUD.add_group(g3);
          
          //mNav.put("Group Item", g1.getName());
          //result.add(m);
    	
  		  MyList l1 = new MyList(1, g1.getID(), "List 1");
  		  MyList l2 = new MyList(2, g1.getID(), "List 2");
  		  MyList l3 = new MyList(3, g1.getID(), "List 3");
  		  MyList l4 = new MyList(4, g2.getID(), "List 1");
  		  MyList l5 = new MyList(5, g2.getID(), "List 2");
  		  
  		  databaseCRUD.add_list(g1, l1);
  		  databaseCRUD.add_list(g1, l2);
  		  databaseCRUD.add_list(g1, l3);
  		  databaseCRUD.add_list(g2, l4);
  		  databaseCRUD.add_list(g2, l5);
    	
    	/*ArrayList<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
          for( int i = 0 ; i < 15 ; ++i ) 
          { // 15 groups........
        	  HashMap<String, String> m = new HashMap<String, String>();
        	  m.put( "Group Item","Group Item " + i ); // the key and it's value.
        	  result.add( m );
          }*/
         
    }
    /*
    //creating children (lists)
    @SuppressWarnings("unchecked")
    private void createChildList() 
    {

		
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