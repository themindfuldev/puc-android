package br.edu.pucminas.notes.activities;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.SimpleCursorAdapter;
import br.edu.pucminas.notes.R;
import br.edu.pucminas.notes.persistence.NoteDAO;
import br.edu.pucminas.notes.persistence.NoteSimpleCursorAdapter;

public class SeeNotesActivity extends ListActivity {
	private NoteDAO dao;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		dao = new NoteDAO(getBaseContext());
		Cursor cursor = dao.listNotes();
		startManagingCursor(cursor);
		SimpleCursorAdapter adapter = new NoteSimpleCursorAdapter(this,
				R.layout.see_a_note, cursor, // cursor
				new String[] { NoteDAO.CREATION_DATE, NoteDAO.TITLE,
						NoteDAO.CONTENT, NoteDAO.LOCATION, NoteDAO.TYPE }, 
				new int[] { R.id.textCreationDate, R.id.textTitle,
						R.id.textContent, R.id.textLocation, R.id.textType } 
		); 
		setListAdapter(adapter);

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (dao != null) {
			dao.close();
		}
	}

}