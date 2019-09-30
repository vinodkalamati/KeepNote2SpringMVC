package com.stackroute.keepnote.controller;

import com.stackroute.keepnote.config.ApplicationContextConfig;
import com.stackroute.keepnote.dao.NoteDAO;
import com.stackroute.keepnote.model.Note;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

/*
 * Annotate the class with @Controller annotation.@Controller annotation is used to mark 
 * any POJO class as a controller so that Spring can recognize this class as a Controller
 */
@Controller
public class NoteController {

	@Autowired
	NoteDAO noteDAO;
	public NoteController(NoteDAO noteDao) {

		noteDao=this.noteDAO;
	}




//ApplicationContext context=new AnnotationConfigApplicationContext(ApplicationContextConfig.class);

	/*
	 * From the problem statement, we can understand that the application requires
	 * us to implement the following functionalities.
	 * 
	 * 1. display the list of existing notes from the persistence data. Each note
	 * should contain Note Id, title, content, status and created date. 
	 * 2. Add a new note which should contain the note id, title, content and status. 
	 * 3. Delete an existing note 
	 * 4. Update an existing note
	 * 
	 */

	/*
	 * Autowiring should be implemented for the NoteDAO.
	 * Create a Note object.
	 * 
	 */




	/*
	 * Define a handler method to read the existing notes from the database and add
	 * it to the ModelMap which is an implementation of Map, used when building
	 * model data for use with views. it should map to the default URL i.e. "/index"
	 */

	@RequestMapping("/")
	public String handler(){
		ModelMap map=new ModelMap();
		map.addAllAttributes(noteDAO.getAllNotes());
		return "index";
	}



	/*
	 * Define a handler method which will read the NoteTitle, NoteContent,
	 * NoteStatus from request parameters and save the note in note table in
	 * database. Please note that the CreatedAt should always be auto populated with
	 * system time and should not be accepted from the user. Also, after saving the
	 * note, it should show the same along with existing messages. Hence, reading
	 * note has to be done here again and the retrieved notes object should be sent
	 * back to the view using ModelMap This handler method should map to the URL
	 * "/add".
	 */

	@RequestMapping(value = "/add",method = RequestMethod.POST)
	public String handler1(HttpServletRequest request, ModelMap mv){
		Note note=new Note();
		if (request.getParameter("noteTitle").isEmpty()||(request.getParameter("noteContent").isEmpty())||request.getParameter("noteStatus").isEmpty())
		{
			mv.addAttribute("error","could not add");
			return "index";
		}
		else{
			String id=request.getParameter("noteId");
			String title=request.getParameter("noteTitle");
			String content=request.getParameter("noteContent");
			String status=request.getParameter("noteStatus");
			System.out.println(title+content+status);
	//		note.setNoteId(Integer.parseInt(id));
			note.setNoteContent(content);
			note.setNoteStatus(status);
			note.setNoteTitle(title);
			note.setCreatedAt(LocalDateTime.now());
			noteDAO.saveNote(note);
			mv.addAttribute("list", noteDAO.getAllNotes());
			System.out.println(noteDAO.getAllNotes());
			return "redirect:/";
			}

	}



	/*
	 * Define a handler method which will read the NoteId from request parameters
	 * and remove an existing note by calling the deleteNote() method of the
	 * NoteRepository class.This handler method should map to the URL "/delete".
	 */
	@RequestMapping("/delete")
	public String Delete(int noteId){
		noteDAO.deleteNote(noteId);
		return "redirect:/";

	}
	/*
	 * Define a handler method which will update the existing note. This handler
	 * method should map to the URL "/update".
	 */
	@RequestMapping("/update")
	public String update(ModelMap map,HttpServletRequest request,HttpServletResponse response){
		Note note=new Note();
		String id=request.getParameter("noteId");
		String title=request.getParameter("noteTitle");
		String content=request.getParameter("noteContent");
		String status=request.getParameter("noteStatus");
//		note.setNoteId(Integer.parseInt(id));
		note.setNoteContent(content);
		note.setNoteStatus(status);
		note.setNoteTitle(title);
		note.setCreatedAt(LocalDateTime.now());
		noteDAO.UpdateNote(note);
		map.addAttribute(note);
		return "redirect:/";
	}

}
