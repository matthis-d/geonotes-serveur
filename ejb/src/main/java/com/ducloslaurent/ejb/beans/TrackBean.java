package com.ducloslaurent.ejb.beans;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.ducloslaurent.ejb.domain.Note;
import com.ducloslaurent.ejb.domain.Track;

@Stateless
public class TrackBean implements LocalTrackBean {
	
	@PersistenceContext
	private EntityManager em;
	
	@EJB
	private LocalNoteBean noteBean;

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
		
		Track track = (Track)this.em.createQuery("SELECT t FROM Track t WHERE t.trackId=:id")
				.setParameter("id", trackId)
				.getSingleResult();
		
		return this.em.createQuery("SELECT n FROM Note n WHERE n.track=:track")
				.setParameter("track", track)
				.getResultList();
	}

	@Override
	public Track addNotes(Integer trackId, Collection<Integer> notesIds) {
		Track track = (Track)this.em.createQuery("SELECT t FROM Track t WHERE t.trackId=:id")
				.setParameter("id", trackId)
				.getSingleResult();
		
		for(Integer id : notesIds) {
			Note note = (Note) this.em.createQuery("SELECT n FROM Note n WHERE n.noteId=:noteId")
					.setParameter("noteId", id)
					.getSingleResult();
			note.setTrack(track);
		}
		
		this.em.flush();
		
		return track;
	}

	@Override
	public Track removeNotes(Integer trackId, Collection<Integer> notesIds) {
		
		Track track = (Track)this.em.createQuery("SELECT t FROM Track t WHERE t.trackId=:id")
				.setParameter("id", trackId)
				.getSingleResult();
		
		for(Integer id : notesIds) {
			Note note = (Note) this.em.createQuery("SELECT n FROM Note n WHERE n.noteId=:noteId")
					.setParameter("noteId", id)
					.getSingleResult();
			note.setTrack(null);
		}
		
		this.em.flush();
		
		return track;
	}

}
