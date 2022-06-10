package com.example.eindwerkJava2.api.geo.json_model;

import com.example.eindwerkJava2.model.State;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class State_json {
    private String state_name;
    public State convertToState(){
        State state = new State();
        state.setStateName(this.state_name);
        return state;
    }
}
