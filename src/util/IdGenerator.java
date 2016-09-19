package util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class IdGenerator {
	
	public String generateId(){
		UUID uuid = UUID.randomUUID();
		Date today = new Date();
		SimpleDateFormat sf1 = new SimpleDateFormat("yyMMddHHmm");
		String id = uuid.toString().substring(0,10)+sf1.format(today);
		return id;
	}
	
}
