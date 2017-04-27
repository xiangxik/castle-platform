package com.whenling.castle.console.support.setting;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.io.ClassPathResource;

import com.whenling.castle.core.StaticConfigSupplier;
import com.whenling.castle.web.WebSpringContext;

public class SettingUtils {

	private static Setting settingHolder;
	private static Object holder = new Object();

	public static Setting get() {
		if (settingHolder == null) {
			synchronized (holder) {
				if (settingHolder == null) {
					Setting setting = Setting.getInstance();
					String settingXmlPath = StaticConfigSupplier.getConfiguration().getString("setting.xml_path",
							"/setting.xml");
					try {
						File settingFile = new ClassPathResource(settingXmlPath).getFile();
						Document document = new SAXReader().read(settingFile);
						@SuppressWarnings("unchecked")
						List<Element> elements = document.selectNodes("/config/setting");
						BeanWrapperImpl beanWrapperImpl = new BeanWrapperImpl(setting);
						ConversionService conversionService = WebSpringContext.getBean(ConversionService.class);
						for (Element element : elements) {
							String name = element.attributeValue("name");
							String value = element.attributeValue("value");
							beanWrapperImpl.setPropertyValue(name,
									conversionService.convert(value, beanWrapperImpl.getPropertyType(name)));
						}
					} catch (IOException | DocumentException e) {
						e.printStackTrace();
					}
					settingHolder = setting;
				}
			}

		}

		return settingHolder;
	}

	public static void set(Setting setting) {
		String settingXmlPath = StaticConfigSupplier.getConfiguration().getString("setting.xml_path", "/setting.xml");
		try {
			File settingFile = new ClassPathResource(settingXmlPath).getFile();
			Document document = new SAXReader().read(settingFile);
			@SuppressWarnings("unchecked")
			List<Element> elements = document.selectNodes("/config/setting");
			BeanWrapperImpl beanWrapperImpl = new BeanWrapperImpl(setting);
			ConversionService conversionService = WebSpringContext.getBean(ConversionService.class);
			for (Element element : elements) {
				String name = element.attributeValue("name");
				String value = conversionService.convert(beanWrapperImpl.getPropertyValue(name), String.class);
				Attribute attribute = element.attribute("value");
				attribute.setValue(value);
			}

			FileOutputStream fileOutputStream = null;
			XMLWriter xmlWriter = null;
			try {
				OutputFormat outputFormat = OutputFormat.createPrettyPrint();
				outputFormat.setEncoding("UTF-8");
				outputFormat.setIndent(true);
				outputFormat.setIndent("	");
				outputFormat.setNewlines(true);
				fileOutputStream = new FileOutputStream(settingFile);
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

}
