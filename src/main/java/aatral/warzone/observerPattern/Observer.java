package aatral.warzone.observerPattern;
/**
 * Interface class for the Observer, which forces all views to implement the
 * update method.
 */
public interface Observer {

	/**
	 * method to be implemented that reacts to the notification generally by
	 * interrogating the model object and displaying its newly updated state.
	 * 
	 * @param o
	 */
	public void update(Observable o);
}
 