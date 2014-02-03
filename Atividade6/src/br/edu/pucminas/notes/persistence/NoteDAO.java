package br.edu.pucminas.notes.persistence;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import br.edu.pucminas.notes.R;
import br.edu.pucminas.notes.model.Note;

public class NoteDAO extends SQLiteOpenHelper {
	public static final String TYPE = "type";
	public static final String LOCATION = "location";
	public static final String CONTENT = "content";
	public static final String TITLE = "title";
	public static final String CREATION_DATE = "creationDate";
	
	private static final String DDL = new StringBuilder()
			.append("CREATE TABLE notes (")
			.append("_id INTEGER PRIMARY KEY AUTOINCREMENT,")
			.append("creationDate DATE NOT NULL,")
			.append("title TEXT NOT NULL,").append("content TEXT NOT NULL,")
			.append("location TEXT NOT NULL,")
			.append("type INTEGER NOT NULL);").toString();

	private static final String DATABASE_NAME = "notes";
	private static final int DATABASE_VERSION = 1;
	private static final String[] COLUMNS = new String[] { "_id",
		CREATION_DATE, TITLE, CONTENT, LOCATION, TYPE };
	private String appName;
	private SimpleDateFormat dateFormat;

	public NoteDAO(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);

		appName = context.getString(R.string.app_name);
		dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		try {
			db.execSQL(DDL);
			Log.w(appName, "Database was successfully created");

		} catch (SQLException e) {
			Log.d(appName, e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(appName, "Upgrading database from version " + oldVersion + " to "
				+ newVersion + ", which will destroy all old data");
		try {
			db.execSQL("DROP TABLE IF EXISTS notes");
			onCreate(db);
		} catch (SQLException e) {
			Log.d(appName, e.getMessage());
			e.printStackTrace();
		}
	}

	public Cursor listNotes() {
		return getReadableDatabase().rawQuery("SELECT * FROM notes", null);
	}

	public void insert(Note note) {
		ContentValues values = new ContentValues();

		values.put("creationDate", dateFormat.format(note.getCreationDate()));
		values.put("title", note.getTitle());
		values.put("content", note.getContent());
		values.put("location", note.getLocation());
		values.put("type", note.getType().ordinal());

		getWritableDatabase().insert(DATABASE_NAME, null, values);
	}

	public List<Note> getAllNotes() {
		Cursor c = getReadableDatabase().query(DATABASE_NAME, COLUMNS, null,
				null, null, null, null);
		List<Note> list = new ArrayList<Note>();
		while (c.moveToNext()) {
			Note note = new Note();
			note.setId(c.getInt(0));

			Date creationDate = null;
			try {
				creationDate = dateFormat.parse(c.getString(1));
			} catch (ParseException e) {
				e.printStackTrace();
				Log.d(appName, e.getMessage());
			}
			note.setCreationDate(creationDate);
			note.setTitle(c.getString(2));
			note.setContent(c.getString(3));
			note.setLocation(c.getString(4));
			note.setType(Note.TYPE.values()[c.getInt(5)]);

			list.add(note);
		}
		c.close();

		return list;

	}

}
