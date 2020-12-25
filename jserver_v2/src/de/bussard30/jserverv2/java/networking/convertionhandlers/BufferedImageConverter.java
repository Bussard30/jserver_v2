package de.bussard30.jserverv2.java.networking.convertionhandlers;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.Base64;

import javax.imageio.ImageIO;

import de.bussard30.jserverv2.java.networking.types.ConvertionHandling;

@ConvertionHandling(target = BufferedImage.class)
public class BufferedImageConverter extends ConversionHandler
{

	@Override
	public String buildString(Object o) throws InvalidParameterException
	{
		if (o instanceof BufferedImage)
		{
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			try
			{
				ImageIO.write((BufferedImage) o, "png", baos);
			} catch (IOException e2)
			{
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			try
			{
				baos.flush();
			} catch (IOException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			String string = Base64.getEncoder().encodeToString(baos.toByteArray());
			try
			{
				baos.close();
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return string;
		}
		throw new InvalidParameterException();
	}

	@Override
	public Object buildObject(String s) throws InvalidParameterException
	{
		try
		{
			return ImageIO.read(new ByteArrayInputStream(Base64.getDecoder().decode(s)));
		} catch (IOException e)
		{
			e.printStackTrace();
			throw new InvalidParameterException();
		}
	}

}
