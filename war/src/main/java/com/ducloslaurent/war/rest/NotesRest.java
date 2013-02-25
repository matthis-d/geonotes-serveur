package com.ducloslaurent.war.rest;

import java.util.Collection;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.ducloslaurent.ejb.beans.LocalNoteBean;
import com.ducloslaurent.ejb.domain.Note;
import com.ducloslaurent.ejb.domain.Track;

@Path("/note")
@RequestScoped
public class NotesRest {
	
	@EJB
	private LocalNoteBean noteBeans;
	
	@Path("/all")
	@GET
	@Produces("application/json")
	public Collection<Note> getAllNotes() {
		
		return this.noteBeans.getAllNotes();
	}
	
	@Path("/new")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Note createNewNote(Note note) {
		
		return this.noteBeans.createNewNote(note.getName(), note.getDescription(), note.getCategory(),
				note.getLatitude(), note.getLongitude());
	}

	
	@Path("/{id}")
	@GET
	@Produces("application/json")
	public Note getNote(@PathParam("id") Integer id) {
		return this.noteBeans.getOneNoteById(id);
	}
	
	@Path("/update/{id}")
	@PUT
	@Consumes("application/json")
	@Produces("application/json")
	public Note updateNote(@PathParam("id") Integer idToUpdate, Note note) {
		
		return this.noteBeans.updateNote(idToUpdate, note.getName(), note.getDescription(), note.getCategory(),
				note.getLatitude(), note.getLongitude());
		
	}
	
	@Path("/remove/{id}")
	@DELETE
	public void removeNote(@PathParam("id") Integer id) {
		this.noteBeans.deleteNote(id);
	}
	
	@Path("/tracks/{id}")
	@GET
	@Produces("application/json")
	public Collection<Track> getTracksFromNote(@PathParam("id") Integer noteId) {
		return this.noteBeans.getTracksFromNote(noteId);
	}

}
