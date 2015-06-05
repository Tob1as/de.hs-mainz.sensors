package de.hsmainz.sensors.helper;

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

public class XML implements Output {
	
	/*
	 * Write Measurements in XML
	 */
	
	/*
	 * Help:
	 * 
	 * http://www.mkyong.com/tutorials/java-xml-tutorials/
	 * http://stackoverflow.com/questions/1384802/java-how-to-indent-xml-generated-by-transformer
	 * http://www.tutorialspoint.com/java_xml/java_dom_parser.htm
	 * http://examples.javacodegeeks.com/core-java/xml/dom/add-node-to-dom-document/ AND http://examples.javacodegeeks.com/core-java/xml/dom/add-text-node-to-dom-document/
	 */

	@Override
	public void write(Variables variables) {
		this.writeMeasureInXML(variables);
	}
	
	private void writeMeasureInXML(Variables variables) {
		try {
			 
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			//docFactory.setValidating(false);
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
	 
			
			Document doc = null;
			Element rootElement = null;
			// Load file with root elements and datas
			if (variables.getFilenameXML().exists()) {
				doc = docBuilder.parse(new FileInputStream(variables.getFilenameXML()));
				rootElement = doc.getDocumentElement();
			}
			// Create file with root elements
			if (!variables.getFilenameXML().exists()) {
				doc = docBuilder.newDocument();
				rootElement = doc.createElement("sensors");
				doc.appendChild(rootElement);
			}
			
	 
			// measure elements
			Element measure = doc.createElement("measure");
			rootElement.appendChild(measure); // "sensors" instead "rootElement" when add new measured in a exitens xml file for whitespace (indent-amount) ??
	 
			// set attribute to measure element
			Attr attr = doc.createAttribute("id");
			attr.setValue(variables.getId());
			measure.setAttributeNode(attr);
	 
			// shorten way
			// measure.setAttribute("id", "variables.getId()");
	 
			// date elements
			Element date = doc.createElement("date");
			date.appendChild(doc.createTextNode(variables.getDate()));
			measure.appendChild(date);
			
			// time elements
			Element time = doc.createElement("time");
			time.appendChild(doc.createTextNode(variables.getTime()));
			measure.appendChild(time);
			
			// battery elements
			Element battery = doc.createElement("battery");
			battery.appendChild(doc.createTextNode(String.format("%.2f", variables.getBattery())));
			measure.appendChild(battery);
			
			// humidity elements
			Element humidity = doc.createElement("humidity");
			humidity.appendChild(doc.createTextNode(String.format("%.2f", variables.getHumidity())));
			measure.appendChild(humidity);
	 
			// irtemperature elements
			Element irtemperature = doc.createElement("irtemperature");
			irtemperature.appendChild(doc.createTextNode(String.format("%.2f", variables.getIrtemperature())));
			measure.appendChild(irtemperature);
			
			// temperature elements
			Element temperature = doc.createElement("temperature");
			temperature.appendChild(doc.createTextNode(String.format("%.2f", variables.getTemperature())));
			measure.appendChild(temperature);
			
			// dewpoint elements
			Element dewpoint = doc.createElement("dewpoint");
			dewpoint.appendChild(doc.createTextNode(String.format("%.2f", variables.getDewpoint())));
			measure.appendChild(dewpoint);
	 
			// pressure elements
			Element pressure = doc.createElement("pressure");
			pressure.appendChild(doc.createTextNode(String.format("%.2f", variables.getPressure())));
			measure.appendChild(pressure);
	 
			// rgb elements
			Element rgb = doc.createElement("lux");
			rgb.appendChild(doc.createTextNode(String.format("%.2f", variables.getRgb())));
			measure.appendChild(rgb);
			
			// altitude elements
			Element altitude = doc.createElement("altitude");
			altitude.appendChild(doc.createTextNode(String.valueOf(variables.getAltitude())));
			measure.appendChild(altitude);
			
			// capacitance elements
			Element capacitance = doc.createElement("capacitance");
			capacitance.appendChild(doc.createTextNode(String.valueOf(variables.getCapacitance())));
			measure.appendChild(capacitance);
			
			// oxidizinggas elements
			Element oxidizinggas = doc.createElement("oxidizinggas");
			oxidizinggas.appendChild(doc.createTextNode(String.format("%.2f", variables.getOxidizinggas())));
			measure.appendChild(oxidizinggas);
			
			// precisiongas elements
			Element precisiongas = doc.createElement("precisiongas");
			precisiongas.appendChild(doc.createTextNode(String.valueOf(variables.getPrecisiongas())));
			measure.appendChild(precisiongas);
			
			// reducinggas elements
			Element reducinggas = doc.createElement("reducinggas");
			reducinggas.appendChild(doc.createTextNode(String.format("%.2f", variables.getReducinggas())));
			measure.appendChild(reducinggas);
			
			// uart/CO2 elements
			Element co2 = doc.createElement("co2");
			co2.appendChild(doc.createTextNode(String.format("%.2f", variables.getCo2())));
			measure.appendChild(co2);
	 
			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.ENCODING,"UTF-8");
			transformer.setOutputProperty(OutputKeys.METHOD,"xml");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(variables.getFilenameXML());
			transformer.transform(source, result);
	 
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		} catch (SAXException | IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}