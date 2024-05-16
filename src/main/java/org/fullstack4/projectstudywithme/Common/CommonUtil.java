package org.fullstack4.projectstudywithme.Common;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import jakarta.servlet.http.HttpServletRequest;

import java.security.SecureRandom;
import java.util.Map;
import java.util.Set;

@Log4j2
public class CommonUtil {

    public static String parseString(Object obj) {
        return (obj != null) ? (String)obj : "";
    }

    public static boolean nullCheck(String str) {
        if (str.equals("")){
            return false;
        } else if (str.trim().equals("")) {
            return false;
        }
        return true;
    }

    public static int parseInt(String str) {
        int result = 0;
        try {
            str = parseString(str);
            result = Integer.parseInt(str);
        } catch (NumberFormatException e) {
            System.out.println("숫자가 아닌 값 들어옴.");
        } catch (Exception e) {
            System.out.println("숫자가 아닌 값 들어옴222.");
        }
        return result;
    }
    public static String getUploadFolder(HttpServletRequest request) {
        // target 인자에는 upload폴더 하위 폴더 명을 넣으시오
//        String realPath = request.getServletContext().getRealPath("");
//        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> realPath : {}", realPath);
//        return realPath.substring(0,realPath.indexOf("build")) + "src\\main\\resources\\static\\upload";
        return "D:\\projectStudyWithMe\\project\\src\\main\\resources\\static\\upload";
    }

    public static String parsingURI(String uri) {
        String myUri = "";
        myUri = uri.substring(uri.indexOf("/") + 1);
        myUri = myUri.substring(myUri.indexOf("/"));
        return myUri;
    }

    public static String comma(String str) {
        return String.format("%,d", parseInt(str));
    }

    private static final char[] rndAllCharacters = new char[]{
            //number
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            //uppercase
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            //lowercase
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            //special symbols
            '@', '$', '!', '%', '*', '?', '&'
    };

    public static String getRandomPassword(int length) {
        SecureRandom random = new SecureRandom();
        StringBuilder stringBuilder = new StringBuilder();

        int rndAllCharactersLength = rndAllCharacters.length;
        for (int i = 0; i < length; i++) {
            stringBuilder.append(rndAllCharacters[random.nextInt(rndAllCharactersLength)]);
        }

        return stringBuilder.toString();
    }
}
