package com.xnx3.j2ee.service.impl;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import com.xnx3.QRCodeUtil;
import com.xnx3.j2ee.service.QRCodeService;
import com.xnx3.media.ImageUtil;

public class QRCodeServiceImpl implements QRCodeService {

	@Override
	public void showQRCodeForPage(String content, HttpServletResponse response) {
		BufferedImage bi = QRCodeUtil.createQRCoder(content);
		try {
			response.getOutputStream().write(ImageUtil.bufferedImageToByte(bi, "jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
