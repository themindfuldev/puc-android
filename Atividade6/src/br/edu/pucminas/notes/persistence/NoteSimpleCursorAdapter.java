package br.edu.pucminas.notes.persistence;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import br.edu.pucminas.notes.R;
import br.edu.pucminas.notes.model.Note;

public class NoteSimpleCursorAdapter extends SimpleCursorAdapter {

	public NoteSimpleCursorAdapter(Context context, int layout, Cursor c,
			String[] from, int[] to) {
		super(context, layout, c, from, to);
	}

	@Override
	public void bindView(View view, final Context context, Cursor cursor) {
		super.bindView(view, context, cursor);
		
		// Type 
		int typeCol = cursor.getColumnIndex(NoteDAO.TYPE);
		int type = cursor.getInt(typeCol);
		
		TextView textType = (TextView) view.findViewById(R.id.textType);
		Note.TYPE noteType = Note.TYPE.values()[type];
		textType.setText(noteType.toString());

		// Content
		Button viewContentButton = (Button) view.findViewById(R.id.buttonViewContent);
		int contentCol = cursor.getColumnIndex(NoteDAO.CONTENT);
		final String content = cursor.getString(contentCol);

		switch (noteType) {
		case AUDIO: 
			viewContentButton.setVisibility(View.VISIBLE);
			viewContentButton.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Intent intent = new Intent(Intent.ACTION_VIEW);
					intent.setDataAndType(Uri.parse(content), "audio/mp3");
					context.startActivity(intent);
				}
			});
			break;
		case IMAGE:
			viewContentButton.setVisibility(View.VISIBLE);
			viewContentButton.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Intent intent = new Intent(Intent.ACTION_VIEW);
					intent.setDataAndType(Uri.parse(content), "image/jpg");
					context.startActivity(intent);
				}
			});
			break;
		case LINK:
			viewContentButton.setVisibility(View.VISIBLE);
			viewContentButton.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Intent intent = new Intent(Intent.ACTION_VIEW);
					intent.setData(Uri.parse(content));
					context.startActivity(intent);
				}
			});
			
			break;
		}
	}

}
