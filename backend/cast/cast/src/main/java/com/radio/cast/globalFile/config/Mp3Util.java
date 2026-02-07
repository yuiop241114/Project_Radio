package com.radio.cast.globalFile.config;

import java.io.File;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.mpatric.mp3agic.Mp3File;
import com.radio.cast.globalFile.dto.Mp3SaveResponse;

import org.apache.tika.parser.ParseContext;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class Mp3Util {
  @Value("${file.upload-path}")
  private String uploadRoot;

  /**
   * mp3 재생 시간 추출
   * @param file
   * @return
   */
  public static long getDurationInSeconds(File file) {
    // try (FileInputStream input = new FileInputStream(file)) {
    //   BodyContentHandler handler = new BodyContentHandler();
    //   Metadata metadata = new Metadata();
    //   // System.out.println("메타 데이터 : " + metadata.get("xmpDM:duration"));
    //   // System.out.println("메타 데이터 : " + metadata.get("samplerate"));
    //   // System.out.println("메타 데이터 : " + metadata.get("channels"));
    //   ParseContext context = new ParseContext();

    //   Mp3Parser parser = new Mp3Parser();
    //   parser.parse(input, handler, metadata, context);

    //   String duration = metadata.get("xmpDM:duration"); // milliseconds

    //   if (duration == null) return 0L;

    //   double millis = Double.parseDouble(duration);
    //   return (long) (millis / 1000);

    // } catch (Exception e) {
    //     e.printStackTrace();
    //     return 0L;
    // }
    try {
            Mp3File mp3 = new Mp3File(file);
            return mp3.getLengthInSeconds();
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
  }

  /**
   * mp3 파일 저장 및 경로 반환
   * @param file
   * @param channelId
   * @return
   * @throws IOException
   */
  public Mp3SaveResponse saveMp3AndGetPath(MultipartFile file, Long channelId) throws IOException{
     Path uploadPath = Paths.get(uploadRoot, "audio", "channel_" + channelId)
                .toAbsolutePath()
                .normalize();

        // 폴더 생성
        Files.createDirectories(uploadPath);

        String filename = file.getOriginalFilename();
        Path filePath = uploadPath.resolve(filename);

        // 저장
        file.transferTo(filePath.toFile());
        String audioUrl = "/audio/channel_" + channelId + "/" + filename;

        // DB / 프론트로 내려줄 URL
        return new Mp3SaveResponse(audioUrl, filePath.toFile());
  }
}
