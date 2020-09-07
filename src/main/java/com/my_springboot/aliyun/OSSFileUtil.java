package com.my_springboot.aliyun;

import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.DeleteObjectsRequest;
import com.aliyun.oss.model.DeleteObjectsResult;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import com.aliyun.oss.model.GetObjectRequest;
import com.my_springboot.util.IdGenerator;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author JiHC
 * @date 2020/7/28
 */
public class OSSFileUtil {

  // 创建OSSClient实例。
//  private OSS ossClient = new OSSClientBuilder()
//      .build(AliyunConstant.ENDPOINT, AliyunConstant.ACCESS_KEY_ID,
//          AliyunConstant.ACCESS_KEY_SECRET);

  /**
   * 文件上传[阿里云]
   *
   * @param file 文件
   */
  public static String putObject(MultipartFile file) {
    String fileName = file.getOriginalFilename();
//    String fileName = IdGenerator.uuid() + ".jpg";
    // 创建OSSClient实例。
    OSS ossClient = new OSSClientBuilder()
        .build(AliyunConstant.ENDPOINT, AliyunConstant.ACCESS_KEY_ID,
            AliyunConstant.ACCESS_KEY_SECRET);
    try {
      InputStream input = file.getInputStream();
      // 上传文件流
      ossClient
          .putObject(AliyunConstant.BUCKET_NAME, AliyunConstant.FILE_PATH_UPLOAD + fileName, input);
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      // 关闭OSSClient。
      ossClient.shutdown();
      return fileName;
    }
  }

  /**
   * 多文件上传[阿里云]
   *
   * @param fileList 文件列表
   */
  public static List<String> putObjects(List<MultipartFile> fileList) {

    List<String> fileNames = new ArrayList<>();
    // 创建OSSClient实例。
    OSS ossClient = new OSSClientBuilder()
        .build(AliyunConstant.ENDPOINT, AliyunConstant.ACCESS_KEY_ID,
            AliyunConstant.ACCESS_KEY_SECRET);
    try {
      for (MultipartFile file : fileList) {
        String fileName = IdGenerator.uuid() + ".jpg";
        fileNames.add(fileName);
        InputStream input = file.getInputStream();
        // 上传文件流
        ossClient.putObject(AliyunConstant.BUCKET_NAME, AliyunConstant.FILE_PATH_UPLOAD + fileName,
            input);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    // 关闭OSSClient。
    ossClient.shutdown();
    return fileNames;
  }

  /**
   * 获得url链接
   */
  public static String getUrl(String fileName) {
    String objectName = AliyunConstant.FILE_PATH_UPLOAD + fileName;
    // 创建OSSClient实例。
    OSS ossClient = new OSSClientBuilder()
        .build(AliyunConstant.ENDPOINT, AliyunConstant.ACCESS_KEY_ID,
            AliyunConstant.ACCESS_KEY_SECRET);
    // 设置权限(公开读)
//    ossClient.setBucketAcl(AliyunConstant.BUCKET_NAME, CannedAccessControlList.PublicRead);
    // 设置图片处理样式。
//        String style = "image/resize,m_fixed,w_100,h_100/rotate,90";
    Date expiration = new Date(new Date().getTime() + 3600l * 1000 * 24 * 365 * 1/*00*/);
    GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest(AliyunConstant.BUCKET_NAME,
        objectName,
        HttpMethod.GET);
    req.setExpiration(expiration);
//        req.setProcess(style);
    URL signedUrl = ossClient.generatePresignedUrl(req);
    // 关闭OSSClient。
    ossClient.shutdown();
    if (signedUrl != null) {
      return signedUrl.toString();
    }
    return null;
  }

  /**
   * 删除文件[阿里云]
   *
   * @param fileName 文件名称
   */
  public static void deleteObject(String fileName) {
    // 创建OSSClient实例。
    OSS ossClient = new OSSClientBuilder()
        .build(AliyunConstant.ENDPOINT, AliyunConstant.ACCESS_KEY_ID,
            AliyunConstant.ACCESS_KEY_SECRET);
    //<yourObjectName>表示从OSS下载文件时需要指定包含文件后缀在内的完整路径，例如abc/efg/123.jpg。
    String objectName = AliyunConstant.FILE_PATH_UPLOAD + fileName;
    // 删除文件。如需删除文件夹，请将ObjectName设置为对应的文件夹名称。
    // 如果文件夹非空，则需要将文件夹下的所有object删除后才能删除该文件夹。
    ossClient.deleteObject(AliyunConstant.BUCKET_NAME, objectName);
    // 关闭OSSClient。
    ossClient.shutdown();
  }

  /**
   * 删除多个文件[阿里云]
   *
   * @param fileName 文件名称列表
   */
  public static void deletedObjects(List<String> fileName) {
    // 创建OSSClient实例。
    OSS ossClient = new OSSClientBuilder()
        .build(AliyunConstant.ENDPOINT, AliyunConstant.ACCESS_KEY_ID,
            AliyunConstant.ACCESS_KEY_SECRET);
    DeleteObjectsResult deleteObjectsResult = ossClient
        .deleteObjects(new DeleteObjectsRequest(AliyunConstant.BUCKET_NAME).withKeys(fileName));
    List<String> deletedObjects = deleteObjectsResult.getDeletedObjects();
    // 关闭OSSClient。
    ossClient.shutdown();
  }
}
