package aatral.warzone.observerPattern;


/**
 * <h1>Observer</h1> Interface class for the Observer, which forces all views to implement the update method
 *
 * @author William
 * @version 1.0
 * @since 24-02-2021
 */
public interface Observer {

	/**
	 * method to be implemented that reacts to the notification generally by
	 * interrogating the model object and displaying its newly updated state.
	 * 
	 * @param o observable object
	 */
	public void update(Observable o);
}
 