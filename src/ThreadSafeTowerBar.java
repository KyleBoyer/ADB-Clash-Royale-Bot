import java.util.concurrent.atomic.AtomicReference;

public class ThreadSafeTowerBar {
	private final AtomicReference<Boolean> leftTowerHalf = new AtomicReference<Boolean>();
	private final AtomicReference<Boolean> rightTowerHalf = new AtomicReference<Boolean>();
	private final AtomicReference<Boolean> leftTowerDone = new AtomicReference<Boolean>();
	private final AtomicReference<Boolean> rightTowerDone = new AtomicReference<Boolean>();
	
	public ThreadSafeTowerBar(){
		this.setLeftTowerDone(false);
		this.setRightTowerDone(false);
		this.setLeftTowerHalf(false);
		this.setRightTowerHalf(false);
	}
	
    public void setLeftTowerHalf(boolean x) {
    	leftTowerHalf.set(x);
    }
    public void setRightTowerHalf(boolean x) {
    	rightTowerHalf.set(x);
    }

    public boolean getLeftTowerHalf() {
        return leftTowerHalf.get();
    }
    
    public boolean getRightTowerHalf() {
        return rightTowerHalf.get();
    }
    
    public void setLeftTowerDone(boolean x) {
    	leftTowerDone.set(x);
    }
    public void setRightTowerDone(boolean x) {
    	rightTowerDone.set(x);
    }

    public boolean getLeftTowerDone() {
        return leftTowerDone.get();
    }
    
    public boolean getRightTowerDone() {
        return rightTowerDone.get();
    }
}