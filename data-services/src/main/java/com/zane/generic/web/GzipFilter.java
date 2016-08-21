package com.zane.generic.web;

import java.io.IOException;
import java.util.Enumeration;
import java.util.StringTokenizer;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

// Referenced classes of package com.lehman.fia.filter:
//      GzipResponseWrapper

public class GzipFilter
  implements Filter
{

  public GzipFilter()
  {
    uriMatch = null;
    uriExclude = null;
    minThreshold = 128;
    extensions = null;
    excludes = null;
  }

  protected FilterConfig getFilterConfig()
  {
    return config;
  }

  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
    throws ServletException, IOException
  {
    HttpServletRequest req = (HttpServletRequest)request;
    HttpServletResponse res = (HttpServletResponse)response;
    if(threshold == 0 || !isGzipSupported(req) || !isGzipEligible(req))
    {
      log.info(" Sid:" + req.getSession().getId() + " GZIP not supported for " + req.getRequestURI());
      isGzipEligible(req);
      chain.doFilter(request, response);
      return;
    }
    log.info(" Sid:" + req.getSession().getId() + " GZIP supported for " + req.getRequestURI());
    GzipResponseWrapper wrappedResponse = new GzipResponseWrapper(res);
    wrappedResponse.setCompressionThreshold(threshold);
    try
    {
      chain.doFilter(request, wrappedResponse);
    }
    finally
    {
      wrappedResponse.finishResponse();
    }
    return;
  }

  public void init(FilterConfig config)
    throws ServletException
  {
    this.config = config;
    extensions = null;
    try
    {
      threshold = Integer.parseInt(config.getInitParameter("threshold"));
    }
    catch(NumberFormatException nfe)
    {
      threshold = 0;
    }
    if(threshold < minThreshold)
    {
      threshold = 0;
    }
    uriMatch = config.getInitParameter("match");
    if(uriMatch != null && !uriMatch.equals("*"))
    {
      StringTokenizer st = new StringTokenizer(uriMatch, ",");
      int i = st.countTokens();
      if(i >= 1)
      {
        extensions = new String[i];
        for(i = 0; st.hasMoreTokens(); i++)
        {
          extensions[i] = st.nextToken().trim();
        }

      }
    }
    uriExclude = config.getInitParameter("exclude");
    if(uriExclude != null)
    {
      StringTokenizer st = new StringTokenizer(uriExclude, ",");
      int i = st.countTokens();
      if(i >= 1)
      {
        excludes = new String[i];
        for(i = 0; st.hasMoreTokens(); i++)
        {
          excludes[i] = st.nextToken().trim();
        }

      }
    }
  }

  public void destroy()
  {
    config = null;
    extensions = null;
    excludes = null;
  }

  private boolean isGzipSupported(HttpServletRequest req)
  {
    String s;
    for(Enumeration de = req.getHeaderNames(); de.hasMoreElements();)
    {
      s = (String)de.nextElement();      
    }

    String browserEncodings = req.getHeader("Accept-Encoding");
    return browserEncodings != null && browserEncodings.indexOf("gzip") != -1;
  }

  private boolean isGzipEligible(HttpServletRequest req)
  {
    String uri = req.getRequestURI();
    if(uri == null)
    {
      return false;
    }
    boolean result = false;
    if(extensions == null)
    {
      result = true;
    } else
    {
      for(int i = 0; i < extensions.length; i++)
      {
        if(uri.indexOf(extensions[i]) == -1)
        {
          continue;
        }
        result = true;
        break;
      }

    }
    if(result && excludes != null)
    {
      for(int i = 0; i < excludes.length; i++)
      {
        if(uri.indexOf(excludes[i]) == -1)
        {
          continue;
        }
        result = false;
        break;
      }

    }
    return result;
  }

  private FilterConfig config = null;
  private static Log log = null;
  protected String uriMatch = null;
  protected String uriExclude = null;
  private int minThreshold = 0;
  protected int threshold = 0;
  private String extensions[] = null;
  private String excludes[] = null;

  static 
  {
    log = LogFactory.getLog(GzipFilter.class);
  }
}

 