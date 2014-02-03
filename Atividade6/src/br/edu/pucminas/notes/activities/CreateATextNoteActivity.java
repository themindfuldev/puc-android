package br.edu.pucminas.notes.activities;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import br.edu.pucminas.notes.R;
import br.edu.pucminas.notes.model.Note;

public class CreateATextNoteActivity extends NotePersistenceActivity {
	
    private Button buttonSave;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_a_text_note);
        
        buttonSave = (Button)findViewById(R.id.buttonSave);
        buttonSave.setOnClickListener(saveTextNote());
        
    }

	private OnClickListener saveTextNote() {
		return new OnClickListener() {
			public void onClick(View v) {
				EditText editTextTitle = (EditText) findViewById(R.id.editTextTitle);
				EditText editTextContent = (EditText) findViewById(R.id.editTextContent);
				
				String title = editTextTitle.getEditableText().toString();
				String content = editTextContent.getEditableText().toString();
				String type = Note.TYPE.TEXT.toString();
				
				// Validation
				if (title.length() == 0) {
					Toast.makeText(getApplicationContext(), R.string.titleIsEmpty, Toast.LENGTH_SHORT).show();
					return;
				}
				if (content.length() == 0) {
					Toast.makeText(getApplicationContext(), R.string.contentIsEmpty, Toast.LENGTH_SHORT).show();
					return;
				}
				
				buttonSave.setEnabled(false);
				insertNote(title, content, type);
			}
		};
	}
    
}