package com.restbucks;

import javax.servlet.http.HttpServletResponse;

import br.com.caelum.vraptor.interceptor.TypeNameExtractor;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.RequestScoped;
import br.com.caelum.vraptor.serialization.XStreamXMLSerialization;

import com.thoughtworks.xstream.XStream;

@Component
@RequestScoped
public class XmlSerializer extends XStreamXMLSerialization{

	public XmlSerializer(HttpServletResponse response,
			TypeNameExtractor extractor) {
		super(response, extractor);
	}
	
	@Override
	protected XStream getXStream() {
		XStream instance = super.getXStream();
		instance.processAnnotations(Order.class);
		instance.processAnnotations(Item.class);
		return instance;
	}

}
