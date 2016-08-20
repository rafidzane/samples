package com.zane.chart.web.filter;

import java.io.ByteArrayOutputStream;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;


public class BufferedResponseWrapper
   extends HttpServletResponseWrapper
{
   private ByteArrayOutputStream byteBuffer;
   private CharArrayWriter charBuffer;
   private ServletOutputStream outputStream;
   private PrintWriter writer;
   private ServletContext context;

   /**
   * Creates a new <code>BufferedResponseWrapper</code>
   */
   public BufferedResponseWrapper
      (ServletResponse response)
   {
      super((HttpServletResponse) response);     
   }

    
   public ServletOutputStream getOutputStream()
      throws IOException
   {
      if (writer != null) {
         String errmsg = "getWriter() was already called";
         throw new IllegalStateException(errmsg);
      }

      if (outputStream == null) {

         // Create a subclass of ServletOutputStream
         // that delegates everything to a
         // ByteArrayOutputStream

         byteBuffer = new ByteArrayOutputStream();
         outputStream = new ServletOutputStream() {
            public void write(int c) throws IOException {
               byteBuffer.write(c);
            }
         };
      }

      return outputStream;
   }

  
   public PrintWriter getWriter()
      throws IOException
   {
      if (outputStream != null) {
         String errmsg = "getOutputStream() was already called";
         throw new IllegalStateException(errmsg);
      }

      if (writer == null) {
         charBuffer = new CharArrayWriter();
         writer = new PrintWriter(charBuffer);
      }

      return writer;
   }

   
   public String getBufferAsString()
      throws IOException
   {
      String buffer = null;

      if (charBuffer != null) {
         writer.flush();
         charBuffer.flush();
         buffer = charBuffer.toString();
      }
      else if (byteBuffer != null) {
         outputStream.flush();
         byteBuffer.flush();
         buffer = byteBuffer.toString();
      }
      else
         buffer = "";

      return buffer;
   }

    
   public byte[] getBufferAsByteArray()
      throws IOException
   {
      byte[] buffer = null;

      if (byteBuffer != null) {
         outputStream.flush();
         byteBuffer.flush();
         buffer = byteBuffer.toByteArray();
      }
      else if (charBuffer != null) {
         writer.flush();
         charBuffer.flush();
         buffer = charBuffer.toString().getBytes();
      }
      else
         buffer = new byte[0];

      return buffer;
   }
   
}
