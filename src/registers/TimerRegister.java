package registers;

public class TimerRegister {

	private int T;
	TimerRegister() {
		this.T = 0;
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
