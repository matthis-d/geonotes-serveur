package com.ducloslaurent.ejb.beans;

import java.util.Collection;

import javax.ejb.Local;

import com.ducloslaurent.ejb.domain.Note;
import com.ducloslaurent.ejb.domain.Track;

@Local
public interface LocalNoteBean {
	
	public Collection<Note> getAllNotes();
	
	public Note getOneNoteById(Integer id);
	
	public Note createNewNote(String name, String description, String category,
			Float latitude, Float longitude);
	
	public Note updateNote(Integer idToUpdate, String name, String description, String category,
			Float latitude, Float longitude);
	
	public void deleteNote(Integer id);
	
	public Track getTrackFromNote(Integer noteId);

}
