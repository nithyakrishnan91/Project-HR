import javax.swing.event.EventListenerList;

public class LowAppraisalComponent {

	protected EventListenerList listenerList = new EventListenerList();
	
	public void addEventListener(LowAppraisalEventListener listener) {
	    listenerList.add(LowAppraisalEventListener.class, listener);
	  }
	  public void removeEventListener(LowAppraisalEventListener listener) {
	    listenerList.remove(LowAppraisalEventListener.class, listener);
	  }
	  void fireEvent(LowAppraisalEvent event) {
	    Object[] listeners = listenerList.getListenerList();
	    for (int i = 0; i < listeners.length; i = i+2) {
	      if (listeners[i] == LowAppraisalEventListener.class) {
	        ((LowAppraisalEventListener) listeners[i+1]).lessRatingEventRecieved(event);
	      }
	    }
	  }	  
}
