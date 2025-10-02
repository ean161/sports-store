package fcc.sportsstore.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Response {

    private int code;
    private String message;
    private Object data;

    /**
     * Constructor
     * @param code Response code (0: error, 1: success, 2: to redirect without message)
     * @param message Response message
     */
    public Response(int code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * Get response properties as map
     * @return Map of response
     */
    public Map<String, Object> pull() {
        Map<String, Object> res = new HashMap<>();
        res.put("code", code);
        res.put("message", message);
        res.put("data", data);

        return res;
    }
}
