import java.util.concurrent.atomic.AtomicReference;

public class ThreadSafeExitBattleButton {
	private final AtomicReference<Boolean> found = new AtomicReference<Boolean>();
	private final AtomicReference<Exception> ex = new AtomicReference<Exception>();
	
	public ThreadSafeExitBattleButton(){
		this.setFound(false);
		this.setException(null);
	}
	
    public void setFound(boolean x) {
    	found.set(x);
    }

    public boolean getFound() throws Exception {
    	Exception e = ex.get();
    	if(e != null){
    		throw e;
    	}
        return found.get();
    }
	
    public void setException(Exception x) {
    	ex.set(x);
    }
}