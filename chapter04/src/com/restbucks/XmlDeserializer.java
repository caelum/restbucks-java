package com.restbucks;

import br.com.caelum.vraptor.deserialization.XStreamXmlDeserializer;
import br.com.caelum.vraptor.http.ParameterNameProvider;
import br.com.caelum.vraptor.ioc.ApplicationScoped;
import br.com.caelum.vraptor.ioc.Component;

import com.thoughtworks.xstream.XStream;

/**
 * Returns our pre-configured version of xstream.
 * 
 * @author guilherme silveira
 */
@Component
@ApplicationScoped
public class XmlDeserializer extends XStreamXmlDeserializer {

	public XmlDeserializer(ParameterNameProvider provider) {
		super(provider);
	}

	@Override
	protected XStream getXStream() {
		XStream instance = super.getXStream();
		instance.processAnnotations(Order.class);
		instance.processAnnotations(Item.class);
		return instance;
	}

}
