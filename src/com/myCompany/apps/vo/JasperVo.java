package com.myCompany.apps.vo;

import java.util.Map;

public class JasperVo {

	private String templateInternalKey;

	private Map<String, Object> templateAttributeMap;

	private Object jasperCollection;

	public String getTemplateInternalKey() {
		return templateInternalKey;
	}

	public void setTemplateInternalKey(String templateInternalKey) {
		this.templateInternalKey = templateInternalKey;
	}

	public Map<String, Object> getTemplateAttributeMap() {
		return templateAttributeMap;
	}

	public void setTemplateAttributeMap(Map<String, Object> templateAttributeMap) {
		this.templateAttributeMap = templateAttributeMap;
	}

	public Object getJasperCollection() {
		return jasperCollection;
	}

	public void setJasperCollection(Object jasperCollection) {
		this.jasperCollection = jasperCollection;
	}

}
