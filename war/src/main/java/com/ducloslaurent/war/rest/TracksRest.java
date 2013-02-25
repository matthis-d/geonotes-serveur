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

import com.ducloslaurent.ejb.beans.LocalTrackBean;
import com.ducloslaurent.ejb.domain.Note;
import com.ducloslaurent.ejb.domain.Track;

@Path("/track")
@RequestScoped
public class TracksRest {
	
	@EJB
	private LocalTrackBean trackBean;
	
	@Path("/all")
	@GET
	@Produces("application/json")
	public Collection<Track> getAllTracks() {
		
		return this.trackBean.getAllTracks();
	}
	
	@Path("/new")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Track createNewTrack(Track track) {
		
		return this.trackBean.createNewTrack(track.getName(), track.getDescription(), track.getCategory(), 
				track.getDistance());
	}
	
	@Path("/update/{id}")
	@PUT
	@Consumes("application/json")
	@Produces("application/json")
	public Track updateTrack(@PathParam("id") Integer idToUpdate, Track track) {
		
		return this.trackBean.updateTrack(idToUpdate, track.getName(), track.getDescription(), 
				track.getCategory(), track.getDistance());
	}
	
	@Path("/remove/{id}")
	@DELETE
	public void removeTrack(@PathParam("id") Integer id) {
		
		this.trackBean.deleteTrack(id);
	}
	
	@Path("/add/{id}/notes")
	@POST
	@Consumes("application/json")
	public void addNotes(@PathParam("id") Integer id, Collection<Note> notes) {
		
		this.trackBean.addNotes(id, notes);
	}
	
	@Path("/delete/{id}/notes")
	@POST
	@Consumes("application/json")
	public void removeNotes(@PathParam("id") Integer id, Collection<Note> notes) {
		
		this.trackBean.removeNotes(id, notes);
	}

}
