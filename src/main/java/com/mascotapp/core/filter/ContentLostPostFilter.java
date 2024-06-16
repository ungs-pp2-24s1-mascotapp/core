package com.mascotapp.core.filter;

public class ContentLostPostFilter extends ContentPostFilter {
	
	public ContentLostPostFilter() {
		super(new String[] {"perdí","perdi", "buscando", "perdio", "escapó"});
	}
}
