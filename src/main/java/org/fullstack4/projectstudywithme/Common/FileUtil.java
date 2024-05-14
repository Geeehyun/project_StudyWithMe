//package org.fullstack4.projectstudywithme.Common;
//
//import lombok.extern.log4j.Log4j2;
//import org.fullstack4.genius.dto.FileDTO;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.multipart.MultipartHttpServletRequest;
//
//import java.io.File;
//import java.util.*;
//
//@Log4j2
//public class FileUtil {
//
//    public static Map<String, String> FileUpload(FileDTO fileDTO) {
//        Map<String, String> map = new HashMap<>();
//        String directory = fileDTO.getUploadFolder();
//        MultipartFile file = fileDTO.getFile();
//
//        String result = "fail";
//        String uploadFolder = directory;
//        String fileRealName = file.getOriginalFilename(); //원래 파일의 이름
//        long size = file.getSize();
//        String fileExt = fileRealName.substring(fileRealName.lastIndexOf("."), fileRealName.length()); // 확장자명
//        //엑셀.파.일xxx.xls --> 제일 마지막 인덱스의 . 에서부터 파일이름 끝에를 파싱
//
//        log.info("============================");
//        log.info("uploadFolder : " + uploadFolder);
//        log.info("fileRealName : " + fileRealName);
//        log.info("size : " + size);
//        log.info("fileExt : " + fileExt);
//
//
//        //새로운 파일명 생성
//        UUID uuid = UUID.randomUUID();
//        String[] uuids = uuid.toString().split("-");
//        String newName = uuids[0];
//
//        log.info("uuid : " + uuid);
//        log.info("uuids : " + uuids);
//        log.info("newName : " + newName);
//
//
//        File saveFile = new File(uploadFolder + "\\" + newName + fileExt);
//
//        try {
//            file.transferTo(saveFile);
//            result = "success";
//        } catch (IllegalStateException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        map.put("orgName", fileRealName);
//        map.put("newName", newName+fileExt);
//        map.put("result", result);
//
//        log.info("============================");
//        return map;
//    }
//
//    public static List<Map<String, String>> MultiFileUpload(List<MultipartFile> list, MultipartHttpServletRequest files,String directory) {
//        List<Map<String, String>> resultList = new ArrayList<>();
//        Map<String, String> map = new HashMap<>();
//
//        String uploadFolder = directory;
//
//        list = files.getFiles("files");
//        for(MultipartFile file : list) {
//            String result = "fail";
//
//            String fileRealName = file.getOriginalFilename(); //원래 파일의 이름
//            long size = file.getSize();
//            String fileExt = fileRealName.substring(fileRealName.lastIndexOf("."), fileRealName.length()); // 확장자명
//            //엑셀.파.일xxx.xls --> 제일 마지막 인덱스의 . 에서부터 파일이름 끝에를 파싱
//
//            log.info("============================");
//            log.info("uploadFolder : " + uploadFolder);
//            log.info("fileRealName : " + fileRealName);
//            log.info("size : " + size);
//            log.info("fileExt : " + fileExt);
//
//            //새로운 파일명 생성
//            UUID uuid = UUID.randomUUID();
//            String[] uuids = uuid.toString().split("-");
//            String newName = uuids[0];
//
//            log.info("uuid : " + uuid);
//            log.info("uuids : " + uuids);
//            log.info("newName : " + newName);
//
//            File saveFile = new File(uploadFolder + "\\" + newName + fileExt);
//
//            try {
//                file.transferTo(saveFile);
//                result = "success";
//            } catch (IllegalStateException e) {
//                e.printStackTrace();
//            }
//            catch(Exception e) {
//                e.printStackTrace();
//            }
//            map.put("orgName", fileRealName);
//            map.put("newName", newName+fileExt);
//            map.put("result", result);
//            resultList.add(map);
//            log.info("============================");
//        }
//        return resultList;
//    }
//}
