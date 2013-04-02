package registers;

public class TimerMechRegister {
	private int TI;
	public TimerMechRegister(int TI) {
		this.TI = TI;
	}
	/**
	 * Taimerio skaitiklis
	 * @return T taimerio reikðmë
	 */
	public int get() {
		return this.TI;
	}
	/**
	 * Sumaþinama taimerio registro reikðmë
	 */
	public void set() {
		if (this.TI != 1) {
			this.TI = this.TI - 1;
		} else {
			this.TI = this.TI - 1;
			RM.RM.T.set(1);
			RM.RM.MODE.set(1);
			update();
		}
	}
	/**
	 * Atstotoma taimerio skaitiklio registro reikðmë
	 */
	public void update() {
		this.TI = 10;
	}
}
