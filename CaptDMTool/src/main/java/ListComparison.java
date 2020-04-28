import java.util.ArrayList;
import java.util.List;

import com.capt.dm.model.Metadata;

public class ListComparison {
	public static void main(String args[]) {
		List<MetaDataObj1> list1 = new ArrayList<MetaDataObj1>();
		List<MetaDataObj1> list2 = new ArrayList<MetaDataObj1>();
		
		MetaDataObj1 obj1 = new MetaDataObj1();
		obj1.setFieldName("1");
		obj1.setFieldValue("1");
		
		list1.add(obj1);
		
		MetaDataObj1 obj2 = new MetaDataObj1();
		obj2.setFieldName("1");
		obj2.setFieldValue("1");
		
		System.out.println("Is contain???"+list1.contains(obj2));
		System.out.println("Is obj equal???"+list1.contains(obj2));
	}

}
