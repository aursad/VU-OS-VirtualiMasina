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
	public void set(int T) {
		this.T = T;
	}
}
