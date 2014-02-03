package br.edu.pucminas.notes.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import br.edu.pucminas.notes.R;
import br.edu.pucminas.notes.model.Note.TYPE;

public class CreateANoteActivity extends NotePersistenceActivity {
	public static final String TYPE_EXTRA = "type";
	public static final String CONTENT_EXTRA = "content";
	private Button buttonSave;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_a_note);

		// Events
		buttonSave = (Button) findViewById(R.id.buttonSave);
		buttonSave.setOnClickListener(saveTextNote());

		String type = "";
		String content = "";

		Intent intent = getIntent();
		if (intent.getAction() != null
				&& intent.getAction().equals("android.intent.action.SEND")) {
			type = TYPE.LINK.toString();
			intent.getParcelableExtra(Intent.EXTRA_TEXT);
			content = intent.getExtras().getString(Intent.EXTRA_TEXT);
		} else {
			intent.getParcelableExtra(TYPE_EXTRA);
			intent.getParcelableExtra(CONTENT_EXTRA);

			if (intent.getExtras().isEmpty() == false) {
				type = intent.getExtras().getString(TYPE_EXTRA);
				content = intent.getExtras().getString(CONTENT_EXTRA);
			}
		}

		TextView textViewContent = (TextView) findViewById(R.id.textContent);
		textViewContent.setText(content);

		TextView textViewType = (TextView) findViewById(R.id.textType);
		textViewType.setText(type);
	}

	private OnClickListener saveTextNote() {
		return new OnClickListener() {
			public void onClick(View v) {
				EditText editTextTitle = (EditText) findViewById(R.id.editTextTitle);
				TextView textContent = (TextView) findViewById(R.id.textContent);
				TextView textType = (TextView) findViewById(R.id.textType);

				String title = editTextTitle.getEditableText().toString();
				String content = textContent.getText().toString();
				String type = textType.getText().toString();

				// Validation
				if (title.length() == 0) {
					Toast.makeText(getApplicationContext(),
							R.string.titleIsEmpty, Toast.LENGTH_SHORT).show();
					return;
				}

				buttonSave.setEnabled(false);
				insertNote(title, content, type);
			}
			
		};
	}

}