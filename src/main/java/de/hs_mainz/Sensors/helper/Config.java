package de.hs_mainz.Sensors.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class Config {
	
	/*
	 * Write/Read settings in config.xml from/to Variables.java
	 */
	
	/*
	 * Help:
	 * 
	 * http://www.mkyong.com/java/java-properties-file-examples/
	 * see: XML.java
	 */
	
	private static DocumentBuilderFactory docFactory = null;
	private static DocumentBuilder docBuilder = null;
	private static Document doc = null;
	private static Element rootElement = null;
	private static Attr attr = null; 
	
	public void config(Variables variables){
		if (variables.getFilenameConfig().exists()) {
			this.readConfig(variables);
		} else {
			this.createConfig(variables);
		}
	}

	private void createConfig(Variables variables) {
		try {
			 
			docFactory = DocumentBuilderFactory.newInstance();
			docBuilder = docFactory.newDocumentBuilder();

			doc = docBuilder.newDocument();
			rootElement = doc.createElement("sensorsConfig");
			doc.appendChild(rootElement);
			
			// sensordrone elements and attribute
			Element sensordrone = doc.createElement("sensordrone");
			rootElement.appendChild(sensordrone);
			attr = doc.createAttribute("Status");
			attr.setValue(ed(variables.isMeasuredSensorDrone()));
			sensordrone.setAttributeNode(attr);
			attr = doc.createAttribute("macAddress");
			attr.setValue(variables.getMacaddress());
			sensordrone.setAttributeNode(attr);	
			
			// outputCLI elements and attribute
			Element outputCLI = doc.createElement("outputCLI");
			rootElement.appendChild(outputCLI);
			attr = doc.createAttribute("Status");
			attr.setValue(ed(variables.isSaveSQL()));
			outputCLI.setAttributeNode(attr);
			
			// outputXML elements and attribute
			Element outputXML = doc.createElement("outputXML");
			rootElement.appendChild(outputXML);
			attr = doc.createAttribute("Status");
			attr.setValue(ed(variables.isWriteXML()));
			outputXML.setAttributeNode(attr);
			attr = doc.createAttribute("file");
			attr.setValue(variables.getFilenameXML().toString());
			outputXML.setAttributeNode(attr);
			
			// outputSQL elements and attribute
			Element outputSQL = doc.createElement("outputSQL");
			rootElement.appendChild(outputSQL);
			attr = doc.createAttribute("Status");
			attr.setValue(ed(variables.isSaveSQL()));
			outputSQL.setAttributeNode(attr);
			attr = doc.createAttribute("sql0Type");
			attr.setValue(variables.getSqldatabasetype());
			outputSQL.setAttributeNode(attr);
			attr = doc.createAttribute("sql1File");
			attr.setValue(variables.getFilenameSQLite().toString());
			outputSQL.setAttributeNode(attr);
			attr = doc.createAttribute("sql2Database");
			attr.setValue(variables.getSqldatabasename());
			outputSQL.setAttributeNode(attr);
			attr = doc.createAttribute("sql3Table");
			attr.setValue(variables.getSqltable());
			outputSQL.setAttributeNode(attr);
			attr = doc.createAttribute("sql4Host");
			attr.setValue(variables.getSqlhost());
			outputSQL.setAttributeNode(attr);
			attr = doc.createAttribute("sql5Port");
			attr.setValue(variables.getSqlport());
			outputSQL.setAttributeNode(attr);
			attr = doc.createAttribute("sql6User");
			attr.setValue(variables.getSqluser());
			outputSQL.setAttributeNode(attr);
			attr = doc.createAttribute("sql7Password");
			attr.setValue(variables.getSqlpassword());
			outputSQL.setAttributeNode(attr);
			
			// outputHTTP elements and attribute
			Element outputHTTP = doc.createElement("outputHTTP");
			rootElement.appendChild(outputHTTP);
			attr = doc.createAttribute("Status");
			attr.setValue(ed(variables.isUploadHTTP()));
			outputHTTP.setAttributeNode(attr);
			attr = doc.createAttribute("securityKey");
			attr.setValue(variables.getUploadHTTPsecurityKey());
			outputHTTP.setAttributeNode(attr);
			attr = doc.createAttribute("url");
			attr.setValue(variables.getUploadHTTPurl());
			outputHTTP.setAttributeNode(attr);
			
			// general settings elements and attribute
			Element general = doc.createElement("general");
			rootElement.appendChild(general);
			attr = doc.createAttribute("description");
			attr.setValue(variables.getDescription());
			general.setAttributeNode(attr);
	 
			// write the settings/config into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.ENCODING,"UTF-8");
			transformer.setOutputProperty(OutputKeys.METHOD,"xml");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(variables.getFilenameConfig());
			transformer.transform(source, result);
	 
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

	private void readConfig(Variables variables) {
		try {
			docFactory = DocumentBuilderFactory.newInstance();
			docBuilder = docFactory.newDocumentBuilder();
			
			doc = docBuilder.parse(new FileInputStream(variables.getFilenameConfig()));
			rootElement = doc.getDocumentElement();
			
			// sensordrone elements and attribute
			Element sensordrone = (Element) doc.getDocumentElement().getElementsByTagName("sensordrone").item(0);
			variables.setMeasuredSensorDrone(variables.checkStatus(sensordrone.getAttribute("Status"))); 
			variables.setMacaddress(sensordrone.getAttribute("macAddress"));
			
			// outputCLI elements and attribute
			Element outputCLI = (Element) doc.getDocumentElement().getElementsByTagName("outputCLI").item(0);
			variables.setShowCLI(variables.checkStatus(outputCLI.getAttribute("Status")));
			
			// outputXML elements and attribute
			Element outputXML = (Element) doc.getDocumentElement().getElementsByTagName("outputXML").item(0);
			variables.setWriteXML(variables.checkStatus(outputXML.getAttribute("Status")));
			variables.setFilenameXML(new File(outputXML.getAttribute("file")));
			
			// outputSQL elements and attribute
			Element outputSQL = (Element) doc.getDocumentElement().getElementsByTagName("outputSQL").item(0);
			variables.setSaveSQL(variables.checkStatus(outputSQL.getAttribute("Status")));
			variables.setSqldatabasetype(outputSQL.getAttribute("sql0Type"));
			variables.setFilenameSQLite(new File(outputSQL.getAttribute("sql1File")));
			variables.setSqldatabasename(outputSQL.getAttribute("sql2Database"));
			variables.setSqltable(outputSQL.getAttribute("sql3Table"));
			variables.setSqlhost(outputSQL.getAttribute("sql4Host"));
			variables.setSqlport(outputSQL.getAttribute("sql5Port"));
			variables.setSqluser(outputSQL.getAttribute("sql6User"));
			variables.setSqlpassword(outputSQL.getAttribute("sql7Password"));
			
			// outputHTTP elements and attribute
			Element outputHTTP = (Element) doc.getDocumentElement().getElementsByTagName("outputHTTP").item(0);
			variables.setUploadHTTP(variables.checkStatus(outputHTTP.getAttribute("Status")));
			variables.setUploadHTTPsecurityKey(outputHTTP.getAttribute("securityKey"));
			variables.setUploadHTTPurl(outputHTTP.getAttribute("url"));
			
			// general settings elements and attribute
			Element general = (Element) doc.getDocumentElement().getElementsByTagName("general").item(0);
			variables.setDescription(general.getAttribute("description"));
			
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (SAXException | IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private String ed(boolean tf){
		if (tf==true){
			return "enable";
		} else {
			return "disable";
		}
	}
}