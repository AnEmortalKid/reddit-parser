package com.anemortalkid.reddit.parser.sitebuilder.wordcloud;

import java.awt.Color;
import java.awt.Dimension;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import com.anemortalkid.ResourceAssistant;
import com.kennycason.kumo.CollisionMode;
import com.kennycason.kumo.WordCloud;
import com.kennycason.kumo.WordFrequency;
import com.kennycason.kumo.bg.CircleBackground;
import com.kennycason.kumo.font.scale.SqrtFontScalar;
import com.kennycason.kumo.nlp.FrequencyAnalyzer;
import com.kennycason.kumo.palette.ColorPalette;

public class WorldCloudTest {

	public static void main(String[] args) throws IOException, URISyntaxException {
		FrequencyAnalyzer analyzer = new FrequencyAnalyzer();
		analyzer.setWordFrequenciesToReturn(300);
		analyzer.setMinWordLength(4);

		List<String> lines = ResourceAssistant.INSTANCE.getLines("locations.txt");

		List<WordFrequency> wordFrequencies = analyzer.load(lines);

		final Dimension dimension = new Dimension(600, 600);
		final WordCloud wordCloud = new WordCloud(dimension, CollisionMode.PIXEL_PERFECT);
		wordCloud.setPadding(2);
		wordCloud.setBackground(new CircleBackground(300));
		wordCloud.setColorPalette(new ColorPalette(new Color(0x4055F1), new Color(0x408DF1), new Color(0x40AAF1),
				new Color(0x40C5F1), new Color(0x40D3F1), new Color(0xFFFFFF)));
		wordCloud.setFontScalar(new SqrtFontScalar(10, 40));
		wordCloud.build(wordFrequencies);
		wordCloud.writeToFile("src/main/resources/wordcloud/wordcloud_square.png");
	}
}
