package com.example.filedemo;

import java.awt.image.BufferedImage;
import java.nio.file.Paths;

import com.example.filedemo.utils.UploadUtil;

public class UploadUtilTest {

	/*public static void main(String[] args) {
		UploadUtil util=new UploadUtil();
		BufferedImage image=util.getBufferedImage("C:\\Users\\bshaik10\\Desktop\\bhaji_shaik_photo.jpg");
		System.out.println(util.meanValueRedGreenBlue(image));
		System.out.println(util.meanValueRGB(image));
	}*/
	
	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException {
		UploadUtil util=new UploadUtil();
		//util.getPdfProperties(Paths.get("C:\\Users\\bshaik10\\Desktop\\OpTransactionHistory05-10-2018.pdf"));
		util.readPdf("C:\\Users\\bshaik10\\Desktop\\Loan Documents\\statement.pdf");
	}

}
