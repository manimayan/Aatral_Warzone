package aatral.warzone.model;

import java.util.List;

import org.beanio.annotation.Field;
import org.beanio.annotation.Record;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Record

/**
 * <h1>Border POJO</h1>
 * 
 * @author Manimaran Palani
 * @version 1.0
 * @since 2021-02-12
 */

public class Borders {

	@Field(maxOccurs = 1, minOccurs = 0)
	private String countryId;

	@Field(collection = List.class, maxOccurs = -1)
	List<String> adjacentCountries;

}
