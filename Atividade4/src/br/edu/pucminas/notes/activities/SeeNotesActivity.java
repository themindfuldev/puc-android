package br.edu.pucminas.notes.activities;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import br.edu.pucminas.notes.R;

public class SeeNotesActivity extends ListActivity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		String[] notes = getResources().getStringArray(R.array.notes_array);
		setListAdapter(new ArrayAdapter<String>(this, R.layout.see_notes,
				notes));

		ListView lv = getListView();
		lv.setTextFilterEnabled(true);

		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(SeeNotesActivity.this, SeeANoteActivity.class);
				startActivity(intent);
			}
		});

	}
}