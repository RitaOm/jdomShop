package com.epam.testapp.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Map;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.jdom2.Document;
import org.jdom2.output.XMLOutputter;

/**
 * XSLXmlManager --- public final class with private constructor and static
 * methods for making xslt, writing to xml file xslt result or writing to xml
 * file JDOM Document
 * 
 * @author Marharyta_Amelyanchuk
 */
public final class XslXmlManager {

	private static final String ENCODING = "UTF-8";

	/**
	 * public static method to make xsl transformation. 
	 * 
	 * @param String
	 *            xslRelativePath
	 * @param File
	 *            xmlFile
	 * @param Writer
	 *            resultWriter
	 * @param Map
	 *            <String, Object> paramsMap
	 * 
	 * @exception TransformerException
	 */
	public static void makeXSLTransform(String xslRelativePath, File xmlFile,
			Writer resultWriter, Map<String, Object> paramsMap)
			throws TransformerException {
		Transformer transformer = TemplatesCache
				.getTransformer(xslRelativePath);
		Source source = new StreamSource(xmlFile);
		StreamResult streamResult = new StreamResult(resultWriter);
		for (String key : paramsMap.keySet()) {
			transformer.setParameter(key, paramsMap.get(key));
		}
	    transformer.transform(source, streamResult);		
	}

	/**
	 * public static method to write to xml file xslt result. 
	 * 
	 * @param Writer
	 *            result
	 * @param File
	 *            xmlFile
	 * 
	 * @exception IOException
	 */
	public static void writeXSLTResultToXMLFile(Writer result, File xmlFile)
			throws IOException {
		Writer fileWriter = null;
		
		try {
			fileWriter = new PrintWriter(xmlFile, ENCODING);
			fileWriter.write(result.toString());
		} finally {
			if (fileWriter != null) {
				fileWriter.close();
			}
		}
	}

	/**
	 * public static method to write to xml file JDOM Document. 
	 * 
	 * @param Document document
	 * @param File
	 *            xmlFile
	 * 
	 * @exception IOException
	 */
	public static void writeDocumentToXMLFile(Document document, File xmlFile)
			throws IOException {
		Writer fileWriter = null;
			try {
			fileWriter = new FileWriter(xmlFile);
			new XMLOutputter().output(document, fileWriter);
		} finally {
			if (fileWriter != null) {
				fileWriter.close();
			}
		}
	}

}
