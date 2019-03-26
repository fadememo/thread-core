package club.majoy.springbootredissession.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/session")
public class SessionController {

    /**
     *
     * @param request
     * @return
     */
    @GetMapping("/set")
    public Map<String,Object> setSSession(HttpServletRequest request){

        Map<String,Object> map = new HashMap<>();
        request.getSession().setAttribute("uri", request.getRequestedSessionId());
        map.put("uri", request.getRequestURL());
        return map;
    }

    /**
     *
     * @param request
     * @return
     */
    @GetMapping("/list")
    public Map<String,Object>getSession(HttpServletRequest request){
        Map<String,Object>map = new HashMap<>();
        map.put("sessionId", request.getSession().getId());
        map.put("uri",request.getSession().getAttribute("uri"));
        return map;
    }

}
