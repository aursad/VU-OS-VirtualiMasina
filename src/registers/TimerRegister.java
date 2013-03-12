package registers;

public class TimerRegister {

	private int T;
	public TimerRegister(int T) {
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
		this.T = this.T - 1;
	}
	/**
	 * Atstotoma taimerio skaitiklio registro reik�m�
	 */
	public void update() {
		this.T = 10;
	}
}
