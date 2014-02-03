package br.edu.pucminas.notes.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import br.edu.pucminas.notes.R;

public class MainActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button buttonCreateANote = (Button)findViewById(R.id.buttonCreateANote);
        buttonCreateANote.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, CreateANoteActivity.class);
				startActivity(intent);
			}
		});
        
        Button buttonTakeAPicture = (Button)findViewById(R.id.buttonTakeAPicture);
        buttonTakeAPicture.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), R.string.willTakeAPicture, Toast.LENGTH_SHORT).show();
			}
		});
        
        Button buttonRecordAnAudio = (Button)findViewById(R.id.buttonRecordAnAudio);
        buttonRecordAnAudio.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), R.string.willRecordAnAudio, Toast.LENGTH_SHORT).show();
			}
		});
        
        Button buttonSeeNotes = (Button)findViewById(R.id.buttonSeeNotes);
        buttonSeeNotes.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, SeeNotesActivity.class);
				startActivity(intent);
			}
		});
    }
}