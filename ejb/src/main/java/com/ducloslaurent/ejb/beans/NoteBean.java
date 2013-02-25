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
public class NoteBean implements LocalNoteBean {
	
	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Note> getAllNotes() {
		
		return this.em.createQuery("SELECT n FROM Note n")
				.getResultList();
	}

	@Override
	public Note getOneNoteById(Integer id) {
		
		return (Note) this.em.createQuery("SELECT n FROM Note n WHERE n.noteId=:noteId")
				.setParameter("noteId", id)
				.getSingleResult();
	}

	@Override
	public Note createNewNote(String name, String description,
			String category, Float latitude, Float longitude) {
		
		Note note = new Note(name, description, category, latitude, longitude, new Date(), new Date(), new HashSet<Track>());
		this.em.persist(note);
		this.em.flush();
		
		return note;
	}

	@Override
	public Note updateNote(Integer idToUpdate, String name,
			String description, String category, Float latitude, Float longitude) {
		Note note = (Note) this.em.createQuery("SELECT n FROM Note n WHERE n.noteId=:noteId")
						.setParameter("noteId", idToUpdate)
						.getSingleResult();
		
		note.setName(name);
		note.setCategory(category);
		note.setDescription(description);
		note.setLatitude(latitude);
		note.setLongitude(longitude);
		note.setUpdatedAt(new Date());
		
		this.em.flush();
		
		return note;
	}

	@Override
	public void deleteNote(Integer id) {
		
		Note note = (Note) this.em.createQuery("SELECT n FROM Note n WHERE n.noteId=:noteId")
				.setParameter("noteId", id)
				.getSingleResult();
		
		this.em.remove(this.em.merge(note));
		this.em.flush();

	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Track> getTracksFromNote(Integer noteId) {
		
		return this.em.createQuery("SELECT n.tracks FROM Note n WHERE n.noteId=:noteId")
				.setParameter("noteId", noteId)
				.getResultList();
	}

}
