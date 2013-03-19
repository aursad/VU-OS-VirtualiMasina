package registers;

public class TimerMechRegister {
	private int TI;
	public TimerMechRegister(int TI) {
		this.TI = TI;
	}
	/**
	 * Taimerio skaitiklis
	 * @return T taimerio reik�m�
	 */
	public int get() {
		return this.TI;
	}
	/**
	 * Suma�inama taimerio registro reik�m�
	 */
	public void set() {
		if (this.TI != 0) {
			this.TI = this.TI - 1;
		} else {
			RM.RM.T.set(1);
			update();
		}
	}
	/**
	 * Atstotoma taimerio skaitiklio registro reik�m�
	 */
	public void update() {
		this.TI = 10;
	}
}
