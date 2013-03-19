package registers;

public class TimerRegister {

	private int T;
	public TimerRegister(int T) {
		this.T = T;
	}
	/**
	 * Taimerio skaitiklis
	 * @return T taimerio reikğmë
	 */
	public int get() {
		return this.T;
	}
	/**
	 * Sumaşinama taimerio registro reikğmë
	 */
	public void set(int T) {
		this.T = T;
	}
}
