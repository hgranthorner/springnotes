package dev.hgranthorner.springnotes;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "notes")
@ToString
@NoArgsConstructor
public class Note {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer id;

	public Note(String author, String message) {
		this.author = author;
		this.message = message;
	}

	@Nonnull
	public String author;
	@Nonnull
	public String message;
	@Nullable
	public String tags;
	@Column(name = "last_updated")
	@UpdateTimestamp
	private LocalDateTime lastUpdated;
}
