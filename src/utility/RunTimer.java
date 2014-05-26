package utility;

/**
 * RunTimer marks the current time upon construction
 * Use the lap method to pause the timer and return
 * the difference between the start and current time
 * @author Dean
 *
 */
public class RunTimer {
	long start;
	public RunTimer() {
		this.start = System.currentTimeMillis();
	}

	public String lap() {
		long end = System.currentTimeMillis();
		return Long.toString(end-start) + " milliseconds";
	}
}