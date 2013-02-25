package com.ducloslaurent.ejb.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "Notes")
@XmlRootElement
public class Note implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "note_id")
	private Integer noteId;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "category")
	private String category;
	
	@Column(name = "latitude")
	private Float latitude;
	
	@Column(name = "longitude")
	private Float longitude;
	
	@Column(name = "created_at")
	@Temporal(TemporalType.DATE)
	private Date createdAt;
	
	@Column(name = "updated_at")
	@Temporal(TemporalType.DATE)
	private Date updatedAt;
	
	@ManyToMany(mappedBy = "notes")
	private Set<Track> tracks;

	public Note() {
		super();
		
		this.tracks = new HashSet<>();
	}

	public Note(String name, String description, String category,
			Float latitude, Float longitude, Date createdAt, Date updatedAt,
			Set<Track> tracks) {
		super();
		this.name = name;
		this.description = description;
		this.category = category;
		this.latitude = latitude;
		this.longitude = longitude;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.tracks = tracks;
	}

	@XmlElement
	public Integer getNoteId() {
		return noteId;
	}

	public void setNoteId(Integer id) {
		this.noteId = id;
	}

	@XmlElement
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlElement
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@XmlElement
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@XmlElement
	public Float getLatitude() {
		return latitude;
	}

	public void setLatitude(Float latitude) {
		this.latitude = latitude;
	}

	@XmlElement
	public Float getLongitude() {
		return longitude;
	}

	public void setLongitude(Float longitude) {
		this.longitude = longitude;
	}

	@XmlElement
	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	@XmlElement
	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	@XmlElement
	public Set<Track> getTracks() {
		return tracks;
	}

	public void setTracks(Set<Track> tracks) {
		this.tracks = tracks;
	}
	
	public void addTrack(Track track) {
		this.tracks.add(track);
	}
	
	public void removeTrack(Track track) {
		this.tracks.remove(track);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Note [id=");
		builder.append(noteId);
		builder.append(", name=");
		builder.append(name);
		builder.append(", description=");
		builder.append(description);
		builder.append(", category=");
		builder.append(category);
		builder.append(", latitude=");
		builder.append(latitude);
		builder.append(", longitude=");
		builder.append(longitude);
		builder.append(", createdAt=");
		builder.append(createdAt);
		builder.append(", updatedAt=");
		builder.append(updatedAt);
		builder.append(", tracks=");
		builder.append(tracks);
		builder.append("]");
		return builder.toString();
	}
	
}
