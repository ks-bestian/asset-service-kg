package kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.exception;


import java.util.function.Supplier;

public class ResourceNotFoundException extends RuntimeException implements Supplier<ResourceNotFoundException> {

	//long
    public ResourceNotFoundException(String dataSource, Long id) {
        super("cannot found : " + id + " in " + dataSource);
    }
    
    //문자열
    public ResourceNotFoundException(String dataSource, String id) {
    	super("cannot found : " + id + " in " + dataSource);
    }
    @Override
    public ResourceNotFoundException get() {
        return this;
    }
}
