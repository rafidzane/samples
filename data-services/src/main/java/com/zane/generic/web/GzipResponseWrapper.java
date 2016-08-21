 
package com.zane.generic.web;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

// Referenced classes of package com.lehman.fia.filter:
//      GzipResponseStream

public class GzipResponseWrapper extends HttpServletResponseWrapper
{

  public GzipResponseWrapper(HttpServletResponse response)
  {
    super(response);
    origResponse = null;
    stream = null;
    writer = null;
    threshold = 0;
    contentType = null;
    origResponse = response;
  }

  public void setContentType(String contentType)
  {
    this.contentType = contentType;
    origResponse.setContentType(contentType);
  }

  public void setCompressionThreshold(int threshold)
  {
    this.threshold = threshold;
  }

  public ServletOutputStream createOutputStream()
    throws IOException
  {
    GzipResponseStream stream = new GzipResponseStream(origResponse);
    stream.setBuffer(threshold);
    return stream;
  }

  public void finishResponse()
  {
    try
    {
      if(writer != null)
      {
        writer.close();
      } else
      if(stream != null)
      {
        stream.close();
      }
    }
    catch(IOException ioexception) { }
  }

  public void flushBuffer()
    throws IOException
  {
    if(stream != null)
    {
      ((GzipResponseStream)stream).flush();
    }
  }

  public ServletOutputStream getOutputStream()
    throws IOException
  {
    if(writer != null)
    {
      throw new IllegalStateException("getWriter() has already been called for this response");
    }
    if(stream == null)
    {
      stream = createOutputStream();
    }
    return stream;
  }

  public PrintWriter getWriter()
    throws IOException
  {
    if(writer != null)
    {
      return writer;
    }
    if(stream != null)
    {
      throw new IllegalStateException("getOutputStream() has already been called for this response");
    }
    stream = createOutputStream();
    String charEnc = origResponse.getCharacterEncoding();
    if(charEnc != null)
    {
      writer = new PrintWriter(new OutputStreamWriter(stream, charEnc));
    } else
    {
      writer = new PrintWriter(stream);
    }
    return writer;
  }

  public void setContentLength(int i)
  {
  }

  protected HttpServletResponse origResponse = null;
  protected ServletOutputStream stream = null;
  protected PrintWriter writer = null;
  protected int threshold = 0;
  protected String contentType = null;
}


 