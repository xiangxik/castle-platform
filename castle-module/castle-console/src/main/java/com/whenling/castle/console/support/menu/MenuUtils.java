package com.whenling.castle.console.support.menu;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.dom.DOMElement;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.springframework.core.io.ClassPathResource;

public class MenuUtils {

	private static Map<String, Menu> menus = new HashMap<>();

	public static Menu get(String key) {
		return menus.get(key);
	}

	public static void set(Menu menu, String xmlPath) {
		try {
			File xmlFile = new ClassPathResource(xmlPath).getFile();
			Document document = new SAXReader().read(xmlFile);

			Element rootEl = document.getRootElement();
			rootEl.attribute("name").setValue(menu.getName());
			rootEl.attribute("type").setValue(menu.getType());
			misToEls(menu.getRoots(), rootEl);

			FileOutputStream fileOutputStream = null;
			XMLWriter xmlWriter = null;
			try {
				OutputFormat outputFormat = OutputFormat.createPrettyPrint();
				outputFormat.setEncoding("UTF-8");
				outputFormat.setIndent(true);
				outputFormat.setIndent("	");
				outputFormat.setNewlines(true);
				fileOutputStream = new FileOutputStream(xmlFile);
				xmlWriter = new XMLWriter(fileOutputStream, outputFormat);
				xmlWriter.write(document);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (xmlWriter != null) {
					try {
						xmlWriter.close();
					} catch (IOException e) {
					}
				}
				IOUtils.closeQuietly(fileOutputStream);
			}

		} catch (IOException | DocumentException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public static void register(String key, String xmlPath) {
		try {
			File xmlFile = new ClassPathResource(xmlPath).getFile();
			Document document = new SAXReader().read(xmlFile);

			Element rootEl = document.getRootElement();
			Menu menu = new Menu();
			menu.setName(rootEl.attributeValue("name"));
			menu.setType(rootEl.attributeValue("type"));

			menu.setRoots(elsToMis(rootEl.elements(), null));

			menus.put(key, menu);
		} catch (IOException | DocumentException e) {
			e.printStackTrace();
		}
	}

	protected static void misToEls(List<MenuItem> menuItems, Element parent) {
		if (menuItems == null || menuItems.size() == 0) {
			return;
		}
		parent.clearContent();
		for (MenuItem mi : menuItems) {
			Element element = new DOMElement("mi");
			element.attribute("name").setValue(mi.getName());
			element.attribute("code").setValue(mi.getCode());
			element.attribute("href").setValue(mi.getHref());
			element.attribute("iconCls").setValue(mi.getIconCls());
			element.attribute("parameters").setValue(mi.getParameters());
			parent.add(element);
			misToEls(mi.getChildren(), element);
		}
	}

	@SuppressWarnings("unchecked")
	protected static List<MenuItem> elsToMis(List<Element> els, MenuItem parent) {
		if (els == null || els.size() == 0) {
			return null;
		}

		List<MenuItem> menuItems = new ArrayList<>();
		for (Element el : els) {
			MenuItem menuItem = new MenuItem();
			menuItem.setName(el.attributeValue("name"));
			menuItem.setCode(el.attributeValue("code"));
			menuItem.setHref(el.attributeValue("href"));
			menuItem.setIconCls(el.attributeValue("iconCls"));
			menuItem.setParameters(el.attributeValue("parameters"));
			menuItem.setParent(parent);
			menuItem.setChildren(elsToMis(el.elements(), menuItem));
			menuItems.add(menuItem);
		}
		return menuItems;
	}

}
