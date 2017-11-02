import java.util.concurrent.atomic.AtomicReference;

public class ThreadSafeTowerBar {
	private final AtomicReference<Boolean> leftTowerHalf = new AtomicReference<Boolean>();
	private final AtomicReference<Boolean> rightTowerHalf = new AtomicReference<Boolean>();
	private final AtomicReference<Boolean> leftTowerDone = new AtomicReference<Boolean>();
	private final AtomicReference<Boolean> rightTowerDone = new AtomicReference<Boolean>();
	private final AtomicReference<Exception> ex = new AtomicReference<Exception>();
	
	public ThreadSafeTowerBar(){
		this.setLeftTowerDone(false);
		this.setRightTowerDone(false);
		this.setLeftTowerHalf(false);
		this.setRightTowerHalf(false);
		this.setException(null);
	}
	
    public void setLeftTowerHalf(boolean x) {
    	leftTowerHalf.set(x);
    }
    public void setRightTowerHalf(boolean x) {
    	rightTowerHalf.set(x);
    }

    public boolean getLeftTowerHalf() throws Exception {
    	Exception e = ex.get();
    	if(e != null){
    		throw e;
    	}
        return leftTowerHalf.get();
    }
    
    public boolean getRightTowerHalf() throws Exception {
    	Exception e = ex.get();
    	if(e != null){
    		throw e;
    	}
        return rightTowerHalf.get();
    }
    
    public void setLeftTowerDone(boolean x) {
    	leftTowerDone.set(x);
    }
    public void setRightTowerDone(boolean x) {
    	rightTowerDone.set(x);
    }

    public boolean getLeftTowerDone() throws Exception {
    	Exception e = ex.get();
    	if(e != null){
    		throw e;
    	}
        return leftTowerDone.get();
    }
    
    public boolean getRightTowerDone() throws Exception {
    	Exception e = ex.get();
    	if(e!= null){
    		throw e;
    	}
        return rightTowerDone.get();
    }
	
    public void setException(Exception x) {
    	ex.set(x);
    }
}