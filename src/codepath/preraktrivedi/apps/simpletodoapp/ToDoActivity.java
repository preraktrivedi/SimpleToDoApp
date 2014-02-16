package codepath.preraktrivedi.apps.simpletodoapp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class ToDoActivity extends Activity {

	private static final String TAG = ToDoActivity.class.getSimpleName();
	private final String FILENAME = "todo.txt";
	private ArrayList<String> items;
	private ArrayAdapter<String> itemsAdapter;
	private ListView listView;
	private EditText etAddItem;
	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_to_do);
		context = this;
		listView = (ListView) findViewById(R.id.lv_items);
		etAddItem = (EditText) findViewById(R.id.et_new_item);
		items = readItems();
		itemsAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, items);
		listView.setAdapter(itemsAdapter);
		items.add("Sample Item 1");
		items.add("Sample Item 2");
		setupListViewListener();

	}

	private ArrayList<String> readItems() {
		ArrayList<String> itemList;
		File todoFile = new File(getFilesDir(), FILENAME);
		try {
			itemList = new ArrayList<String>(FileUtils.readLines(todoFile, "UTF-8"));
		} catch (IOException e) {
			itemList = new ArrayList<String>();
			Log.e(TAG, "I/O Error: \n" + e.getMessage());
			e.printStackTrace();
		}
		return itemList;
	}

	private void saveItems() {
		File todoFile = new File(getFilesDir(), FILENAME);
		try {
			FileUtils.writeLines(todoFile, items);
		} catch (IOException ioe) {
			Log.e(TAG, ioe.getMessage());
		}
	}

	private void setupListViewListener() {
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> viewIn, View item,
					int pos, long id) {
				items.remove(pos);
				itemsAdapter.notifyDataSetChanged();
				saveItems();
				return true;
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.to_do, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.action_clearitems:
			if(items != null && items.size() > 0) {
				items.clear();
				itemsAdapter.notifyDataSetChanged();
				saveItems();
				Toast.makeText(ToDoActivity.this, "Cleared List", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(ToDoActivity.this, "List already Empty", Toast.LENGTH_SHORT).show();
			}
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}


	public void addToDoItem(View v) {
		String etString = etAddItem.getText().toString().trim();
		if(etString.length() > 0) {
			itemsAdapter.add(etString);
			etAddItem.setText("");
		} else {
			Toast.makeText(context, "Please add some text", Toast.LENGTH_SHORT).show();
		}
	}

}
