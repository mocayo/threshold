package Action;

import java.io.IOException;

import org.dom4j.DocumentException;

import util.Action;

public class Test extends Action{
	public void test() throws IOException{
        String string=request.getParameter("string");
        int result=0;
        try {
			result=ModifyFile_XML(string);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
