package com.dozengame.xml;

import java.io.InputStream;
import java.util.HashMap;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.content.SharedPreferences;

import com.dozengame.net.pojo.Gift;

public class SAXParseXmlService {

	/**
	 * 解析流得到礼物集合数据
	 * @param inStream
	 * @return
	 * @throws Exception
	 */
	public static HashMap<Integer, Gift> getGiftService(InputStream inStream)
			throws Exception {
		
		
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser = factory.newSAXParser();
		GiftDefaultHandler handler = new GiftDefaultHandler();
		parser.parse(inStream, handler);
		inStream.close();
		return handler.getPersons();
	}

	private static final class GiftDefaultHandler extends DefaultHandler {
		HashMap<Integer, Gift> gifts = null;
		private Gift currentGift;

		public HashMap<Integer, Gift> getPersons() {
			return gifts;
		}

		public void setPersons(HashMap<Integer, Gift> gifts) {
			this.gifts = gifts;
		}

		private String tagName = null;

//		@Override
//		public void characters(char[] ch, int start, int length)
//				throws SAXException {
//			if (tagName != null) {
//				String data = new String(ch, start, length);
//				if ("name".equals(data)) {
//					currentGift.setName(data);
//				} else if ("tab".equals(data)) {
//					currentGift.setTab(Integer.parseInt(data));
//				} else if ("id".equals(data)) {
//					currentGift.setId(Integer.parseInt(data));
//				} else if ("img".equals(data)) {
//					currentGift.setImgPath(data);
//				}
//
//			}
//		}

		@Override
		public void endElement(String uri, String localName, String qName)
				throws SAXException {
			if ("item".equals(localName)) {
				if(currentGift !=null){
				  gifts.put(currentGift.getId(), currentGift);
				  currentGift= null;
				}
			}
			tagName = null;
		}

		@Override
		public void startDocument() throws SAXException {
			gifts = new HashMap<Integer, Gift>();
		}
		@Override
		public void startElement(String uri, String localName, String name,
				org.xml.sax.Attributes attr) throws SAXException {
			if ("item".equals(localName)) {
				currentGift = new Gift();
				currentGift.setId(Integer.parseInt(attr.getValue("id")));
				currentGift.setName(attr.getValue("name"));
				currentGift.setImgPath(attr.getValue("img"));
				currentGift.setTab(Integer.parseInt(attr.getValue("tab")));
			}
			tagName = localName;
		}

	}

}
