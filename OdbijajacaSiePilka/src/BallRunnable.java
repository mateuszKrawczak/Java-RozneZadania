import java.awt.Component;

class BallRunnable implements Runnable {
	
	private Ball ball;
	private Component component;
	public static final int STEPS=1500;
	public static final int DELAY=5;
	
	public BallRunnable(Ball ball,Component component) {
	this.ball=ball;
	this.component=component;
	
}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			for(int i=1;;i++) {
				ball.move(component.getBounds());
				component.repaint();
				Thread.sleep(DELAY);
			}
		}catch(InterruptedException e) {
			
		}
	}

}
