package jp.gr.java_conf.hakusai.exeption;

public class AppException extends Exception {

	/**
	 *
	 */
	private static final long serialVersionUID = -3010739363704087821L;

	public AppException(String str) {
		super(str);
	}

	public AppException(Exception e) {
		super(e);
	}
}
