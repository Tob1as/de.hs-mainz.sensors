package de.hsmainz.sensors.helper;

import java.io.File;

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

public class XML {
	
	// TODO XML mit neuen Messwerten erweitern, statt zu überschreiben!
	
	/*
	 * Write Measurements in XML
	 */
	public void writeMeasureInXML(String date2, String time2, Float humidity2, Float irtemp2, Float pressure2, Float rgb2, Float temp2, Float battery2) {
		// http://www.mkyong.com/java/how-to-create-xml-file-in-java-dom/
		// http://stackoverflow.com/questions/1384802/java-how-to-indent-xml-generated-by-transformer
		// http://kohnlehome.de/java/jdom.pdf
		try {
			 
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
	 
			// root elements
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("sensors");
			doc.appendChild(rootElement);
	 
			// measure elements
			Element measure = doc.createElement("measure");
			rootElement.appendChild(measure);
	 
			// set attribute to measure element
			Attr attr = doc.createAttribute("id");
			attr.setValue(date2+"-"+time2);
			measure.setAttributeNode(attr);
	 
			// shorten way
			// measure.setAttribute("id", "date2+"-"+time2");
	 
			// humidity elements
			Element humidity = doc.createElement("humidity");
			humidity.appendChild(doc.createTextNode(String.format("%.2f", humidity2)));
			measure.appendChild(humidity);
	 
			// irtemp elements
			Element irtemp = doc.createElement("irtemp");
			irtemp.appendChild(doc.createTextNode(String.format("%.2f", irtemp2)));
			measure.appendChild(irtemp);
	 
			// pressure elements
			Element pressure = doc.createElement("pressure");
			pressure.appendChild(doc.createTextNode(String.format("%.2f", pressure2)));
			measure.appendChild(pressure);
	 
			// rgb elements
			Element rgb = doc.createElement("lux");
			rgb.appendChild(doc.createTextNode(String.format("%.2f", rgb2)));
			measure.appendChild(rgb);
			
			// temp elements
			Element temp = doc.createElement("temp");
			temp.appendChild(doc.createTextNode(String.format("%.2f", temp2)));
			measure.appendChild(temp);
			
			// battery elements
			Element battery = doc.createElement("battery");
			battery.appendChild(doc.createTextNode(String.format("%.2f", battery2)));
			measure.appendChild(battery);

	 
			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.ENCODING,"UTF-8");
			transformer.setOutputProperty(OutputKeys.METHOD,"xml");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("measure.xml"));
	 
			// Output to console for testing
			// StreamResult result = new StreamResult(System.out);
	 
			transformer.transform(source, result);
	 
			//System.out.println("File saved!");
	 
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}
	}
}
