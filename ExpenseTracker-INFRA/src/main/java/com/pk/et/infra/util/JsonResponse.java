package com.pk.et.infra.util;

import java.util.List;

public class JsonResponse<T> {
	private List<T> data;
	
	public JsonResponse(){
		
	}
	
	public JsonResponse(List<T> data){
		this.data=data;
	}
	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}
	
}
