package com.codepath.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<TaskItem> items;
    private ListViewAdapter itemsAdapter;
    private ListView lvlItems;
    private int selectedPosition;
    private final String FILE_NAME = "todo.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        readItems();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // list view
        lvlItems = (ListView) findViewById(R.id.lvItems);
        // initialize adapters
        itemsAdapter=new ListViewAdapter(this, items);
        lvlItems.setAdapter(itemsAdapter);

        // set up listeners
        setupListViewListener();
        setupListViewOnClickListener();
    }

    /**
     * handler for add Task onClick event
     * @param view
     */
    public void onAddItem(View view) {
        launchActivity(ActionType.ADD, -1);
    }

    /**
     * OnItemLongClickListener
     * removes a selected task from the list view
     */
    public void setupListViewListener() {

        lvlItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View arg1,
                                           int pos, long arg3) {
                items.remove(pos);
                itemsAdapter.updateList(items);
                // save items to file
                writeItems();
                return true;
            }
        });
    }

    private final int REQUEST_CODE = 20;

    /**
     * Edit a selected task
     */
    public void setupListViewOnClickListener() {
            // FirstActivity, launching an activity for a result
            lvlItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick (AdapterView < ? > parent, View view,
                final int position, long id){
                    launchActivity(ActionType.EDIT, position);
                }
            }
        );
    }

    /**
     * Activity result handler
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            // Extract name value from result extras
            String item = data.getExtras().getString("itemName");
            String date = data.getExtras().getString("itemDate");
            String itemNotes = data.getExtras().getString("itemNotes");
            ActionType actionType = ActionType.valueOf(data.getExtras().getString("actionType"));

            TaskItem taskItem = null;
            // item added
            if (actionType == ActionType.ADD) {
                taskItem = new TaskItem();
                taskItem.setItemName(item);
                taskItem.setItemDate(date);
                taskItem.setItemNotes(itemNotes);

                items.add(taskItem);
            } else {
                // item updated
                taskItem = items.get(selectedPosition);
                taskItem.setItemName(item);
                taskItem.setItemDate(date);
                taskItem.setItemNotes(itemNotes);
                items.set(selectedPosition, taskItem);
            }

            // refresh list view
            itemsAdapter.updateList(items);
            lvlItems.invalidate();
            // update file
            writeItems();
        }
    }

    /**
     * Launches Edit/Add activity
     * @param actionType
     * @param position
     */
    private void launchActivity(ActionType actionType, int position){
        String itemName = "";
        String itemDate = "";
        String itemNotes = "";

        // get the item
        if (actionType == ActionType.EDIT){
            TaskItem item = (TaskItem)itemsAdapter.getItem(position);
            selectedPosition = position;
            itemName = item.getItemName();
            itemDate = item.getItemDate();
            itemNotes = item.getItemNotes();
        }

        // set intent parameters
        Intent i = new Intent(MainActivity.this, EditItemActivity.class);
        i.putExtra("actionType", actionType.toString()); // pass arbitrary data to launched activity
        i.putExtra("itemName", itemName); // pass arbitrary data to launched activity
        i.putExtra("itemDate", itemDate); // pass arbitrary data to launched activity
        i.putExtra("itemNotes", itemNotes); // pass arbitrary data to launched activity

        startActivityForResult(i, REQUEST_CODE);
    }

    /**
     * read task items from a file
     */
    private void readItems(){
        File fileDir = getFilesDir();
        File todoFile = new File(fileDir, FILE_NAME);
        try {
            final Gson gson = new Gson();
            String lines = FileUtils.readFileToString(todoFile);

            Type listType = new TypeToken<ArrayList<TaskItem>>(){}.getType();
            // read object
            items = new Gson().fromJson(lines, listType);

        } catch (IOException ex){
            items = new ArrayList<TaskItem>();
        }
    }

    /**
     * write task items to a file
     */
    private void writeItems(){
        File fileDir = getFilesDir();
        File todoFile = new File(fileDir, FILE_NAME);
        try {

            final Gson gson = new Gson();
            String json = gson.toJson(items, ArrayList.class);
            // write contents to a file
            FileUtils.writeStringToFile(todoFile, json);

        } catch (IOException ex){
            ex.printStackTrace();
        }
    }
}

