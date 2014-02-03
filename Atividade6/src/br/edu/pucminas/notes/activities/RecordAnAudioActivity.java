package br.edu.pucminas.notes.activities;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import br.edu.pucminas.notes.R;
import br.edu.pucminas.notes.model.Note.TYPE;

public class RecordAnAudioActivity extends Activity {
	public static final String FILENAME = "filename";
	private MediaRecorder recorder;
	private String fileName;
	private Button buttonStart;
	private Button buttonSave;
	private String appName;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.record_an_audio);
		
		// Intent
		Intent intent = getIntent();
		intent.getParcelableExtra(FILENAME);

		if (intent.getExtras().isEmpty() == false) {
			fileName = intent.getExtras().getString(FILENAME);
		}
		
		//fileName = Environment.getExternalStorageDirectory().getAbsolutePath() + "/audiorecordtest.3gp";
		
		// Events
		buttonStart = (Button) findViewById(R.id.buttonStart);
		buttonStart.setOnClickListener(startAudioRecording());
		
		buttonSave = (Button) findViewById(R.id.buttonSave);
		buttonSave.setOnClickListener(saveAudioNote());
		
		appName = getString(R.string.app_name);
	}

	private OnClickListener startAudioRecording() {
		return new OnClickListener() {
			public void onClick(View v) {
				try {
					recorder=new MediaRecorder();
					recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
					recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
					recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
					recorder.setOutputFile(fileName);
					recorder.prepare();
					recorder.start();
					
					buttonStart.setEnabled(false);
					buttonSave.setEnabled(true);
				} catch (IllegalStateException e) {
					Log.e(appName, e.getMessage());
				} catch (IOException e) {
					Log.e(appName, e.getMessage());
				}
			}
		};
	}

	private OnClickListener saveAudioNote() {
		return new OnClickListener() {
			public void onClick(View v) {
				recorder.stop();
				recorder.release();
				
				Log.i(appName, "Audio saved as " + fileName);
				Intent intent = new Intent(RecordAnAudioActivity.this,
						CreateANoteActivity.class);
				intent.putExtra(CreateANoteActivity.CONTENT_EXTRA, fileName);
				intent.putExtra(CreateANoteActivity.TYPE_EXTRA, TYPE.AUDIO.toString());
				startActivity(intent);
				finish();
			}
		};
	}
}
