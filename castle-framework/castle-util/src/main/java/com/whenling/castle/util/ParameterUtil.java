package com.whenling.castle.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.whenling.castle.core.CastleConstants;

public class ParameterUtil {

	public static final Splitter andSplitter = Splitter.on("&");
	public static final Splitter dotSplitter = Splitter.on(".");

	@SuppressWarnings("unchecked")
	public static Map<String, Object> formSerializeToMap(String serialize) {
		if (Strings.isNullOrEmpty(serialize)) {
			return new HashMap<>();
		}

		Map<String, Object> parameters = new HashMap<>();
		andSplitter.split(serialize).forEach(parameter -> {
			String[] keyValue = StringUtils.split(parameter, "=");
			String key = keyValue[0];
			String value = null;
			if (keyValue.length == 2) {
				try {
					value = URLDecoder.decode(keyValue[1], CastleConstants.characterEncoding);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}

			List<String> segments = Lists.newArrayList(dotSplitter.split(key));
			int size = segments.size();
			Map<String, Object> path = parameters;
			for (int i = 0; i < size; i++) {
				if (i + 1 < size) {
					path = (Map<String, Object>) path.get(segments.get(i));
					if (path == null) {
						path = new HashMap<>();
					}
				} else {
					Object obj = path.get(segments.get(i));
					if (obj == null) {
						path.put(segments.get(i), value);
					} else {
						if (obj.getClass().isArray()) {
							path.put(segments.get(i), ArrayUtils.add((Object[]) obj, value));
						} else {
							path.put(segments.get(i), new Object[] { obj, value });
						}
					}

				}
			}

		});

		return parameters;
	}

}
