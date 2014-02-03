package br.edu.pucminas.notes.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import br.edu.pucminas.notes.R;

public class CreateANoteActivity extends Activity {
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_a_note);
        
        Button buttonSave = (Button)findViewById(R.id.buttonSave);
        buttonSave.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				finish();
				Toast.makeText(getApplicationContext(), R.string.saved, Toast.LENGTH_SHORT).show();
			}
		});
        
    }
    
}