package com.example.crud;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.crud.controller.NoteController;
import com.example.crud.model.Note;
import com.example.crud.repository.NoteRepository;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class CrudApplicationTests {

	@Mock
    private NoteRepository noteRepository;

    @InjectMocks
    private NoteController noteController;

	@Test
	public void testListNotes() {
        List<Note> mockNotes = new ArrayList<>();
        mockNotes.add(new Note(1L, "Title 1", "Content 1"));
        mockNotes.add(new Note(2L, "Title 2", "Content 2"));

        Mockito.when(noteRepository.findAll()).thenReturn(mockNotes);

        List<Note> result = noteController.getAllNotes();

        assertEquals(2, result.size());
        assertEquals("Title 1", result.get(0).getTitle());
        assertEquals("Content 1", result.get(0).getContent());
        assertEquals("Title 2", result.get(1).getTitle());
        assertEquals("Content 2", result.get(1).getContent());
    }


    @Test
    public void testCreateNote() {
        Note mockNote = new Note();
        mockNote.setId(1L);
        mockNote.setTitle("Test Title");
        mockNote.setContent("Test Content");

        Mockito.when(noteRepository.save(Mockito.any(Note.class))).thenReturn(mockNote);

        Note createdNote = noteController.createNote(mockNote);

        assertNotNull(createdNote);
        assertEquals(1L, createdNote.getId().longValue());
        assertEquals("Test Title", createdNote.getTitle());
        assertEquals("Test Content", createdNote.getContent());
    }

	@Test
    public void testGetNoteById() {
        Long noteId = 1L;
        Note mockNote = new Note();
        mockNote.setId(noteId);
        mockNote.setTitle("Test Title");
        mockNote.setContent("Test Content");

        Mockito.when(noteRepository.findById(noteId)).thenReturn(Optional.of(mockNote));

        Note foundNote = noteController.getNoteById(noteId);

        assertNotNull(foundNote);
        assertEquals(noteId, foundNote.getId());
        assertEquals("Test Title", foundNote.getTitle());
        assertEquals("Test Content", foundNote.getContent());
    }


	@Test
	public void testUpdateNote() {
        Long noteId = 1L;
        Note mockNote = new Note();
        mockNote.setId(noteId);
        mockNote.setTitle("Title");
        mockNote.setContent("Content");

        Note updatedNote = new Note();
        updatedNote.setId(noteId);
        updatedNote.setTitle("Updated Title");
        updatedNote.setContent("Updated Content");

        Mockito.when(noteRepository.findById(noteId)).thenReturn(Optional.of(mockNote));
        Mockito.when(noteRepository.save(Mockito.any(Note.class))).thenReturn(updatedNote);

        Note responseEntity = noteController.updateNote(noteId, updatedNote);

        assertEquals(noteId, responseEntity.getId());
        assertEquals("Updated Title", responseEntity.getTitle());
        assertEquals("Updated Content", responseEntity.getContent());
    }


	@Test
    public void testDeleteNote() {
        Long noteId = 1L;
        Note mockNote = new Note();
        mockNote.setId(noteId);
        mockNote.setTitle("Title");
        mockNote.setContent("Content");

        Mockito.when(noteRepository.findById(noteId)).thenReturn(Optional.of(mockNote));

        ResponseEntity<?> responseEntity = noteController.deleteNote(noteId);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }


}
