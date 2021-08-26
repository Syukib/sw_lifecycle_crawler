package com.crawler.test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class htmlunit_test {
	//for github actions testing
	private static Path lifecycle_result_data = Paths.get("lifecycle_result_data.csv");
	
	
	public static void main(String[] args) throws IOException {
      		String url = "https://www.php.net/eol.php";
		String version_name = "";
		String version_rule = "//table/tbody/tr/td[1]";
		String eol = "";
		String date_rule = "//table/tbody/tr/td[2]";
		
		
		HtmlPage EntrancePage = CrawlerUtils.getPage(url);
		List <HtmlElement> infoListEle_v = (List <HtmlElement>)  EntrancePage.getByXPath(version_rule);
		List <HtmlElement> infoListEle_e = (List <HtmlElement>)  EntrancePage.getByXPath(date_rule);
		
		for (int i = 0; i < infoListEle_v.size(); i++) {
			version_name = infoListEle_v.get(i).getTextContent();
			eol = infoListEle_e.get(i).getTextContent().trim();;
			System.out.println(version_name + " | " +  eol);
	        if (!Files.exists(lifecycle_result_data)) {
	            Files.createFile(lifecycle_result_data);
	        }
	        Files.write(lifecycle_result_data, version_name.getBytes(), StandardOpenOption.APPEND);
	        Files.write(lifecycle_result_data, " , ".getBytes(), StandardOpenOption.APPEND);
	        Files.write(lifecycle_result_data, eol.getBytes(), StandardOpenOption.APPEND);
	        Files.write(lifecycle_result_data, "\n".getBytes(), StandardOpenOption.APPEND);
		}		
	}
	

}