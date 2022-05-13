package com.example.eindwerkJava2.wrappers;

import com.example.eindwerkJava2.model.Supplier;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class SupplierSuccess extends SuccessObject{
    private Supplier supplier;
    private List<Supplier> suppliers = new ArrayList<>();
}
