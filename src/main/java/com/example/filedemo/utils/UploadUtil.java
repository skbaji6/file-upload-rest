package com.example.filedemo.utils;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.example.filedemo.payload.ImageProp;
import com.example.filedemo.payload.PdfProp;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

@Component
public class UploadUtil {
	
	public ImageProp getImageDetails(BufferedImage image) {
		double redSum = 0.0;
		double greenSum = 0.0;
		double blueSum = 0.0;
		double rgbSum = 0.0;

		for (int y = 0; y < image.getHeight(); ++y) {
			for (int x = 0; x < image.getWidth(); ++x) {
				Color r = new Color(image.getRGB(x, y));
				redSum += r.getRed();
				greenSum += r.getGreen();
				blueSum += r.getBlue();
				rgbSum += r.getRGB();
			}
		}
		ImageProp imageProp=new ImageProp();
		imageProp.setMeanOfRed(redSum / (image.getWidth() * image.getHeight()));
		imageProp.setMeanOfGreen(greenSum / (image.getWidth() * image.getHeight()));
		imageProp.setMeanOfBlue(blueSum / (image.getWidth() * image.getHeight()));
		imageProp.setMeanOfRGB(rgbSum / (image.getWidth() * image.getHeight()));
		imageProp.setImageResolution(String.valueOf(image.getHeight())+" X "+String.valueOf(image.getWidth()));
		return imageProp;
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
				|| !(file.getContentType().toLowerCase().equals("application/pdf")
						|| file.getContentType().toLowerCase().equals("image/jpg")
						|| file.getContentType().toLowerCase().equals("image/jpeg")
						|| file.getContentType().toLowerCase().equals("image/png"))) {
			return true;
		}

		return false;
	}
	
	public PdfProp getPdfDetails(String path) {
		PdfProp pdfProp =new PdfProp();
		try{
			 PdfReader reader = new PdfReader(path);
			 List<String> pageTexts = IntStream.rangeClosed(1, reader.getNumberOfPages()).mapToObj(i-> getpdfExtraction(reader,i)).collect(Collectors.toList());
	            
	            List<String> lines=pageTexts.stream().flatMap(page -> Arrays.stream(page.trim().split("\n"))).collect(Collectors.toList());
	            Long wordCount=lines.stream().flatMap(line -> Arrays.stream(line.trim().split(" ")))
	            	    .map(word -> word.replaceAll("[^a-zA-Z]", "").toLowerCase().trim())
	            	    .filter(word -> !word.isEmpty()).count();
			pdfProp.setTotalNoOfLines(String.valueOf(lines.size()));
			pdfProp.setTotalNoOfWords(String.valueOf(wordCount));
			Rectangle pdfPageRect=reader.getPageSizeWithRotation(1);
			for(Field field:PageSize.class.getDeclaredFields()){
            	Rectangle paper=((Rectangle)field.get(null));
            	if(paper.getHeight()==pdfPageRect.getHeight() && paper.getWidth()==pdfPageRect.getWidth()){
            		pdfProp.setPaperSize(field.getName());
            		break;
            	}
            }
			if(StringUtils.isEmpty(pdfProp.getPaperSize())){
				pdfProp.setPaperSize(pdfPageRect.getHeight() +" X "+pdfPageRect.getWidth());
			}
			reader.close();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return pdfProp;
	}
	
	private String getpdfExtraction(PdfReader reader,int i) {
		try {
			return PdfTextExtractor.getTextFromPage(reader,i);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
