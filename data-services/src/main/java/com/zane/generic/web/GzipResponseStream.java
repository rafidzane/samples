 
package com.zane.generic.web;

import java.io.IOException;
import java.util.zip.GZIPOutputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

public class GzipResponseStream extends ServletOutputStream
{

  public GzipResponseStream(HttpServletResponse response)
    throws IOException
  {
    compressionThreshold = 0;
    buffer = null;
    bufferCount = 0;
    gzipstream = null;
    closed = false;
    length = -1;
    this.response = null;
    output = null;
    closed = false;
    this.response = response;
    output = response.getOutputStream();
  }

  protected void setBuffer(int threshold)
  {
    compressionThreshold = threshold;
    buffer = new byte[compressionThreshold];
  }

  public void close()
    throws IOException
  {
    if(closed)
    {
      return;
    }
    if(gzipstream != null)
    {
      flushToGZip();
      gzipstream.close();
      gzipstream = null;
    } else
    if(bufferCount > 0)
    {
      output.write(buffer, 0, bufferCount);
      bufferCount = 0;
    }
    output.close();
    closed = true;
  }

  public void flush()
    throws IOException
  {
    if(closed)
    {
      return;
    }
    if(gzipstream != null)
    {
      gzipstream.flush();
    }
  }

  public void flushToGZip()
    throws IOException
  {
    if(bufferCount > 0)
    {
      writeToGZip(buffer, 0, bufferCount);
      bufferCount = 0;
    }
  }

  public void write(int b)
    throws IOException
  {
    if(closed)
    {
      throw new IOException("Cannot write to a closed output stream");
    }
    if(bufferCount >= buffer.length)
    {
      flushToGZip();
    }
    buffer[bufferCount++] = (byte)b;
  }

  public void write(byte b[])
    throws IOException
  {
    write(b, 0, b.length);
  }

  public void write(byte b[], int off, int len)
    throws IOException
  {
    if(closed)
    {
      throw new IOException("Cannot write to a closed output stream");
    }
    if(len == 0)
    {
      return;
    }
    if(len <= buffer.length - bufferCount)
    {
      System.arraycopy(b, off, buffer, bufferCount, len);
      bufferCount += len;
      return;
    }
    flushToGZip();
    if(len <= buffer.length - bufferCount)
    {
      System.arraycopy(b, off, buffer, bufferCount, len);
      bufferCount += len;
      return;
    } else
    {
      writeToGZip(b, off, len);
      return;
    }
  }

  public void writeToGZip(byte b[], int off, int len)
    throws IOException
  {
    if(gzipstream == null)
    {
      gzipstream = new GZIPOutputStream(output);
      response.addHeader("Content-Encoding", "gzip");
    }
    gzipstream.write(b, off, len);
  }

  public boolean closed()
  {
    return closed;
  }

  protected int compressionThreshold = 0;
  protected byte buffer[] = null;
  protected int bufferCount = 0;
  protected GZIPOutputStream gzipstream = null;
  protected boolean closed = false;
  protected int length = 0;
  protected HttpServletResponse response = null;
  protected ServletOutputStream output = null;
}
 