package exception;

/**
 * 表示用户没有登录就访问保护资源时抛出的异常
 * @author 孔佳齐丶
 *
 */
public class AccessForbiddentException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AccessForbiddentException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AccessForbiddentException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public AccessForbiddentException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public AccessForbiddentException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public AccessForbiddentException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
	
	

}
