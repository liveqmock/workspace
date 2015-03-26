package base;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.LazyDynaMap;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections.TransformerUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang.time.FastDateFormat;

import com.yazuo.erp.base.JsonResult;
import com.yazuo.erp.system.vo.SysUserVO;
import com.yazuo.util.DateUtil;

/**
 * main 方法测试
 * 
 * @author song
 * 
 */
@SuppressWarnings("unused")
public class TestMain {
	public static void main(String[] args) throws Exception {
		// others();
		System.out.println(FastDateFormat.getInstance("yyyy-MM-dd hh-mm-ss").format(1417363200000L));
		System.out.println(FastDateFormat.getInstance("yyyy-MM-dd hh-mm-ss").format(1417776435726L));
		FastDateFormat fdf = FastDateFormat.getInstance("MM");
		
		String test = fdf.format(DateUtils.addMonths(new Date(), -1));
		System.out.println(test);
		//这里使用LazyDynaMap，它是 LazyBean 的一个轻量级实现
		//11.
		//dynaMap();
		// ClosureUtils.forClosure(count, closure)
		// 10. sub array
		// subArray();
		// 9. date test
		// baseDateTest();
		// addDay(date);
		// 8. truncate
		// testTruncate(date);
		// 3. test
		// testCollectionTransformer();
		// 4. test truncate
		// truncate(date);
		// 5. test duration
		// String duration = DurationFormatUtils.formatPeriod(start.getTime(),
		// date.getTime(), "mm:ss");
		// System.out.println(duration);
		// 6. test array copy
		// arrayMerge();
		// 7. test array and map convert.
		// convertMapAndArray();

	}

	private static void dynaMap() {
		LazyDynaMap dynaBean1 = new LazyDynaMap();
		dynaBean1.set("foo", "bar");                  // simple
		dynaBean1.set("customer", "title", "Mr");        // mapped
		dynaBean1.set("address", 0, "address1");   
		System.out.println(dynaBean1.get("address",0));
		Map myMap = dynaBean1.getMap();           // retrieve the Map
		System.out.println(JsonResult.getJsonString(myMap));
	}

	private static void others() {
		Date fromMonth = DateUtil.fromMonth();
		Date toMonth = DateUtil.toMonth();
		System.out.println(fromMonth + "  " + toMonth);
		System.out.println(fromMonth.before(toMonth));
		System.out.println(fromMonth.after(toMonth));

		List<Integer> list = new ArrayList<Integer>();

		System.out.println(JsonResult.getJsonString(list.toArray(new Integer[list.size()])));
		System.out.println(JsonResult.getJsonString(new Integer[0]));
		System.out.println(new Integer[0].length);
		System.out.println(list.size());
	}

	private static void subArray() {
		String description = "aaa";
		description = "aaa@bbb";
		String[] descs = description.split("@");
		System.out.println(ArrayUtils.toString(descs));
		String[] subarray = (String[]) ArrayUtils.subarray(descs, 1, descs.length);
		System.out.println(ArrayUtils.toString(subarray));
	}

	private static void baseDateTest() {
		Date date = new Date();
		FastDateFormat fdf = FastDateFormat.getInstance("MM");
		String customDateTime = fdf.format(new Date());
		System.out.println(customDateTime);

		System.out.println(FastDateFormat.getInstance("yyyy-MM-dd").format(1406822400000L));
	}

	private static void testTruncate(Date date) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(dateFormat.format(DateUtils.truncate(date, Calendar.HOUR_OF_DAY)));
		// 2009-08-04 16:00:00
		System.out.println(dateFormat.format(DateUtils.truncate(date, Calendar.MINUTE)));// 2009-08-04
																							// 16:23:00
		System.out.println(dateFormat.format(DateUtils.truncate(date, Calendar.SECOND))); // 2009-08-04
																							// 16:23:14

		System.out.println(dateFormat.format(DateUtils.truncate(date, Calendar.DAY_OF_MONTH)));
		// 2009-08-04 00:00:00
		System.out.println(dateFormat.format(DateUtils.truncate(date, Calendar.MONTH))); // 2009-08-01
																							// 00:00:00
		System.out.println(dateFormat.format(DateUtils.truncate(date, Calendar.YEAR))); // 2009-01-01
																						// 00:00:00

		System.out.println(DateUtils.setDays(DateUtils.truncate(date, Calendar.MONTH), 1));
		System.out.println(DateUtils.addMonths(DateUtils.setDays(DateUtils.truncate(date, Calendar.MONTH), 1), 1));
	}

	private static void addDay(Date date) {
		Date start = DateUtils.setDays(date, 1);
		Date end = DateUtils.addMonths(DateUtils.setDays(date, 1), 1);

		start = DateUtils.setMinutes(date, 1);

		System.out.println("from : " + start);
		System.out.println("to : " + end);
	}

	private static void convertMapAndArray() {
		final String[][][] buttons = { { { "type", "1" }, { "text", "回访" } }, { { "type", "2" }, { "text", "再次回访" } },
				{ { "type", "3" }, { "text", "放弃" } }, { { "type", "4" }, { "text", "完成" } } };
		Map map = ArrayUtils.toMap(buttons);
		System.out.println("");
		MapUtils.verbosePrint(System.out, buttons, map);
		System.out.println("");
		System.out.println(map);
		System.out.println("");
		System.out.println(JsonResult.getJsonString(map));

		Map colorMap = MapUtils.putAll(new HashMap(), new String[][] { { "RED", "#FF0000" }, { "GREEN", "#00FF00" },
				{ "BLUE", "#0000FF" } });
		// System.out.println(colorMap);
	}

	private static void arrayMerge() {
		final String[][][] buttons = { { { "type", "1" }, { "text", "回访" } }, { { "type", "2" }, { "text", "再次回访" } },
				{ { "type", "3" }, { "text", "放弃" } }, { { "type", "4" }, { "text", "完成" } } };
		Object[] addAll = ArrayUtils.addAll(ArrayUtils.subarray(buttons, 1, 2), ArrayUtils.subarray(buttons, 3, 4));
		System.out.println(ArrayUtils.toString(addAll));
	}

	private static void truncate(Date date) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		System.out.println(dateFormat.format(DateUtils.truncate(date, Calendar.HOUR_OF_DAY)));
		// 2009-08-04 16:00:00
		System.out.println(dateFormat.format(DateUtils.truncate(date, Calendar.MINUTE)));// 2009-08-04
																							// 16:23:00
		System.out.println(dateFormat.format(DateUtils.truncate(date, Calendar.SECOND))); // 2009-08-04
																							// 16:23:14

		System.out.println(dateFormat.format(DateUtils.truncate(date, Calendar.DAY_OF_MONTH)));
		// 2009-08-04 00:00:00
		System.out.println(dateFormat.format(DateUtils.truncate(date, Calendar.MONTH))); // 2009-08-01
																							// 00:00:00
		System.out.println(dateFormat.format(DateUtils.truncate(date, Calendar.YEAR))); // 2009-01-01
	}

	private static void testCollectionTransformer() {
		Map nameToLetter = new HashMap();
		nameToLetter.put("ALPHA", "a");
		nameToLetter.put("BETA", "b");
		nameToLetter.put("GAMMA", "g");
		nameToLetter.put("DELTA", "d");
		String[] values = { "ALPHA", "BETA", "GAMMA", "GAMMA", "DELTA", "EPSILON" };
		List valueList = Arrays.asList(values);
		Collection resultCollection = CollectionUtils.collect(valueList, TransformerUtils.mapTransformer(nameToLetter));

		System.out.println(resultCollection);

	}
}
