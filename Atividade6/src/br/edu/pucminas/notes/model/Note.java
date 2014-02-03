package br.edu.pucminas.notes.model;

import java.util.Date;

import android.location.Location;

public class Note {

	public enum TYPE {
		TEXT, AUDIO, IMAGE, LINK;
	}

	private int id;
	private Date creationDate;
	private String title;
	private String content;
	private String location;
	private TYPE type;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public TYPE getType() {
		return type;
	}

	public void setType(TYPE type) {
		this.type = type;
	}

	public void setLocation(Location location) {
		this.location = "latitude: " + location.getLatitude() + ", longitude: " + location.getLongitude();
	}
	
}
