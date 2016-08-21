package com.zane.generic.xml;

import java.io.Writer;

import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

public class CDATAPrinterDriver extends XppDriver{

	public HierarchicalStreamWriter createWriter(Writer out) {
		return new CDATAPrinter(out);
	}
}  