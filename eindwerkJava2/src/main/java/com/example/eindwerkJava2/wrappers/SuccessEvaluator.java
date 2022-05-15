package com.example.eindwerkJava2.wrappers;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class SuccessEvaluator<T> extends SuccessObject {
    private T entity;
    private List<T> entities;
}
