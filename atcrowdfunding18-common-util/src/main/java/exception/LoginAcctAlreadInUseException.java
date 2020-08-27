package exception;

/**
 * 保存或更新Admin时如果检测到登录账号重复抛出这个异常
 * @author 孔佳齐丶
 *
 */
public class LoginAcctAlreadInUseException  extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LoginAcctAlreadInUseException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LoginAcctAlreadInUseException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public LoginAcctAlreadInUseException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public LoginAcctAlreadInUseException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public LoginAcctAlreadInUseException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
	
	

}
