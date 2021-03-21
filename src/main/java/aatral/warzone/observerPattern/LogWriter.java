package aatral.warzone.observerPattern;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.time.LocalDateTime;

/**
 * <h1>Observer LogWriter Class to implement observer pattern</h1>
 * 
 * @author Manimaran Palani
 * @version 1.0
 * @since 2021-03-21
 */


public class LogWriter implements Observer {

	private LogEntryBuffer subject;

	public LogWriter(LogEntryBuffer subject) {
		this.subject = subject;
		this.subject.attach(this);
	}

	@Override
	public void update(Observable o) {

		WriteLogFile(o);

	}

	/**
	 * WriteLogFile method is used to write the commands in a text file
	 * 
	 * @param Observable o 
	 */
	public void WriteLogFile(Observable o) {

		String FILE_NAME = "src/main/resources/Gamelog.txt";

		String timeNow = LocalDateTime.now().toString();
		String gamePhase = ((LogEntryBuffer) o).getGamePhase();
		String player = (((LogEntryBuffer) o).getPlayer() != null) ? ((LogEntryBuffer) o).getPlayer() : null;
		String command = ((LogEntryBuffer) o).getCommand();
		String status = ((LogEntryBuffer) o).getStatus();

		String writeRecord = new LogEntryBuffer().returnEntry(timeNow, gamePhase, player, command, status);

		try {
			Writer output;
			output = new BufferedWriter(new FileWriter(FILE_NAME, true));
			output.append("\n");
			output.append(writeRecord);
			output.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
