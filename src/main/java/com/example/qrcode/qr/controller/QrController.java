package com.example.qrcode.qr.controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RestController
public class QrController {

    @GetMapping("/qrcode/book/{storeNo}")
    public ResponseEntity<byte[]> qrToBook(@PathVariable String  storeNo) throws WriterException, IOException {

//        가게에 예약확인 qr을 구현하려고 하는데 지금 여기서는 내 깃허브 페이지로 들어갈 수 있도록 할 것이다.
//        그래서 storeNo는 원래는 int이지만 지금은 https://github.com/Passbob로 이동하기 위해 String으로 두었다.

//        qr 정보
        int width = 120;
        int height = 120;
        String url = "https://github.com/"+storeNo;

//        여기서 qr에 대한 데이터를 만들 것이다.
//        코드는 랜덤하고 겹치지 않도록 만들기
//        유저 넘버 저장 (auth에 담겨있는)
//        가게 정보 저장 (PathVariable)
//        그 다음 코드를 찍게되면 넘어가는 사이트가 가게 확인 사이트
//        url에 코드 가지고 넘어가서 해당 정보를 조회할 수 있도록 만들자.
//        확인이 완료되면 qr코드는 제거



//        QR Code - BitMatrix : qr code 정보 생성
        BitMatrix encode = new MultiFormatWriter()
                .encode(url, BarcodeFormat.QR_CODE, width, height);

        try{
//            output Stream으로 구현
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

//            Bitmatrix, file.format, outputStream
            MatrixToImageWriter.writeToStream(encode, "png", baos);

            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .body(baos.toByteArray());
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }
}
