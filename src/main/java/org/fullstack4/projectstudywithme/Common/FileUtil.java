package org.fullstack4.projectstudywithme.Common;

import lombok.extern.log4j.Log4j2;
import org.fullstack4.projectstudywithme.dto.FileDTO;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.util.*;

@Log4j2
public class FileUtil {

    public static Map<String, String> FileUpload(FileDTO fileDTO) {
        Map<String, String> map = new HashMap<>();
        String directory = fileDTO.getUploadFolder();
        MultipartFile file = fileDTO.getFile();

        String result = "fail";
        String uploadFolder = directory;
        String fileRealName = file.getOriginalFilename(); //원래 파일의 이름
        long size = file.getSize();
        String fileExt = fileRealName.substring(fileRealName.lastIndexOf("."), fileRealName.length()); // 확장자명
        //엑셀.파.일xxx.xls --> 제일 마지막 인덱스의 . 에서부터 파일이름 끝에를 파싱

        //새로운 파일명 생성
        UUID uuid = UUID.randomUUID();
        String[] uuids = uuid.toString().split("-");
        String newName = uuids[0];

        File saveFile = new File(uploadFolder + "\\" + newName + fileExt);

        try {
            file.transferTo(saveFile);
            result = "success";
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        map.put("orgName", fileRealName);
        map.put("newName", newName+fileExt);
        map.put("result", result);

        return map;
    }
}
