package com.dantefung.debug;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author fenghaolin
 * @date 2024/07/25 17/49
 * @since JDK1.8
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DebugInfo implements Serializable {

    private static final long serialVersionUID = 1557045781075006327L;

    private Map<String, Object> variables = new HashMap<>();
    private List<String> messages = new ArrayList<>();

    public void setVariable(String name, Object value) {
        variables.put(name, value);
    }

    public Object getVariable(String name) {
        return variables.get(name);
    }

    public void addMessage(String message) {
        messages.add(message);
    }

    public List<String> getMessages() {
        return messages;
    }
}
