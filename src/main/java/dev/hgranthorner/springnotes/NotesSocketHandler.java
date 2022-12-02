package dev.hgranthorner.springnotes;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.socket.*;

@Component
public class NotesSocketHandler implements WebSocketHandler {
	final NoteRepository repo;
	final ObjectMapper json;

	public NotesSocketHandler(NoteRepository repo, ObjectMapper json) {
		this.repo = repo;
		this.json = json;
	}

	enum MessageType {
		MessageType(final String text) {

		}
		GetNotes("getNotes")
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		var notes = repo.findAll();
		session.sendMessage(new TextMessage(json.writeValueAsString(notes)));
		System.out.println("Connected!");
	}

	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		if (message instanceof TextMessage x) {
			var payload = x.getPayload();
			session.sendMessage(new TextMessage(payload + " modified"));
		}
	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {

	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {

	}

	@Override
	public boolean supportsPartialMessages() {
		return false;
	}
}
