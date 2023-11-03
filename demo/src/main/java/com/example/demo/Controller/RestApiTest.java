package com.example.demo.Controller;

import com.example.demo.entity.RestResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
public class RestApiTest {

    private static final Logger console = LoggerFactory.getLogger(RestApiTest.class);

    /**
     * REST API : GET
     * 아래 두가지 방식을 POSTMAN에서 호출하면 결과값 보여주는 방식에 차이점이 없지만, 웹브라우저에서 호출해보면 다르게 보임
     * @return
     */
    @GetMapping(value = "/v1/get/01")
    public String GetRestApi01() {
        return "Get : 01";
    }

    @RequestMapping(value = "/v1/get/02", method = {RequestMethod.GET}, produces = {"application/json; charset=utf8", "text/plain"})
    public String GetRestApi02() { return  "Get : 02"; }

    /**
     * REST API : POST
     * @RequestParam 을 사용하는 방식으로 구현하면 안되겠다. Postman으로 테스트 할때 body 에 넣어서 테스트 안됨
     * url에 붙여서 보내는 Params 에 넣으면 전달되어 테스트 가능함 (진정한 POST 방식이라고 볼 수 없음)
     * @return
     */
    @PostMapping(value = "/v1/post/01")
    public @ResponseBody String PostRestApi01() {
        System.out.println("/v1/post/01");
        return "Post : 01";
    }

    /**
     * url 을 v1 으로 명시해서 사용
     * @param request
     * @param regId
     * @param nameStr
     * @return
     */
    @RequestMapping(value = "/v1/post/02", method = {RequestMethod.POST}, produces = {"application/json; charset=utf8", "text/plain"})
    public ResponseEntity<?> PostRestApi02(
            HttpServletRequest request
//            , @PathVariable(value="version") String version
            , @RequestParam(required=false, value="regId") String regId
            , @RequestParam(required=false, value="nameStr") String nameStr
    ) {
        System.out.println("/v1/post/02");
//        System.out.println("version: " + version);

        Map<String, Object> rtnMap = new HashMap<>();
        rtnMap.put("regId", regId);
        rtnMap.put("nameStr", nameStr);
        rtnMap.put("resultStr", "success");

        return new ResponseEntity<>(rtnMap, HttpStatus.OK);
    }

    /**
     * ResponseBody, @RequestBody 사용하지 않음
     * HttpServletRequest, {RequestMethod.POST} 사용
     * url 을 v{version} 으로 받아서 사용
     * @param request
     * @param version
     * @param regId
     * @param nameStr
     * @return
     */
    @RequestMapping(value = "/v{version}/post/04", method = {RequestMethod.POST}, produces = {"application/json; charset=utf8", "text/plain"})
    public ResponseEntity<?> PostRestApi02(
            HttpServletRequest request
            , @PathVariable(value="version") String version
            , @RequestParam(required=false, value="regId") String regId
            , @RequestParam(required=false, value="nameStr") String nameStr
    ) {
        System.out.println("/v1/post/04");

        Map<String, Object> rtnMap = new HashMap<>();
        if (version.equals("1")) {
            rtnMap.put("version", version);
            rtnMap.put("regId", regId);
            rtnMap.put("nameStr", nameStr);
        } else {
            rtnMap.put("resultSt", version);
            rtnMap.put("regId", regId);
            rtnMap.put("nameStr", "exia");
            rtnMap.put("resultStr", "success");
        }

        return new ResponseEntity<>(rtnMap, HttpStatus.OK);
    }

    /**
     * ResponseBody, @RequestBody 사용
     * @param params
     * @return
     */
    @PostMapping(value = "/v1/post/03")
    public @ResponseBody ResponseEntity<?> PostRestApi03(@RequestBody Map<String, Object> params) {
        System.out.println("/v1/post/03");

        Map<String, Object> rtnMap = new HashMap<>();
        rtnMap.put("regId", params.get("regId"));
        rtnMap.put("nameStr", params.get("nameStr"));
        rtnMap.put("resultStr", "success");

        return new ResponseEntity<>(rtnMap, HttpStatus.OK);
    }

    /**
     * ResponseBody, @RequestBody, @PathVariable 사용
     * {version} 사용하는 방법
     * @param params
     * @param version
     * @return
     */
    @PostMapping(value = "/v{version}/post/05")
    public @ResponseBody ResponseEntity<?> PostRestApi05(
            @RequestBody Map<String, Object> params
            , @PathVariable(value="version") String version
    ) {
        System.out.println("/v1/post/05");

        Map<String, Object> rtnMap = new HashMap<>();

        if (version.equals("1")) {
            rtnMap.put("regId", params.get("regId"));
            rtnMap.put("nameStr", params.get("nameStr"));
            rtnMap.put("resultStr", "success");
        } else {
            rtnMap.put("regId", params.get("regId"));
            rtnMap.put("nameStr", "exia");
            rtnMap.put("resultStr", "success");
        }

        return new ResponseEntity<>(rtnMap, HttpStatus.OK);
    }
}
