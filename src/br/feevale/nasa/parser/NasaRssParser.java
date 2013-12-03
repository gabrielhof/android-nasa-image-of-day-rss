package br.feevale.nasa.parser;

import br.feevale.nasa.model.NasaRss;

public interface NasaRssParser {

	public void parse();
	public NasaRss getRss();
	
}
