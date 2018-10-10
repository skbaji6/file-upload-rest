package com.example.filedemo.utils;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

@Component
public class UploadUtil {

	public double meanValueRedGreenBlue(BufferedImage image) {
		double redSum = 0.0;
		double greenSum = 0.0;
		double blueSum = 0.0;

		for (int y = 0; y < image.getHeight(); ++y) {
			for (int x = 0; x < image.getWidth(); ++x) {
				Color r = new Color(image.getRGB(x, y));
				redSum += r.getRed();
				greenSum += r.getGreen();
				blueSum += r.getBlue();
			}
		}
		System.out.println(redSum);
		System.out.println(greenSum);
		System.out.println(blueSum);
		return ((redSum / (image.getWidth() * image.getHeight())) + (greenSum / (image.getWidth() * image.getHeight()))
				+ (blueSum / (image.getWidth() * image.getHeight()))) / 3d;
	}
	
	public double meanValueRGB(BufferedImage image) {
		double rgbSum = 0.0;

		for (int y = 0; y < image.getHeight(); ++y) {
			for (int x = 0; x < image.getWidth(); ++x) {
				rgbSum +=image.getRGB(x, y);
			}
		}
		System.out.println(rgbSum);
		return rgbSum / (image.getWidth() * image.getHeight()) ;
	}

	public BufferedImage getBufferedImage(String imageurl) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File(imageurl));
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
		return image;
	}
	
	public boolean isInvalidFile(MultipartFile file) {

		if (file.isEmpty() || file.getSize() == 0
				|| !(file.getContentType().toLowerCase().equals("image/jpg")
						|| file.getContentType().toLowerCase().equals("image/jpeg")
						|| file.getContentType().toLowerCase().equals("image/png"))) {
			return true;
		}

		return false;
	}
	
	public void getPdfProperties(Path path) {
		try{
		PDDocument doc = PDDocument.load(path.toFile());
		PDDocumentInformation info = doc.getDocumentInformation();
		String author = info.getAuthor();
		int pages = doc.getNumberOfPages();
		// String creator = info.getCreator();
		Calendar calendar = info.getCreationDate();
		System.out.println("Author : " + author);
		System.out.println(
				"Created : " + new SimpleDateFormat("dd-MM-yyyy hh:mm:ss aa").format(calendar.getTimeInMillis()));
		System.out.println("Total Pages : " + pages);
		if (pages > 0) {
			float width = doc.getPage(0).getMediaBox().getWidth();
			float height = doc.getPage(0).getMediaBox().getHeight();
			System.out.println("Page 0 size : " + width + " * " + height);
		} else {
			System.err.println("No pages.");
		}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	
	public void readPdf(String string) throws IllegalArgumentException, IllegalAccessException {

        PdfReader reader;

        try {

            reader = new PdfReader(string);

            // pageNumber = 1
            String textFromPage = PdfTextExtractor.getTextFromPage(reader, 1);

            //System.out.println(textFromPage);

            //com.itextpdf.text.Rectangle rec=reader.getPageSize(0).;
            System.out.println(reader.getPageSizeWithRotation(1).getHeight());
            System.out.println(reader.getPageSizeWithRotation(1).getWidth());
            System.out.println(PageSize.A4.getHeight());
            System.out.println(PageSize.A4.getWidth());
            if(reader.getPageSizeWithRotation(1).equals(PageSize.A4)){
            	System.out.println();
            }
            for(Field field:PageSize.class.getDeclaredFields()){
            	System.out.println(field.getName());
            }
            	
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
