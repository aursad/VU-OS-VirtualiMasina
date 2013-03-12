package registers;

public class TimerRegister {

	private int T;
	public TimerRegister(int T) {
		this.T = T;
	}
	/**
	 * Taimerio skaitiklis
	 * @return T taimerio reikðmë
	 */
	public int get() {
		return this.T;
	}
	/**
	 * Sumaþinama taimerio registro reikðmë
	 */
	public void set() {
		this.T = this.T - 1;
	}
	/**
	 * Atstotoma taimerio skaitiklio registro reikðmë
	 */
	public void update() {
		this.T = 10;
	}
}
