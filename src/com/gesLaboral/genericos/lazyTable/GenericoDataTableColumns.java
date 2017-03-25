package com.gesLaboral.genericos.lazyTable;

public class GenericoDataTableColumns {
	private String header;
	private String property;
	private String sorter;

	public GenericoDataTableColumns() {
	}

	public GenericoDataTableColumns(String header, String property, String sorter) {
		super();
		this.header = header;
		this.property = property;
		this.sorter = sorter;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getSorter() {
		return sorter;
	}

	public void setSorter(String sorter) {
		this.sorter = sorter;
	}

}
