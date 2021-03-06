package edu.ncsu.csc.itrust.serverutils;

import java.util.Enumeration;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

@SuppressWarnings("all")
public class MockHttpSession implements HttpSession {
	static int mins = 0;

	//Added syncronized to prevent issues from accessing static attribute from mutliple instances.
	public synchronized void setMaxInactiveInterval(int arg0) {
		mins = arg0;
	}

	public Object getAttribute(String arg0) {
		throw new IllegalStateException("should not be hit!");
	}

	
	
	public Enumeration getAttributeNames() {
		throw new IllegalStateException("should not be hit!");
	}

	public long getCreationTime() {
		throw new IllegalStateException("should not be hit!");
	}

	public String getId() {
		throw new IllegalStateException("should not be hit!");
	}

	public long getLastAccessedTime() {
		throw new IllegalStateException("should not be hit!");
	}

	public synchronized int getMaxInactiveInterval() {
		throw new IllegalStateException("should not be hit!");
	}

	public ServletContext getServletContext() {
		throw new IllegalStateException("should not be hit!");
	}

	@Deprecated
	public HttpSessionContext getSessionContext() {
		throw new IllegalStateException("should not be hit!");
	}

	public Object getValue(String arg0) {
		throw new IllegalStateException("should not be hit!");
	}

	public String[] getValueNames() {
		throw new IllegalStateException("should not be hit!");
	}

	public void invalidate() {
		throw new IllegalStateException("should not be hit!");
	}

	public boolean isNew() {
		throw new IllegalStateException("should not be hit!");
	}

	public void putValue(String arg0, Object arg1) {
		throw new IllegalStateException("should not be hit!");
	}

	public void removeAttribute(String arg0) {
		throw new IllegalStateException("should not be hit!");
	}

	public void removeValue(String arg0) {
		throw new IllegalStateException("should not be hit!");
	}

	public void setAttribute(String arg0, Object arg1) {
		throw new IllegalStateException("should not be hit!");
	}
}
