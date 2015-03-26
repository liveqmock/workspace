package cn.org.rapid_framework.generator.ext;

import java.io.File;
import java.util.Arrays;
import java.util.Scanner;

import cn.org.rapid_framework.generator.GeneratorFacade;
import cn.org.rapid_framework.generator.util.ArrayHelper;
import cn.org.rapid_framework.generator.util.StringHelper;
import cn.org.rapid_framework.generator.util.SystemHelper;
/**
 * 命令行工具类,可以直接运行
 * 
 * @author badqiuj
 */
public class CommandLine {
	
	public static void main(String[] args) throws Exception {
		deleteAll(new File("D:\\workspaces\\Git\\rapid-generator\\rapid-generator\\bin\\generator-output"));
		freemarker.log.Logger.selectLoggerLibrary(freemarker.log.Logger.LIBRARY_NONE);//disable freemarker logging
//		startProcess();
		
		//gen mkt_brand_interview,mkt_shop_survey ibatis3
		GeneratorFacade facade = new GeneratorFacade();
		facade.g.setIncludes("ibatis3/**");
		//------------------------------------------------需要更改的参数在这里
//		String[] strs = new String[]{"bes_call_record","bes_former_user_merchant","bes_monthly_report","bes_requirement","bes_talking_skills"};
		String[] strs = new String[]{"mkt_business_type","mkt_former_sales_merchant"};
		for (String string : strs) {
			facade.generateByTable(string,getTemplateRootDir());
		}
	}

	private static void startProcess() throws Exception {
		Scanner sc = new Scanner(System.in);
		System.out.println("templateRootDir:"+new File(getTemplateRootDir()).getAbsolutePath());
		printUsages();
		while(sc.hasNextLine()) {
			try {
				processLine(sc);
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				Thread.sleep(700);
				printUsages();
			}
		}
	}

	private static void processLine(Scanner sc) throws Exception {
		GeneratorFacade facade = new GeneratorFacade();
		String cmd = sc.next();
		if("gen".equals(cmd)) {
			String[] args = nextArguments(sc);
			if(args.length == 0) return;
			facade.g.setIncludes(getIncludes(args,1));
			//增加逗号分隔，多个table生成
			String[] tables = new String[1];
			if(args[0].contains(",")){
				tables = new String[args[0].split(",").length];
				tables = args[0].split(",");
			}else{
				tables[0] = args[0];
			}
			for (String table : tables) {
				facade.generateByTable(table,getTemplateRootDir());
			}
//			facade.generateByTable(args[0],getTemplateRootDir());
			if(SystemHelper.isWindowsOS) {
//			    Runtime.getRuntime().exec("cmd.exe /c start "+GeneratorProperties.getRequiredProperty("outRoot").replace('/', '\\'));
			}
		}else if("del".equals(cmd)) {
			String[] args = nextArguments(sc);
			if(args.length == 0) return;
			facade.g.setIncludes(getIncludes(args,1));
			//增加逗号分隔，多个table删除
			String[] tables = new String[1];
			if(args[0].contains(",")){
				tables = new String[args[0].split(",").length];
				tables = args[0].split(",");
			}else{
				tables[0] = args[0];
			}
			for (String table : tables) {
				facade.deleteByTable(table, getTemplateRootDir());
			}
//			facade.deleteByTable(args[0], getTemplateRootDir());
		}else if("quit".equals(cmd)) {
		    System.exit(0);
		}else {
			System.err.println(" [ERROR] unknow command:"+cmd);
		}
	}

	private static String getIncludes(String[] args, int i) {
		String includes = ArrayHelper.getValue(args, i);
		if(includes == null) return null;
		return includes.indexOf("*") >= 0 || includes.indexOf(",") >= 0 ? includes : includes+"/**";
	}
	
	private static String getTemplateRootDir() {
		return System.getProperty("templateRootDir", "template");
	}

	private static void printUsages() {
		System.out.println("Usage:");
		System.out.println("\tgen table_name [include_path]: generate files by table_name");
		System.out.println("\tdel table_name [include_path]: delete files by table_name");
		System.out.println("\tgen * [include_path]: search database all tables and generate files");
		System.out.println("\tdel * [include_path]: search database all tables and delete files");
		System.out.println("\tquit : quit");
		System.out.println("\t[include_path] subdir of templateRootDir,example: 1. dao  2. dao/**,service/**");
		System.out.print("please input command:");
	}
	
	private static String[] nextArguments(Scanner sc) {
		return StringHelper.tokenizeToStringArray(sc.nextLine()," ");
	}

	public static void deleteAll(File file) {
		if (file.isFile() || (file.list()!=null&&file.list().length == 0)) {
			file.delete();
		} else {
			File[] files = file.listFiles();
			if(files!=null){
				for (File f : files) {
					deleteAll(f);// 递归删除每一个文件
					f.delete();// 删除该文件夹
				}
			}
		}
	}
}
