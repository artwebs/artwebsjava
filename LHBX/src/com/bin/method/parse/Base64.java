package com.bin.method.parse;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

public class Base64
{
  public static String encode(String str)
    throws RuntimeException
  {
    byte[] bytes = str.getBytes();
    byte[] encoded = encode(bytes);
    try {
      return new String(encoded, "ASCII");
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException("ASCII is not supported!", e);
    }
  }

  public static String encode(String str, String charset)
    throws RuntimeException
  {
    byte[] bytes;
    try
    {
      bytes = str.getBytes(charset);
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException("Unsupported charset: " + charset, e);
    }
    byte[] encoded = encode(bytes);
    try {
      return new String(encoded, "ASCII");
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException("ASCII is not supported!", e);
    }
  }

  public static String decode(String str)
    throws RuntimeException
  {
    byte[] bytes;
    try
    {
      bytes = str.getBytes("ASCII");
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException("ASCII is not supported!", e);
    }
    byte[] decoded = decode(bytes);
    return new String(decoded);
  }

  public static String decode(String str, String charset)
    throws RuntimeException
  {
    byte[] bytes;
    try
    {
      bytes = str.getBytes("ASCII");
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException("ASCII is not supported!", e);
    }
    byte[] decoded = decode(bytes);
    try {
      return new String(decoded, charset);
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException("Unsupported charset: " + charset, e);
    }
  }

  public static byte[] encode(byte[] bytes)
    throws RuntimeException
  {
    return encode(bytes, 0);
  }

  public static byte[] encode(byte[] bytes, int wrapAt)
    throws RuntimeException
  {
    ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    try {
      encode(inputStream, outputStream, wrapAt);
    } catch (IOException e) {
      throw new RuntimeException("Unexpected I/O error", e);
    } finally {
      try {
        inputStream.close();
      }
      catch (Throwable localThrowable1) {
      }
      try {
        outputStream.close();
      }
      catch (Throwable localThrowable2) {
      }
    }
    return outputStream.toByteArray();
  }

  public static byte[] decode(byte[] bytes)
    throws RuntimeException
  {
    ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    try {
      decode(inputStream, outputStream);
    } catch (IOException e) {
      throw new RuntimeException("Unexpected I/O error", e);
    } finally {
      try {
        inputStream.close();
      }
      catch (Throwable localThrowable1) {
      }
      try {
        outputStream.close();
      }
      catch (Throwable localThrowable2) {
      }
    }
    return outputStream.toByteArray();
  }

  public static void encode(InputStream inputStream, OutputStream outputStream)
    throws IOException
  {
    encode(inputStream, outputStream, 0);
  }

  public static void encode(InputStream inputStream, OutputStream outputStream, int wrapAt)
    throws IOException
  {
    Base64OutputStream aux = new Base64OutputStream(outputStream, wrapAt);
    copy(inputStream, aux);
    aux.commit();
  }

  public static void decode(InputStream inputStream, OutputStream outputStream)
    throws IOException
  {
    copy(new Base64InputStream(inputStream), outputStream);
  }

  public static void encode(File source, File target, int wrapAt)
    throws IOException
  {
    InputStream inputStream = null;
    OutputStream outputStream = null;
    try {
      inputStream = new FileInputStream(source);
      outputStream = new FileOutputStream(target);
      encode(inputStream, outputStream, wrapAt);
    } finally {
      if (outputStream != null)
        try {
          outputStream.close();
        }
        catch (Throwable localThrowable1)
        {
        }
      if (inputStream != null)
        try {
          inputStream.close();
        }
        catch (Throwable localThrowable2)
        {
        }
    }
  }

  public static void encode(File source, File target)
    throws IOException
  {
    InputStream inputStream = null;
    OutputStream outputStream = null;
    try {
      inputStream = new FileInputStream(source);
      outputStream = new FileOutputStream(target);
      encode(inputStream, outputStream);
    } finally {
      if (outputStream != null)
        try {
          outputStream.close();
        }
        catch (Throwable localThrowable1)
        {
        }
      if (inputStream != null)
        try {
          inputStream.close();
        }
        catch (Throwable localThrowable2)
        {
        }
    }
  }

  public static void decode(File source, File target)
    throws IOException
  {
    InputStream inputStream = null;
    OutputStream outputStream = null;
    try {
      inputStream = new FileInputStream(source);
      outputStream = new FileOutputStream(target);
      decode(inputStream, outputStream);
    } finally {
      if (outputStream != null)
        try {
          outputStream.close();
        }
        catch (Throwable localThrowable1)
        {
        }
      if (inputStream != null)
        try {
          inputStream.close();
        }
        catch (Throwable localThrowable2)
        {
        }
    }
  }

  private static void copy(InputStream inputStream, OutputStream outputStream)
    throws IOException
  {
    int len;
    byte[] b = new byte[1024];

    while ((len = inputStream.read(b)) != -1)
      outputStream.write(b, 0, len);
  }
}