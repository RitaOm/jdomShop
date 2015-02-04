package com.epam.testapp.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

/**
 * XmlDocumentOperations --- public final class with private constructor and static
 * methods for making making different operations with JDOM Document
 * 
 * @author Marharyta_Amelyanchuk
 */
public final class XmlDocumentOperations {

	private static final Lock readLock = new ReentrantReadWriteLock().readLock();

	private XmlDocumentOperations() {
	}

	/**
	 * Public static method to build JDOM Document by parsing XML with SAXBuilder. Uses ReadWriteLock to resolve
	 * the problem of concurrent access to XML
	 * 
	 * @param String
	 *            relativePath - relative path of xml file
	 * @return Document	
	 * @exception JDOMException, IOException
	 */
	public static Document buildDocument(String relativePath)
			throws JDOMException, IOException {
		SAXBuilder builder = new SAXBuilder();
		Document doc = null;
		String path = XmlDocumentOperations.class.getResource(relativePath)
				.getPath();
		readLock.lock();
		try {
			doc = builder.build(new File(path));
		} finally {
			readLock.unlock();
		}
		return doc;
	}

	/**
	 * Public static method to return the attribute 'name' of category element 
	 * 
	 * @param int index - category index in Document
	 * @param Document document
	 * @return String - category name
	 * @exception NoSuchElementException
	 */
	public static String getCategoryName(int index, Document document)
			throws NoSuchElementException {
		try {
			return document.getRootElement().getChildren().get(index)
					.getAttributes().get(0).getValue();
		} catch (NullPointerException e) {
			throw new NoSuchElementException("Category number " + index
					+ " doesn't exists");
		}
	}


	/**
	 * Public static method to return the attribute 'name' of subcategory element 
	 * 
	 * @param int subInd - category index in Document
	 * @param int subcatInd - subcategory index in Document
	 * @param Document document
	 * @return String - subcategory name
	 * @exception NoSuchElementException
	 */
	public static String getSubcategoryName(int subInd, int subcatInd,
			Document document) throws NoSuchElementException {
		try {
			return document.getRootElement().getChildren().get(subInd)
					.getChildren().get(subcatInd).getAttributes().get(0)
					.getValue();
		} catch (NullPointerException e) {
			throw new NoSuchElementException("Subcategory number " + subcatInd
					+ " with category number " + subInd + " doesn't exists");
		}
	}

	/**
	 * Public static method to return subcategory element 
	 * 
	 * @param int subInd - category index in Document
	 * @param int subcatInd - subcategory index in Document
	 * @param Document document
	 * @return Element - subcategory element
	 * @exception NoSuchElementException
	 */
	public static Element getSubcategory(int subInd, int catsubInd,
			Document document) throws NoSuchElementException {
		try {
			return document.getRootElement().getChildren().get(subInd)
					.getChildren().get(catsubInd);
		} catch (NullPointerException e) {
			throw new NoSuchElementException("Subcategory number " + catsubInd
					+ " with category number " + subInd + " doesn't exists");
		}
	}

	/**
	 * Public static method to edit Document instance: add and remove tags "price" 
	 * and "not_in_stock" where it's needed 
	 * 
	 * @param int subInd - category index in Document
	 * @param int subcatInd - subcategory index in Document
	 * @param Map<Integer, String> priceMap 
	 * @param Integer[] idsNotInStock
	 * @param Document document
	 * @return Document - edited Document instance
	 * @exception NoSuchElementException
	 */
	public static Document addRemovePriceAndNotInStockTags(int subInd,
			int catsubInd, Map<Integer, String> priceMap,
			Integer[] idsNotInStock, Document document)
			throws NoSuchElementException {
		List<Element> goodsList;
		goodsList = getSubcategory(subInd, catsubInd, document).getChildren();
		for (Integer index : idsNotInStock) {
			Element good = goodsList.get(index);
			good.removeChild("price");
			good.addContent(new Element("not_in_stock"));
		}
		for (Integer index : priceMap.keySet()) {
			Element good = goodsList.get(index);
			good.removeChild("not_in_stock");
			String price = priceMap.get(index);
			Element priceTag = new Element("price").addContent(price);
			good.addContent(priceTag);
		}
		return document;
	}

	public static Integer[] getNotInStockArray(int subInd, int catsubInd,
			Document document) throws NoSuchElementException {
		List<Element> goodsList;
		try {
			goodsList = getSubcategory(subInd, catsubInd, document)
					.getChildren();
		} catch (NullPointerException e) {
			return new Integer[0];
		}
		ArrayList<Integer> list = new ArrayList<Integer>();
		int i = -1;
		for (Element good : goodsList) {
			i++;
			if (null == good.getChild("price")) {
				list.add(i);
			}
		}
		Integer[] array = new Integer[list.size()];
		list.toArray(array);
		return array;
	}

}
