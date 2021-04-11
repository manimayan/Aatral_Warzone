package aatral.warzone.model;

import java.util.Random;

import org.beanio.annotation.Field;
import org.beanio.annotation.Record;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Record

/**
 * <h1>Continent POJO</h1>
 * 
 * @author Manimaran Palani
 * @version 1.0
 * @since 2021-02-12
 */

public class InputContinent {

	int leftLimit = 97; // letter 'a'
	int rightLimit = 122; // letter 'z'
	int targetStringLength = 4;
	Random random = new Random();

	String generatedString = random.ints(leftLimit, rightLimit + 1)
			.limit(targetStringLength)
			.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
			.toString();

	public InputContinent(String ContinentId, String ContinentValue) {
		this.ContinentId = ContinentId;
		this.ContinentName = "addContinent"+generatedString;
		this.ContinentValue = ContinentValue;
	}

	public InputContinent(Continent value) {
		this.ContinentId = value.getContinentId();
		this.ContinentName = value.getContinentName();
		this.ContinentValue = value.getContinentValue();
	}

	public InputContinent(ConquestContinent convertConquest, int i) {
		this.ContinentId = Integer.toString(i);
		this.ContinentName = convertConquest.getContinentName();
		this.ContinentValue = convertConquest.getContinentValue();
	}

	@Field
	public String ContinentId;

	@Field
	private String ContinentName;

	@Field
	public String ContinentValue;
}
