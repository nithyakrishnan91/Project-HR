import java.util.EventListener;

public interface LowAppraisalEventListener extends EventListener {
	
	public void lessRatingEventRecieved(LowAppraisalEvent event);

}
