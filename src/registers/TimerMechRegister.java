package registers;

public class TimerMechRegister {
	private int T;
	public TimerMechRegister(int T) {
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
		if (this.T != 0) {
			this.T = this.T - 1;
		} else {
			update();
		}
	}
	/**
	 * Atstotoma taimerio skaitiklio registro reikðmë
	 */
	public void update() {
		this.T = 10;
	}
}
