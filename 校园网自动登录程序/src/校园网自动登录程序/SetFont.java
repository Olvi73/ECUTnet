package 校园网自动登录程序;

import java.awt.Font;
import java.util.Enumeration;

import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

public class SetFont {
	public static void InitGlobalFont(Font font) {
		  FontUIResource fontRes = new FontUIResource(font);
		  for (Enumeration<Object> keys = UIManager.getDefaults().keys();
		  keys.hasMoreElements(); ) {
			  Object key = keys.nextElement();
			  Object value = UIManager.get(key);
			  if (value instanceof FontUIResource) 
			  {
				  UIManager.put(key, fontRes);
			  }
		
		  	}
		}
}


