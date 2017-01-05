package core.site;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

import core.forecast.factory.ForecastAbstractFactory;
import core.forecast.factory.MeteoAMForecastFactory;

public class MeteoAM extends Site {

	public MeteoAM(String url) {
		super(url);
	}

	@Override
	public String getLocationUrl(String location, int day) {
		String locUrl = getUrl();
		try {
			locUrl += getCodeLocation(location);
		} catch (IOException e) {
			System.out.println("FNF");
		}
		return locUrl;
	}

	@Override
	public ForecastAbstractFactory getForecastConstructor() {
		return new MeteoAMForecastFactory();
	}

	@Override
	public String cleanSiteContent(String siteContent) {
		HTMLCleaner cleaner = new HTMLCleaner(); 
		String cleanContent = cleaner.cutTag(siteContent, "([^\"])(<script.)", "</script>"); //toglie i tag script e il loro contenuto
		cleanContent = cleaner.removeBlocks(cleanContent, "<!-- /.block -->");
		return siteContent;
	}

	private String getCodeLocation(String location) throws IOException {
		URL url = getClass().getResource("listaLocalita.txt");
		BufferedReader locList = new BufferedReader(new FileReader(url.getPath()));
		String s;
		String codeLoc;
		while (true) {
			s = locList.readLine();
			if (s.contains(location.toUpperCase())) {
				codeLoc = s.substring(0, s.indexOf('|'));
				break;
			}
		}
		locList.close();
		return codeLoc;
	}

}
