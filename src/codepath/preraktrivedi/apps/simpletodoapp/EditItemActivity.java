package codepath.preraktrivedi.apps.simpletodoapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditItemActivity extends Activity {

	private EditText etEditItem;
	private int itemPosition;
	private String originalText;
	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this;
		setContentView(R.layout.activity_edit_item);
		etEditItem = (EditText) findViewById(R.id.et_edit_item);
		TextView tvOriginalText = (TextView) findViewById(R.id.tv_original_item_text);
		originalText = getIntent().getStringExtra("item_to_edit_text");
		itemPosition = getIntent().getIntExtra("item_to_edit_position", 0);

		//populate with old text
		tvOriginalText.setText(originalText);
		etEditItem.setText(originalText);
		etEditItem.setSelection(originalText.length());
	}

	public void saveEditedItem(View v) {

		String editedItem =  etEditItem.getText().toString().trim();

		if(editedItem.length() > 0) {
			if(editedItem.equals(originalText)) {
				Toast.makeText(context, "Nothing to Edit", Toast.LENGTH_SHORT).show();	
			} else {
				Intent editFinish = new Intent();
				editFinish.putExtra("updated_item_text", etEditItem.getText().toString());
				editFinish.putExtra("updated_item_position", itemPosition);
				setResult(RESULT_OK, editFinish);
				finish();
			}
		} else {
			Toast.makeText(context, "Please add some text", Toast.LENGTH_SHORT).show();
		}
	}
}
