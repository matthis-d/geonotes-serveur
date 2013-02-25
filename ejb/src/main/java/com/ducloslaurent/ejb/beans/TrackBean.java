package com.ducloslaurent.ejb.beans;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.ducloslaurent.ejb.domain.Note;
import com.ducloslaurent.ejb.domain.Track;

@Stateless
public class TrackBean implements LocalTrackBean {
	
	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Track> getAllTracks() {
		
		return this.em.createQuery("SELECT t FROM Track t")
					.getResultList();
	}

	@Override
	public Track getOneTrackById(Integer id) {
		
		return (Track)this.em.createQuery("SELECT t FROM Track t WHERE t.trackId=:id")
				.setParameter("id", id)
				.getSingleResult();
	}

	@Override
	public Track createNewTrack(String name, String description,
			String category, Float distance) {
		
		Track track = new Track(name, description, category, distance, new Date(), new Date(), new HashSet<Note>());
		
		this.em.persist(track);
		this.em.flush();
		
		return track;
	}

	@Override
	public Track updateTrack(Integer idToUpdate, String name,
			String description, String category, Float distance) {
		
		Track track = (Track)this.em.createQuery("SELECT t FROM Track t WHERE t.trackId=:id")
				.setParameter("id", idToUpdate)
				.getSingleResult();
		
		track.setName(name);
		track.setDescription(description);
		track.setCategory(category);
		track.setDistance(distance);
		track.setUpdatedAt(new Date());
		
		this.em.flush();
		
		return track;
	}

	@Override
	public void deleteTrack(Integer id) {
		Track track = (Track)this.em.createQuery("SELECT t FROM Track t WHERE t.trackId=:id")
				.setParameter("id", id)
				.getSingleResult();
		
		this.em.remove(this.em.merge(track));
		this.em.flush();

	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Note> getNotesFromTrack(Integer trackId) {
		
		return this.em.createQuery("SELECT t.notes FROM Track t WHERE t.trackId=:trackId")
				.setParameter("trackId", trackId)
				.getResultList();
	}

	@Override
	public Track addNotes(Integer trackId, Collection<Note> notes) {
		Track track = (Track)this.em.createQuery("SELECT t FROM Track t WHERE t.trackId=:id")
				.setParameter("id", trackId)
				.getSingleResult();
		
		
		for(Note note : notes) {
			track.addNote(note);
		}
		
		this.em.flush();
		
		return track;
	}

	@Override
	public Track removeNotes(Integer trackId, Collection<Note> notes) {
		Track track = (Track)this.em.createQuery("SELECT t FROM Track t WHERE t.trackId=:id")
				.setParameter("id", trackId)
				.getSingleResult();
		
		
		for(Note note : notes) {
			track.removeNote(note);
		}
		
		this.em.flush();
		
		return track;
	}

}
