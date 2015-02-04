package com.epam.testapp.presentation.action;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.TransformerException;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;
import org.apache.struts.actions.MappingDispatchAction;
import org.jdom2.Document;
import org.jdom2.JDOMException;

import com.epam.testapp.bean.Good;
import com.epam.testapp.constants.ActionConstants;
import com.epam.testapp.presentation.form.ProductForm;

import static com.epam.testapp.constants.PathHolder.*;

import com.epam.testapp.util.*;
import com.epam.testapp.validator.GoodValidationErrors;
import com.epam.testapp.validator.GoodValidator;

/**
 * ProductAction --- a class which accepts incoming HTTP requests and call
 * appropriate method to process these requests
 * 
 * @author Marharyta_Amelyanchuk
 */
public final class ProductAction extends MappingDispatchAction {

	private static final Logger LOG = Logger.getLogger(ProductAction.class);
	private static final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
	private final String PRODUCTS_XML_ABS_PATH = getClass().getResource(
			PRODUCTS_XML_REL_PATH).getPath();

	/**
	 * Method is called when there is needed to go on categories pages. Sets to
	 * form Document
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param form
	 *            The optional ActionForm bean for this request (if any)
	 * @param request
	 *            The HTTP request we are processing
	 * @param response
	 *            The HTTP response we are creating
	 * @return The ActionMapping parameter's value: failure if JDOMException |
	 *         IOException occurs, success if there is no exceptions
	 * @exception Exception
	 *                if any exception occurs
	 */
	public ActionForward categoryList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			setDocumentToProductForm(form);
		} catch (JDOMException | IOException e) {
			return errorHandler(mapping, e.getMessage());
		}
		return mapping.findForward(ActionConstants.SUCCESS.toString());
	}

	/**
	 * Method is called when there is needed to go on subcategories pages. Sets
	 * to form Document
	 * 
	 * @return The ActionMapping parameter's value: failure if JDOMException |
	 *         IOException occurs, success if there is no exceptions
	 * @exception Exception
	 *                if any exception occurs
	 */
	public ActionForward subcategoryList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ProductForm productForm;
		try {
			productForm = setDocumentToProductForm(form);
		} catch (JDOMException | IOException e) {
			return errorHandler(mapping, e.getMessage());
		}
		if (productForm.getCatIndex() == -1) {
			int index = Integer.parseInt(request
					.getParameter(ActionConstants.INDEX.toString()));
			productForm.setCatIndex(index);
		}
		return mapping.findForward(ActionConstants.SUCCESS.toString());
	}

	/**
	 * Method is called when there is needed to go on goods page. Sets to form
	 * Document, Integer array of good ids that are not in stock and empty price
	 * map
	 * 
	 * @return The ActionMapping parameter's value: failure if JDOMException |
	 *         IOException occurs, success if there is no exceptions
	 * @exception Exception
	 *                if any exception occurs
	 */
	public ActionForward goodsList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ProductForm productForm = (ProductForm) form;
		try {
			productForm.setDocument(getDocument());
		} catch (JDOMException | IOException e) {
			return errorHandler(mapping, e.getMessage());
		}
		if (productForm.getSubcatIndex() == -1) {
			int index = Integer.parseInt(request
					.getParameter(ActionConstants.INDEX.toString()));
			productForm.setSubcatIndex(index);
		}
		setFormPropertiesForGoingToGoodsPage(productForm);
		return mapping.findForward(ActionConstants.SUCCESS.toString());
	}

	/**
	 * Method is called when there is needed to go on add page. Sets to form
	 * category and subcategory names, new Good and GoodValidationErrors
	 * instances
	 * 
	 * @exception Exception
	 *                if any exception occurs
	 */
	public ActionForward goToAddPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ProductForm productForm = (ProductForm) form;
		String category = getCategory(productForm);
		String subcategory = getSubcategory(productForm);
		productForm.setCategory(category);
		productForm.setSubcategory(subcategory);
		productForm.setGood(new Good());
		productForm.setErrors(new GoodValidationErrors());
		return mapping.findForward(ActionConstants.SUCCESS.toString());
	}

	/**
	 * Updates goods information, write info to XML file instances
	 * 
	 * @return The ActionMapping parameter 'failure' if IOException occurs or
	 *         new ActionRedirect to goods page if there is no exceptions
	 * @exception Exception
	 *                if any exception occurs
	 */
	public ActionForward updateGoods(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ProductForm productForm = (ProductForm) form;
		Document document = XmlDocumentOperations
				.addRemovePriceAndNotInStockTags(productForm.getCatIndex(),
						productForm.getSubcatIndex(),
						productForm.getPriceMap(),
						productForm.getIdsNotInStock(),
						productForm.getDocument());
		productForm.setDocument(document);
		File xmlFile = new File(PRODUCTS_XML_ABS_PATH);
		Lock writeLock = readWriteLock.writeLock();
		writeLock.lock();
		try {
			XslXmlManager.writeDocumentToXMLFile(document, xmlFile);
		} catch (IOException e) {
			return errorHandler(mapping, e.getMessage());
		} finally {
			writeLock.unlock();
		}
		return new ActionRedirect("/Subcategory.do");
	}

	/**
	 * Saves new good to XML file: makes xsl transformation. Forwards to goods
	 * page if good is valid and to add page with errors otherwise
	 * 
	 * @return The ActionMapping parameter 'failure' if JDOMException |
	 *         IOException occurs or new ActionRedirect to goods page if there
	 *         is no exceptions
	 * @exception Exception
	 *                if any exception occurs
	 */
	public ActionForward saveGood(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ProductForm productForm = (ProductForm) form;
		Good good = productForm.getGood();
		GoodValidator validator = new GoodValidator();
		String category = getCategory(productForm);
		String subcategory = getSubcategory(productForm);
		File xmlFile = new File(PRODUCTS_XML_ABS_PATH);
		Map<String, Object> paramsMap = XslParamsMapCreator.create(category,
				subcategory, good, validator, null);
		Writer resultWriter = new StringWriter();
		Lock readLock = readWriteLock.readLock();
		readLock.lock();
		try {
			XslXmlManager.makeXSLTransform(SAVE_XSL_REL_PATH, xmlFile,
					resultWriter, paramsMap);
		} catch (TransformerException e) {
			return errorHandler(mapping, e.getMessage());
		} finally {
			readLock.unlock();
		}
		if (validator.isGoodValid()) {
			Lock writeLock = readWriteLock.writeLock();
			writeLock.lock();
			try {
				XslXmlManager.writeXSLTResultToXMLFile(resultWriter, xmlFile);
				productForm.setDocument(getDocument());
			} catch (JDOMException | IOException e) {
				return errorHandler(mapping, e.getMessage());
			} finally {
				writeLock.unlock();
			}
			return new ActionRedirect("/Subcategory.do");
		} else {
			productForm.setErrors(validator.getErrors());
			return mapping.findForward(ActionConstants.NOT_VALID.toString());
		}
	}

	/**
	 * Private method to set to form Document parsed from xml file
	 * 
	 * @param ActionForm
	 *            form - form to set Document
	 * @return ProductForm
	 * @exception JDOMException
	 *                , IOException
	 */
	private ProductForm setDocumentToProductForm(ActionForm form)
			throws JDOMException, IOException {
		ProductForm productForm = (ProductForm) form;
		if (productForm.getDocument() == null) {
			productForm.setDocument(getDocument());
		}
		return productForm;
	}

	/**
	 * Private method to parse Document from xml file
	 * 
	 * @return Document
	 * @exception JDOMException
	 *                , IOException
	 */
	private Document getDocument() throws JDOMException, IOException {
		Document document = XmlDocumentOperations
				.buildDocument(PRODUCTS_XML_REL_PATH);
		return document;
	}

	/**
	 * Private method to set to form Integer array of good ids that are not in
	 * stock and empty price map
	 * 
	 * @param ProductForm
	 *            productForm
	 * @exception NoSuchElementException
	 */
	private void setFormPropertiesForGoingToGoodsPage(ProductForm productForm)
			throws NoSuchElementException {
		Integer[] idsNotInStock = XmlDocumentOperations.getNotInStockArray(
				productForm.getCatIndex(), productForm.getSubcatIndex(),
				productForm.getDocument());
		productForm.setIdsNotInStock(idsNotInStock);
		productForm.setPriceMap(new HashMap<Integer, String>());
	}

	/**
	 * Private method to learn category name by its index which is stored in
	 * productForm
	 * 
	 * @param ProductForm
	 *            productForm
	 * @return String
	 * @exception NoSuchElementException
	 */
	private String getCategory(ProductForm productForm)
			throws NoSuchElementException {
		return XmlDocumentOperations.getCategoryName(productForm.getCatIndex(),
				productForm.getDocument());
	}

	/**
	 * Private method to learn subcategory name by its index which is stored in
	 * productForm
	 * 
	 * @param ProductForm
	 *            productForm
	 * @return String
	 * @exception NoSuchElementException
	 */
	private String getSubcategory(ProductForm productForm)
			throws NoSuchElementException {
		return XmlDocumentOperations.getSubcategoryName(
				productForm.getCatIndex(), productForm.getSubcatIndex(),
				productForm.getDocument());
	}

	/**
	 * Private method to handle error: write message to log and forward to error
	 * page
	 * 
	 * @param ActionMapping
	 *            mapping
	 * @param String
	 *            message - to write to log
	 * @return ActionForward - error mapping
	 */
	private ActionForward errorHandler(ActionMapping mapping, String message) {
		LOG.error(message);
		return mapping.findForward(ActionConstants.FAILURE.toString());
	}
}
