package com.alipay;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

 

public class SAXParseXmlProductService {

	 
	public static List<CzProductInfo> getProductsService(InputStream inStream)
			throws Exception {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser = factory.newSAXParser();
		GiftDefaultHandler handler = new GiftDefaultHandler();
		parser.parse(inStream, handler);
		inStream.close();
		return handler.getProducts();
	}

	private static final class GiftDefaultHandler extends DefaultHandler {
		 List<CzProductInfo> products = null;
		private CzProductInfo currentPorduct;

		public  List<CzProductInfo> getProducts() {
			return products;
		}

		public void setProducts( List<CzProductInfo> gifts) {
			this.products = gifts;
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
				if(currentPorduct !=null){
					products.add(currentPorduct);
					currentPorduct= null;
				}
			}
			tagName = null;
		}

		@Override
		public void startDocument() throws SAXException {
		    products = new ArrayList<CzProductInfo>();
		}
		@Override
		public void startElement(String uri, String localName, String name,
				org.xml.sax.Attributes attr) throws SAXException {
			if ("item".equals(localName)) {
				currentPorduct = new CzProductInfo();
				currentPorduct.setProductId(Integer.parseInt(attr.getValue("id")));
				currentPorduct.setProductName(attr.getValue("name"));
				currentPorduct.setProductImgPath(attr.getValue("img"));
				currentPorduct.setProductPrice(Integer.parseInt(attr.getValue("price")));
			}
			tagName = localName;
		}

	}

}
