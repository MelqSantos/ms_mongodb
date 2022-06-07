package br.com.digisystem.exceptions;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StandardError {
	
	protected LocalDateTime timestamp;
	protected Integer status;
	protected String error;
	protected String message;
	protected String path;
}
