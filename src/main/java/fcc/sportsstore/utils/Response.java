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

    public Response(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public Map<String, Object> pull() {
        Map<String, Object> res = new HashMap<>();
        res.put("code", code);
        res.put("message", message);
        res.put("data", data);

        return res;
    }
}
