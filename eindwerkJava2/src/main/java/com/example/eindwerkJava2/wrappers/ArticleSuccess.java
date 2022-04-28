package com.example.eindwerkJava2.wrappers;

import com.example.eindwerkJava2.model.Article;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class ArticleSuccess {
    private Article article;
    private String message;
    private Boolean isSuccessfull;

    public void extendMessage(String message){
        if(this.message==null){
            setMessage(message);
        }else{
            this.message+="\n "+message;
        }

    }
}
