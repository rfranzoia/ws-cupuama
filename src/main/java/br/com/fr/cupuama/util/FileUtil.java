package br.com.fr.cupuama.util;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class FileUtil {
	public static Byte[] extractBytesFromImagePath(String ImageName) throws IOException {
		Byte[] converted = null;

		// open image
		File imgPath = new File(ImageName);
		BufferedImage bufferedImage = ImageIO.read(imgPath);

		// get DataBufferBytes from Raster
		WritableRaster raster = bufferedImage.getRaster();
		DataBufferByte data = (DataBufferByte) raster.getDataBuffer();

		converted = bytesToBytesObject(data.getData());

		return converted;
	}

	private static Byte[] bytesToBytesObject(byte[] bytesPrim) {

		Byte[] bytes = new Byte[bytesPrim.length];
		int i = 0;
		for (byte b : bytesPrim)
			bytes[i++] = b; // Autoboxing
		return bytes;
	}
}