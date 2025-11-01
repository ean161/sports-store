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

    private String message;

    private Object data;

    public Response(String message) {
        this.message = message;
    }

    public Response(Object data) {
        this.message = message;
        this.data = data;
    }

    public Map<String, Object> build() {
        Map<String, Object> res = new HashMap<>();
        res.put("message", message);
        res.put("data", data);

        return res;
    }
}
