package com.ducloslaurent.ejb.beans;

import java.util.Collection;

import javax.ejb.Local;

import com.ducloslaurent.ejb.domain.Note;
import com.ducloslaurent.ejb.domain.Track;

@Local
public interface LocalTrackBean {
	
	public Collection<Track> getAllTracks();
	
	public Track getOneTrackById(Integer id);
	
	public Track createNewTrack(String name, String description, String category,
			Float distance);
	
	public Track updateTrack(Integer idToUpdate, String name, String description, String category,
			Float distance);
	
	public void deleteTrack(Integer id);
	
	public Collection<Note> getNotesFromTrack(Integer trackId);
	
	public Track addNotes(Integer trackId, Collection<Note> notes);
	
	public Track removeNotes(Integer trackId, Collection<Note> notes);

}
