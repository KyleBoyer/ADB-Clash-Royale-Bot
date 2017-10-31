import java.util.concurrent.atomic.AtomicReference;

public class ThreadSafeExitBattleButton {
	private final AtomicReference<Boolean> found = new AtomicReference<Boolean>();
	
	public ThreadSafeExitBattleButton(){
		this.setFound(false);
	}
	
    public void setFound(boolean x) {
    	found.set(x);
    }

    public boolean getFound() {
        return found.get();
    }
}