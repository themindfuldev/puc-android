package br.edu.pucminas.notes.activities;

import java.util.Date;

import br.edu.pucminas.notes.R;
import br.edu.pucminas.notes.cloud.NoteCloudStorage;
import br.edu.pucminas.notes.model.Note;
import br.edu.pucminas.notes.model.Note.TYPE;
import br.edu.pucminas.notes.persistence.NoteDAO;
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

public class NotePersistenceActivity extends Activity {
	private LocationManager locationManager;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Location
		locationManager = (LocationManager) this
				.getSystemService(Context.LOCATION_SERVICE);
	}
	
	void insertNote(String title, String content, String type) {
		// Insertion
		Location currentLocationFromNetwork = locationManager
				.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		Location currentLocationFromGPS = locationManager
				.getLastKnownLocation(LocationManager.GPS_PROVIDER);

		Location currentLocation = currentLocationFromNetwork != null ? currentLocationFromNetwork
				: currentLocationFromGPS;

		if (currentLocation == null) {
			LocationListener locationListener = new NoteLocationListener(
					title, content, type);

			locationManager.requestLocationUpdates(
					LocationManager.GPS_PROVIDER, 0, 0,
					locationListener);
			locationManager.requestLocationUpdates(
					LocationManager.NETWORK_PROVIDER, 0, 0,
					locationListener);
			Toast.makeText(getApplicationContext(),
					R.string.waitingForLocation, Toast.LENGTH_SHORT)
					.show();
		} else {
			saveNote(title, content, type, currentLocation);
		}
	}
	
	private void saveNote(String title, String content, String type,
			Location currentLocation) {
		Note note = new Note();
		note.setCreationDate(new Date());
		note.setTitle(title);
		note.setContent(content);
		note.setType(TYPE.valueOf(type));
		note.setLocation(currentLocation);

		NoteDAO dao = new NoteDAO(NotePersistenceActivity.this);
		dao.insert(note);
		dao.close();
		Toast.makeText(getApplicationContext(), R.string.saved,
				Toast.LENGTH_SHORT).show();
		
		// Cloud storage
		new NoteCloudStorage().execute(note);
		
		finish();
	}

	private class NoteLocationListener implements LocationListener {
		private String title;
		private String content;
		private String type;
		private boolean active;

		public NoteLocationListener(String title, String content, String type) {
			this.title = title;
			this.content = content;
			this.type = type;
			active = true;
		}

		public void onLocationChanged(Location location) {
			if (active == true) {
				saveNote(title, content, type, location);
			}
			active = false;
		}

		public void onStatusChanged(String provider, int status, Bundle extras) {
		}

		public void onProviderEnabled(String provider) {
		}

		public void onProviderDisabled(String provider) {
		}
	}
	
}
