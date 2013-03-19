package registers;

public class TimerMechRegister {
	private int T;
	public TimerMechRegister(int T) {
		this.T = T;
	}
	/**
	 * Taimerio skaitiklis
	 * @return T taimerio reik�m�
	 */
	public int get() {
		return this.T;
	}
	/**
	 * Suma�inama taimerio registro reik�m�
	 */
	public void set() {
		if (this.T != 0) {
			this.T = this.T - 1;
		} else {
			update();
		}
	}
	/**
	 * Atstotoma taimerio skaitiklio registro reik�m�
	 */
	public void update() {
		this.T = 10;
	}
}
