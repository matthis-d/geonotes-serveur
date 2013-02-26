package com.ducloslaurent.ejb.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "Tracks")
@XmlRootElement
public class Track implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "track_id")
	private Integer trackId;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "category")
	private String category;
	
	@Column(name = "distance")
	private Float distance;
	
	@Column(name = "created_at")
	@Temporal(TemporalType.DATE)
	private Date createdAt;
	
	@Column(name = "updated_at")
	@Temporal(TemporalType.DATE)
	private Date updatedAt;

	public Track() {
		super();
	}

	public Track(String name, String description, String category,
			Float distance, Date createdAt, Date updatedAt, Set<Note> notes) {
		super();
		this.name = name;
		this.description = description;
		this.category = category;
		this.distance = distance;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	@XmlElement
	public Integer getTrackId() {
		return trackId;
	}

	public void setTrackId(Integer track_id) {
		this.trackId = track_id;
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
	public Float getDistance() {
		return distance;
	}

	public void setDistance(Float distance) {
		this.distance = distance;
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Track [track_id=");
		builder.append(trackId);
		builder.append(", name=");
		builder.append(name);
		builder.append(", description=");
		builder.append(description);
		builder.append(", category=");
		builder.append(category);
		builder.append(", distance=");
		builder.append(distance);
		builder.append("]");
		return builder.toString();
	}

}
