package com.stackroute.keepnote.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.stackroute.keepnote.model.Note;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/*
 * This class is implementing the NoteDAO interface. This class has to be annotated with @Repository
 * annotation.
 * @Repository - is an annotation that marks the specific class as a Data Access Object, thus 
 * 				 clarifying it's role.
 * @Transactional - The transactional annotation itself defines the scope of a single database 
 * 					transaction. The database transaction happens inside the scope of a persistence 
 * 					context.  
 * */
@Repository
@Transactional
public class NoteDAOImpl implements NoteDAO {

	@Autowired
	SessionFactory sessionFactory;

	/*
	 * Autowiring should be implemented for the SessionFactory.
	 */

	public NoteDAOImpl(SessionFactory sessionFactory) {

		this.sessionFactory=sessionFactory;
	}

	/*
	 * Save the note in the database(note) table.
	 */

	public boolean saveNote(Note note) {
		Session session=sessionFactory.getCurrentSession();
		session.save(note);
		session.flush();
		return true;

	}

	/*
	 * Remove the note from the database(note) table.
	 */

	public boolean deleteNote(int noteId) {
		Session session=sessionFactory.getCurrentSession();
		String query="from Note where noteId=:id";
		Query query1=session.createQuery(query);
		query1.setParameter("id",noteId);
		Note note = (Note) query1.uniqueResult();
		session.delete(note);
		session.flush();
		return true;
	}

	/*
	 * retrieve all existing notes sorted by created Date in descending
	 * order(showing latest note first)
	 */
	public List<Note> getAllNotes() {
		Session session=sessionFactory.getCurrentSession();
		String query="from Note";
		Query query1=session.createQuery(query);
		List<Note> notes=query1.getResultList();
		return notes;

	}

	/*
	 * retrieve specific note from the database(note) table
	 */
	public Note getNoteById(int noteId) {
		Session session=sessionFactory.getCurrentSession();
		String query="from Note where noteId=:id";
		Query query1=session.createQuery(query);
		query1.setParameter("id",noteId);
		Note note = (Note) query1.uniqueResult();
		session.flush();
		return note;


	}

	/* Update existing note */

	public boolean UpdateNote(Note note) {
		Session session=sessionFactory.getCurrentSession();
		session.update(note);
		session.flush();
		return true;

	}

}
