package temp;

import java.util.Timer;
import java.util.TimerTask;

public class Launcher {
	
	public static final int TEN_MINUTES = 600000;
	
	public static void main(String[] args) {
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			
			@Override
			public void run() {
				Main.main(null);
				
			}
		}, 0, TEN_MINUTES);
	}
}
