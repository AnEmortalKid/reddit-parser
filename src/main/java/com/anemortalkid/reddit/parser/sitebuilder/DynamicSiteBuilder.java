package com.anemortalkid.reddit.parser.sitebuilder;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.anemortalkid.ResourceAssistant;

public class DynamicSiteBuilder {

	private static final int WAIT_BEFORE_NEXT_URL_MS = 3000;

	/**
	 * Since we keep changing the flair, we'll have to try to update the query to tag newer posts.
	 */
	private static final String[] searchQueryURLS = {
			"https://www.reddit.com/r/DnDBehindTheScreen/search?q=flair%3A%2710K%27&restrict_sr=on&sort=new&t=all",
			"https://www.reddit.com/r/DnDBehindTheScreen/search?q=flair%3A%2710K+Event%27&restrict_sr=on&sort=new&t=all",
			"https://www.reddit.com/r/DnDBehindTheScreen/search?q=flair%3A%27Event%27+and+title%3A%2710k%27&restrict_sr=on&sort=new&t=all"

	};
	
	private static final EnumMap<DataTypes, Set<String>> urlsByType = new EnumMap<>(DataTypes.class);

	private static String INFO_TEMPLATE = "<p><b>{0}</b> <a href=\"./{1}/\">{2}</a></p>";

	public static void main(String[] args) throws IOException, InterruptedException {

		if (args != null && args.length > 0) {
			String outputDir = args[0];
			if (!outputDir.endsWith("/")) {
				outputDir = outputDir + "/";
			}

			System.setProperty(SiteBuilder.OUTPUT_DIR_PROPERTY_NAME, System.getProperty("user.dir") + "/" + outputDir);
			System.out.println("Property " + SiteBuilder.OUTPUT_DIR_PROPERTY_NAME + "="
					+ System.getProperty(SiteBuilder.OUTPUT_DIR_PROPERTY_NAME));
		}

		for (DataTypes dt : DataTypes.values()) {
			urlsByType.putIfAbsent(dt, new TreeSet<>());
		}

		for (String searchQuery : searchQueryURLS) {
			extractUrlsFromQuery(searchQuery);
		}

		EnumMap<DataTypes, Integer> siteCount = new EnumMap<>(DataTypes.class);

		List<SiteBuilder<?>> siteBuilders = new ArrayList<>();
		for (Entry<DataTypes, Set<String>> entry : urlsByType.entrySet()) {
			DataTypes dataType = entry.getKey();
			Set<String> siteURLs = entry.getValue();
			List<String> orderedUrls = new ArrayList<>(siteURLs);
			Function<List<String>, SiteBuilder<?>> factory = dataType.getSiteFactory();
			if (factory != null) {
				SiteBuilder<?> siteBuilder = factory.apply(orderedUrls);
				siteBuilders.add(siteBuilder);
				siteBuilder.buildSite();
				siteCount.put(dataType, orderedUrls.size());
			} else {
				System.out.println("Havent done " + dataType);
			}
		}

		for (Entry<DataTypes, Integer> entry : siteCount.entrySet()) {
			System.out.println(entry.getKey() + " used " + entry.getValue());
		}

		writeDnDIndex(siteBuilders);
	}

	private static void extractUrlsFromQuery(String searchQuery) throws IOException, InterruptedException {
		String urlToVisit = searchQuery;
		do {
			Document document = Jsoup.connect(urlToVisit).userAgent("Mozilla").get();
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

			// check if there's a next element
			Elements nextElems = document.getElementsByAttributeValueContaining("rel", "next");
			if (nextElems != null && !nextElems.isEmpty()) {
				Element nextElem = nextElems.first();
				String nextURL = nextElem.attr("href");
				urlToVisit = nextURL;
			} else {
				urlToVisit = null;
			}
			
			System.out.println("Sleeping for" + WAIT_BEFORE_NEXT_URL_MS);
			Thread.sleep(WAIT_BEFORE_NEXT_URL_MS);

		} while (urlToVisit != null);
	}

	private static void writeDnDIndex(List<SiteBuilder<?>> siteBuilders) {
		StringBuilder lastUpdated = new StringBuilder();
		lastUpdated.append("<p>Last updated: " + new Date() + "</p>\n");
		siteBuilders.forEach(sb -> {
			String titleReplaced = sb.getTitle().replace(" ", "").toLowerCase();
			String line = MessageFormat.format(INFO_TEMPLATE, sb.scrubbedCount(), titleReplaced, sb.getTitle());
			lastUpdated.append(line + "\n");
		});

		String dndIndexTemplate = ResourceAssistant.INSTANCE.getDataFromFile(SiteResourcesConstants.DND_INDEX_TEMPLATE);
		String dndIndexContent = MessageFormat.format(dndIndexTemplate, lastUpdated.toString());
		System.out.println("DND Index Content:" + dndIndexContent);

		String indexLocation = SiteBuilder.BASE_RESOURCES + "dnd-index.html";
		ResourceAssistant.INSTANCE.writeToFile(indexLocation, dndIndexContent);
	}

}
