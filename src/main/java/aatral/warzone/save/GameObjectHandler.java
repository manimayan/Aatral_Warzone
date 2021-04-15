package aatral.warzone.save;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

public class GameObjectHandler {

	public boolean saveGame(GameObjects gameElements, String savename) {
		boolean saved = false;
		try {
			FileOutputStream saveFile = new FileOutputStream("src/main/resources/savedGame/" + savename + "-game.ser");
			ObjectOutputStream save = new ObjectOutputStream(saveFile);
			save.writeObject(gameElements);
			save.close();
			saved = true;
		} catch (Exception exc) {
			exc.printStackTrace();
			saved = false;
		}
		return saved;
	}

	public GameObjects loadGame(String savename) {
		GameObjects gameElements = new GameObjects();
		try {
			FileInputStream saveFile = new FileInputStream("src/main/resources/savedGame/" + savename + "-game.ser");
			ObjectInputStream save = new ObjectInputStream(saveFile);
			gameElements = (GameObjects) save.readObject();
			save.close();

		} catch (Exception exc) {
			exc.printStackTrace();
		}
		return gameElements;
	}

	public Set<String> listFilesUsingDirectoryStream() throws IOException {
		Set<String> fileList = new HashSet<>();
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get("src/main/resources/savedGame"))) {
			for (Path path : stream) {
				if (!Files.isDirectory(path)) {
					fileList.add(path.getFileName().toString());
				}
			}
		}
		Set<String> customFileList = new HashSet<>();
		for (String string : fileList) {
			String result = string.substring(0, string.indexOf("-"));
			customFileList.add(result);
		}
		return customFileList;
	}

}
