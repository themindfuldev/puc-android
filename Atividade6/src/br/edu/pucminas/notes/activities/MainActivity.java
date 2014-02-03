package br.edu.pucminas.notes.activities;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import br.edu.pucminas.notes.R;
import br.edu.pucminas.notes.model.Note.TYPE;

public class MainActivity extends Activity {
	private static final int TAKE_A_PICTURE_REQUEST_CODE = 100;
	private String appName;
	private Uri pictureURI;

	public MainActivity() {
		super();
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Button buttonCreateANote = (Button) findViewById(R.id.buttonCreateANote);
		buttonCreateANote.setOnClickListener(createATextNote());

		Button buttonTakeAPicture = (Button) findViewById(R.id.buttonTakeAPicture);
		buttonTakeAPicture.setOnClickListener(takeAPicture());

		Button buttonRecordAnAudio = (Button) findViewById(R.id.buttonRecordAnAudio);
		buttonRecordAnAudio.setOnClickListener(recordAnAudio());

		Button buttonSeeNotes = (Button) findViewById(R.id.buttonSeeNotes);
		buttonSeeNotes.setOnClickListener(seeNotes());

		appName = getString(R.string.app_name);
	}

	private OnClickListener seeNotes() {
		return new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,
						SeeNotesActivity.class);
				startActivity(intent);
			}
		};
	}

	private OnClickListener recordAnAudio() {
		return new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,
						RecordAnAudioActivity.class);

				String filePath = getOutputMediaFileUri(false).toString();
				if (filePath != null) {
					intent.putExtra(RecordAnAudioActivity.FILENAME, filePath);
				}

				startActivity(intent);
			}
		};
	}

	private OnClickListener takeAPicture() {
		return new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

				pictureURI = Uri.fromFile(getOutputMediaFileUri(true));
				if (pictureURI != null) {
					intent.putExtra(MediaStore.EXTRA_OUTPUT, pictureURI);
				}

				startActivityForResult(intent, TAKE_A_PICTURE_REQUEST_CODE);
			}
		};
	}

	private OnClickListener createATextNote() {
		return new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,
						CreateATextNoteActivity.class);
				startActivity(intent);
			}
		};
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == TAKE_A_PICTURE_REQUEST_CODE) {
			if (resultCode == RESULT_OK) {
				Log.i(appName, "Image saved as " + pictureURI.toString());
				Intent intent = new Intent(this,
						CreateANoteActivity.class);
				intent.putExtra(CreateANoteActivity.CONTENT_EXTRA, pictureURI.toString());
				intent.putExtra(CreateANoteActivity.TYPE_EXTRA, TYPE.IMAGE.toString());
				startActivity(intent);
			} else if (resultCode == RESULT_CANCELED) {
				Log.e(appName, "Image capture was cancelled");
			} else {
				String message = "Falha na captura da imagem.";
				Log.e(appName, message);
				Toast.makeText(this, message, Toast.LENGTH_LONG).show();
			}
			pictureURI = null;
		} 
	}

	/** Create a File for saving an image or video */
	private File getOutputMediaFileUri(boolean isImage) {
		if (!Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			Log.d(appName, "external storage not mounted for writing.");
			return null;

		}

		File mediaStorageDir;
		if (isImage) {
			mediaStorageDir = new File(
					Environment
							.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
					"Notas");
		}
		else {
			mediaStorageDir = new File(
					Environment
							.getExternalStoragePublicDirectory(Environment.DIRECTORY_PODCASTS),
					"Notas");
		}

		if (!mediaStorageDir.exists()) {
			if (!mediaStorageDir.mkdirs()) {
				Log.d(appName, "failed to create directory");
				return null;
			}
		}

		// Create a media file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
				.format(new Date());
		
		File mediaFile;
		
		if (isImage) {
			mediaFile = new File(mediaStorageDir.getPath() + File.separator 
					+ "IMAGE_" + timeStamp + ".jpg");
		}
		else {
			mediaFile = new File(mediaStorageDir.getPath() + File.separator 
					+ "AUDIO_" + timeStamp + ".mp3");
		}

		return mediaFile;
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if (pictureURI != null) {
			outState.putString("pictureURI", pictureURI.toString());
		}
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		if (savedInstanceState.containsKey("pictureURI")) {
			pictureURI = Uri.parse(savedInstanceState.getString("pictureURI"));
		}
	}
}