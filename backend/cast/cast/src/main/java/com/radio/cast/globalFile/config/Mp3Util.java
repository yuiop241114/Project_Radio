package com.radio.cast.globalFile.config;

import java.io.File;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.mpatric.mp3agic.Mp3File;

import org.apache.tika.parser.ParseContext;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class Mp3Util {
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
  public String saveMp3AndGetPath(MultipartFile file, Long channelId) throws IOException{
    // String baseDir = "src/main/resources/static/audio/channel_" + channelId;
    // File dir = new File(baseDir);
    // if (!dir.exists()) dir.mkdirs();

    // String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
    // File savedFile = new File(dir, filename);

    // file.transferTo(savedFile);

    // return "/audio/channel_" + channelId + "/" + filename;

    // 1. 프로젝트 루트 경로를 기준으로 절대 경로 생성
    String rootPath = System.getProperty("user.dir"); 
    Path uploadPath = Paths.get(rootPath, "src", "main", "resources", "static", "audio", "channel_" + channelId)
                           .toAbsolutePath().normalize();

    // 2. 폴더 생성 (이미 있으면 무시, 없으면 상위 폴더까지 싹 다 생성)
    Files.createDirectories(uploadPath);

    // 3. 파일명 생성
    // String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
    // File savedFile = uploadPath.resolve(filename).toFile();

    File savedFile = uploadPath.resolve(file.getOriginalFilename()).toFile();

    // 4. 저장
    file.transferTo(savedFile);

    return "/audio/channel_" + channelId + "/" + file.getOriginalFilename();
  }
}
