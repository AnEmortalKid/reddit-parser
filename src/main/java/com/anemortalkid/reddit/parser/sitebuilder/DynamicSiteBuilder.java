package com.anemortalkid.reddit.parser.sitebuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Function;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class DynamicSiteBuilder {

	private static final String queryURL = "https://www.reddit.com/r/DnDBehindTheScreen/search?q=flair%3A%2710K+Event%27&restrict_sr=on&sort=new&t=all";

	private static final EnumMap<DataTypes, List<String>> urlsByType = new EnumMap<>(DataTypes.class);

	public static void main(String[] args) throws IOException {

		for (DataTypes dt : DataTypes.values()) {
			urlsByType.put(dt, new ArrayList<>());
		}

		Document document = Jsoup.connect(queryURL).userAgent("Mozilla").get();
		Elements searchResultHeaders = document.getElementsByClass("search-result-header");

		for (Element element : searchResultHeaders) {
			Element anchor = element.children().last();
			String linkTitle = anchor.text();
			String url = anchor.attr("href");
			System.out.println(linkTitle);
			System.out.println("\t" + url);
			DataTypes dt = DataTypes.getFromTitle(linkTitle);
			System.out.println("DT: " + dt);
			urlsByType.get(dt).add(url);
		}

		EnumMap<DataTypes, Integer> siteCount = new EnumMap<>(DataTypes.class);

		for (Entry<DataTypes, List<String>> entry : urlsByType.entrySet()) {
			DataTypes dataType = entry.getKey();
			List<String> siteURLs = entry.getValue();
			Function<List<String>, ISiteBuilder<?>> factory = dataType.getSiteFactory();
			if (factory != null) {
				ISiteBuilder<?> siteBuilder = factory.apply(siteURLs);
				siteBuilder.buildSite();
				siteCount.put(dataType, siteURLs.size());
			} else {
				System.out.println("Havent done " + dataType);
			}
		}

		for (Entry<DataTypes, Integer> entry : siteCount.entrySet()) {
			System.out.println(entry.getKey() + " used " + entry.getValue());
		}

	}

}
