package com.liferay.test;
import java.io.IOException;
import java.io.Writer;
import java.util.logging.Logger;

import javax.portlet.GenericPortlet;
import javax.portlet.MimeResponse;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServletRequest;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;

import com.liferay.portal.kernel.util.StringBundler;
import org.w3c.dom.Element;


public class TestPortlet extends GenericPortlet {

	private static final String ELEMENT_ATTR_SRC = "src";
	private static final String ELEMENT_ATTR_TYPE = "type";
	private static final String ELEMENT_ATTR_REL = "rel";
	private static final String ELEMENT_ATTR_HREF = "href";
	private static final String ELEMENT_ATTR_NAME= "name";
	private static final String ELEMENT_ATTR_CONTENT= "content";

	private static final String ELEMENT_NAME_SCRIPT = "script";
	private static final String ELEMENT_NAME_LINK = "link";
	private static final String ELEMENT_NAME_META = "meta";

	private static final String ELEMENT_VALUE_JAVASCRIPT = "text/javascript";
	private static final String ELEMENT_VALUE_CSS = "text/css";

	private static Log _log = LogFactoryUtil.getLog(TestPortlet.class);

	private boolean isAjaxCall(RenderRequest renderRequest) {
		HttpServletRequest httpRequest =
			((LiferayPortletRequest)renderRequest).getHttpServletRequest();
		String uri = httpRequest.getRequestURI();
		String method = httpRequest.getMethod();

		boolean ajax =
			uri.contains("c/portal/update_layout") && "POST".equals(method);

		if (!ajax)  {
			for (StackTraceElement ste : Thread.currentThread().getStackTrace()) {
				if (ste.getMethodName().equals("renderAjax") ||
					ste.getClassName().contains("RenderPortletAction")) {
					ajax = true;
					break;
				}
			}
		}
		return ajax;
	}

	private String getTheadId() {
		return Thread.currentThread().getName()+"["+Thread.currentThread().getId()+"]";
	}

	private String getScriptCode(RenderRequest renderRequest, RenderResponse renderResponse, String from, String how) {
		StringBundler sb = new StringBundler();
		sb.append("alert('");
		sb.append("Test Markup Head Ajaxable\\n");
		sb.append("From: " + from + "\\n");
		sb.append("Mechanism: " + how + "\\n");
		sb.append("Thread: " + getTheadId() + "\\n");
		sb.append("Ajax render?: " + isAjaxCall(renderRequest) + "')");
		return sb.toString();
	}

	private void doAddMarkupHeadElements(
		RenderRequest renderRequest, RenderResponse renderResponse,
		String from) {

		Element script = renderResponse.createElement(ELEMENT_NAME_SCRIPT);
		script.setAttribute(ELEMENT_ATTR_TYPE, ELEMENT_VALUE_JAVASCRIPT);
		script.setTextContent(
			getScriptCode(
				renderRequest, renderResponse, from,
				"JSR-286 MARKUP_HEAD_ELEMENT"));
		renderResponse.addProperty(MimeResponse.MARKUP_HEAD_ELEMENT, script);

		Element link = renderResponse.createElement(ELEMENT_NAME_LINK);
		link.setAttribute(ELEMENT_ATTR_REL, "stylesheet");
		link.setAttribute(ELEMENT_ATTR_TYPE, ELEMENT_VALUE_CSS);
		link.setAttribute(ELEMENT_ATTR_HREF, "main.css");
		renderResponse.addProperty(MimeResponse.MARKUP_HEAD_ELEMENT, link);

		for (int i = 0; i < 10000; i++) {
			Element meta = renderResponse.createElement(ELEMENT_NAME_META);
			meta.setAttribute(ELEMENT_ATTR_NAME, "threadId");
			meta.setAttribute(ELEMENT_ATTR_CONTENT, getTheadId() + "(iteration " + i + ")");
			renderResponse.addProperty(MimeResponse.MARKUP_HEAD_ELEMENT, meta);
		}
	}

	private void doWriteContentIntoResponse(RenderRequest renderRequest, RenderResponse renderResponse, String from) {
		try {
			Writer output = renderResponse.getWriter();

			StringBundler sb = new StringBundler();

			sb.append("<script type='text/javascript'>");
			sb.append(getScriptCode(
				renderRequest, renderResponse, from, "writing into response"));
			sb.append("</script>");

			sb.append("<meta name='author' content='daniel'/>");
			sb.append("<link rel='stylesheet' type='text/css' href='main.css'/>");

			output.write(sb.toString());
		}
		catch (IOException e) {
			System.err.println("!@#$ " + from + ": IO Exception");
		}
	}

	protected void generateContent(
		RenderRequest renderRequest, RenderResponse renderResponse,
		String method) {

		try {
			Writer output = renderResponse.getWriter();
			output.write("<p>Generating content from " + method + " :</p>");
			output.write("<ul><li>Creating JSR-286 markup head elements ...");
			doAddMarkupHeadElements(renderRequest, renderResponse, method);
			output.write("done </li>");
			output.write("<li>Writing content into response ...");
			doWriteContentIntoResponse(renderRequest, renderResponse, method);
			output.write("done</li></ul>");
		}
		catch (IOException e) {
			System.err.println("!@#$ " + method + ": IO Exception");
		}
	}

	@Override
	protected void doHeaders(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		String method = "doHeaders()";

		_log.error(method + "(" + getTheadId() + ") - start");

		generateContent(renderRequest, renderResponse, method);

		_log.error(method + "(" + getTheadId() + ") - end");
	}

	@Override
	protected void doView(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException, IOException {

		String method = "doView()";

		_log.error(method + "(" + getTheadId() + ") - start");
		try {
			Thread.sleep(1000);
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		Writer output = renderResponse.getWriter();

		generateContent(renderRequest, renderResponse, method);

		if (isAjaxCall(renderRequest)) {
			output.write("<p><b>You just dropped me into the page</b></p>");
			/*output.write("<p>I am rendering from an ajax request, so</p>");
			output.write("<ul><li>No 'markup head element' was added to the HEAD.</li>");
			output.write("<li>Two scripts were written into response. They will be executed <b>after</b> this text was shown (please close 2 alert windows)</li></ul>");
			output.write("<p>Now, look at page source and check that there are not scrips neither in HEAD nor in portlet body (string 'Test Markup Head Ajaxable' occurs 0 times within script code)</p>");
			output.write("<p>Then, <b>reload</b> the page. You will see 2 alerts before any content is shown</p>");*/
		}
		else {
			output.write("<p><b>You just requested the whole page to the server</b></p>");
			/*output.write("<p>I am rendering as part of regular page rendering, so</p>");
			output.write("<ul><li>Two 'markup head element' scripts were added to the HEAD. Therefore, they were executed <b>before</b> rendering the page body.</li>");
			output.write("<li>Two scripts were written into response, but were executed as they appear in the response, <b>before</b> this text was shown</li></ul>");
			output.write("<p>Now, look at page source and check that the string 'Test Markup Head Ajaxable' occurs 4 times (within script code) per instance of this portlet</p>");*/
		}

		_log.error(method + "(" + getTheadId() + ") - end");
	}

}
