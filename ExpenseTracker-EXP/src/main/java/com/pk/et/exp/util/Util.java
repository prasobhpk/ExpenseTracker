package com.pk.et.exp.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.map.ObjectMapper;

import com.pk.et.infra.util.SearchFilter;

public class Util {
	public static String getPath(HttpServletRequest request) {
		return "http://" + request.getLocalAddr() + ":"
				+ request.getLocalPort() + request.getContextPath() + "/";
	}

	public static SearchFilter getSearchFilter(String json) {
		ObjectMapper mapper = new ObjectMapper(); // can reuse, share globally
		SearchFilter filter = null;
		try {
			filter = mapper.readValue(json, SearchFilter.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return filter;
	}
	
	public static String scrape(String url) {
        try {
            URL u = new URL(url);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                    u.openStream()));

            String inputLine;
            StringBuilder b = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                b.append(inputLine);
            }

            in.close();
            return b.toString();
        } catch (IOException ioe) {
            return "Could not scrape URL: " + ioe;
        } 
    }
}
