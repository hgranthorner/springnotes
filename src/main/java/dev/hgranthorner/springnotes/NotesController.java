package dev.hgranthorner.springnotes;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
public class NotesController {
	final NoteRepository repository;

	public NotesController(NoteRepository repository) {
		this.repository = repository;
	}

	@GetMapping("/api/notes")
	@ResponseBody
	public String getNotes() {
		var notes = repository.findAll();
		return notes.stream().map(Note::toString).collect(Collectors.joining());
	}

	public record NoteDto(String author, String message) {
		public Note toModel() {
			return new Note(author, message);
		}
	}

	@PostMapping("/api/notes")
	@ResponseBody
	public String addNote(@RequestBody NoteDto note) {
		Note model = note.toModel();
		repository.save(model);
		return model.toString();
	}
}
