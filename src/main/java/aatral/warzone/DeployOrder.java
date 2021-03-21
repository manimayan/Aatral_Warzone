package aatral.warzone;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class DeployOrder extends Order{
	
	private String CountryID;
	
	private String armies;
	
	public void execute()
	{
		
		System.out.println(this.CountryID+" "+this.armies);
	}
	
}
