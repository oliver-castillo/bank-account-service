package com.bank.accountservice.model.document.parents;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Getter
@Setter
public abstract class BaseDocument {
    @Id
    private String id;

    private LocalDateTime creationDate = LocalDateTime.now();

    private LocalDateTime lastUpdateDate = null;

    private boolean isDeleted = false;
}
