package kr.co.bestiansoft.ebillservicekg.common.exceptionAdvice.exception;


public class AlreadyExistsException extends RuntimeException {

    public AlreadyExistsException(String dataSource, Long id) {
        super("already exists " + id + " in " + dataSource);
    }

    public AlreadyExistsException(String dataSource, String id) {
        super("already exists : " + id + " in " + dataSource);
    }

	public AlreadyExistsException(String dataSource, Integer id) {
		super("already exists : " + id + " in " + dataSource);
	}
}
