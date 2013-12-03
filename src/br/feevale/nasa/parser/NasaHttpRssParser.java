package br.feevale.nasa.parser;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import br.feevale.nasa.builder.NasaRssBuilder;
import br.feevale.nasa.model.NasaRss;

public class NasaHttpRssParser implements NasaRssParser {

	private NasaRss rss;
	
	public String nasaUrl;
	
	public NasaHttpRssParser(String nasaUrl) {
		this.nasaUrl = nasaUrl;
	}
	
	@Override
	public void parse() {
		try {
			Element lastItem = findLastItem();
			
			Element titleElement = (Element) lastItem.getElementsByTagName("title").item(0);
			Element descriptionElement = (Element) lastItem.getElementsByTagName("description").item(0);
			Element imageElement = (Element) lastItem.getElementsByTagName("enclosure").item(0);
			Element dateElement = (Element) lastItem.getElementsByTagName("pubDate").item(0);
			
			NasaRssBuilder builder = new NasaRssBuilder();
			builder.title(titleElement.getTextContent());
			builder.description(descriptionElement.getTextContent());
			builder.image(imageElement.getAttribute("url"));
			builder.date(dateElement.getTextContent(), "EEE, dd MMM yyyy HH:mm:ss zzz");
			
			this.rss = builder.build();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Element findLastItem() throws Exception {
		DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
		
		Document document = documentBuilder.parse(nasaUrl);
		NodeList items = document.getElementsByTagName("item");
		
		return (Element) items.item(0);
	}
	
	@Override
	public NasaRss getRss() {
		if (rss == null) {
			throw new NullPointerException("É necessário chamar o método parse() antes.");
		}
		
		return rss;
	}

}
