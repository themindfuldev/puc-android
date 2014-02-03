package br.edu.pucminas.notes.cloud;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;
import br.edu.pucminas.notes.model.Note;

public class NoteCloudStorage extends AsyncTask<Note, Void, Void> {
	
	private SimpleDateFormat dateFormat;

	public NoteCloudStorage() {
		dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	}

	@Override
	protected Void doInBackground(Note... notes) {
		/*HttpURLConnection urlConnection = null;
		try {
			URL url = new URL("http://jdrop.org/save");
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setDoOutput(true);
			urlConnection.setChunkedStreamingMode(0);
			OutputStreamWriter writer = new OutputStreamWriter(new BufferedOutputStream(
					urlConnection.getOutputStream()));
			writeNote(writer, notes[0]);
		} catch (IOException e) {
			Log.e("CursoAndroid", "Erro ao acessar servidor", e);
		} finally {
			if (urlConnection != null)
				urlConnection.disconnect();
		}*/
		return null;
	}

	private void writeNote(OutputStreamWriter out, Note note) throws IOException {
		out.write("?appname=TomaNotas&title=");
		out.write(URLEncoder.encode(note.getTitle()));
		out.write("&version=1.0&summary=");
		out.write(URLEncoder.encode(note.getType().toString()));
		out.write("&json=");
		out.write(writeJSON(note).toString());
	}
	
	public JSONObject writeJSON(Note note) {
		JSONObject object = new JSONObject();
		try {
			object.put("creationDate",  dateFormat.format(note.getCreationDate()));
			object.put("title", note.getTitle());
			object.put("content", note.getContent());
			object.put("type", note.getType().toString());
			object.put("location", note.getLocation());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return object;
	}

}
