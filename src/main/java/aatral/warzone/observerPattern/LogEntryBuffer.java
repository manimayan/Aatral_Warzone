package aatral.warzone.observerPattern;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <h1>Observable LogEntryBuffer Class to implement observer pattern</h1>
 * 
 * @author Manimaran Palani
 * @version 1.0
 * @since 2021-03-21
 */


@Getter
@Setter
@NoArgsConstructor
public class LogEntryBuffer extends Observable implements Serializable {

	private static final long serialVersionUID = 1L;

	private String timeStamp;

	private String gamePhase;

	private String player;

	private String command;

	private String status;

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(LocalDateTime timeStamp) {
		this.timeStamp = LocalDateTime.now().toString();
	}

	/**
	 * info method is used notify observer of gameplay phase
	 * @param gamePhase :  gamePhase
	 * @param player :     player
	 * @param command :    command
	 * @param status :     status
	 */
	public void info(String gamePhase, String player, String command, String status) {
		this.gamePhase = gamePhase;
		this.player = player;
		this.command = command;
		this.status =  status;
		notifyObservers(this);
	}

	/**
	 * info method is used notify observer of mapeditor phase
	 * @param gamePhase :  gamePhase
	 * @param command :    command
	 * @param status :     status
	 */
	public void info(String gamePhase, String command, String status) {
		this.gamePhase = gamePhase;
		this.command = command;
		this.status =  status;
		notifyObservers(this);
	}

	/**
	 * returnEntry method is compose write object
	 * @param timeStamp: timeStamp
	 * @param gamePhase :  gamePhase
	 * @param player :     player
	 * @param command :    command
	 * @param status :     status
	 * 
	 * @return toGameString
	 * @return toMapEditorString
	 */
	public String returnEntry(String timeStamp, String gamePhase, String player, String command, String status) {
		this.timeStamp = timeStamp;
		this.gamePhase = gamePhase;
		this.player = player;
		this.command = command;
		this.status =  status;
		if (player != null) {
			return toGameString();
		} else {
			return toMapEditorString();
		}

	}

	/**
	 * toGameString method is compose write object
	 * @return string
	 */
	public String toGameString() {
		return new StringBuffer(this.timeStamp).append("  GamePhase: " + this.gamePhase)
				.append("  Player: " + this.player).append(" Command: " + this.command).append(" Status: " + this.status).toString();
	}

	/**
	 * toMapEditorString method is compose write object
	 * @return string
	 */
	public String toMapEditorString() {
		return new StringBuffer(this.timeStamp).append("  GamePhase: " + this.gamePhase)
				.append(" Command: " + this.command).append(" Status: " + this.status).toString();
	}
}
