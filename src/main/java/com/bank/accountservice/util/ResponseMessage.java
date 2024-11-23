package com.bank.accountservice.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ResponseMessage {
    CREATED_SUCCESSFULLY("Se ha creado el registro correctamente"),
    UPDATED_SUCCESSFULLY("Se ha actualizado el registro correctamente"),
    DELETED_SUCCESSFULLY("Se ha eliminado el registro correctamente"),

    INTERNAL_SERVER_ERROR("Error interno del servidor"),
    DUPLICATE_KEY("Uno o más datos enviados ya existen en la base de datos"),

    NOT_FOUND("El registro no existe"),
    ARGUMENT_NOT_VALID("Uno o más argumentos no son válidos"),
    REQUIREMENT_NOT_MET("Uno o más requisitos no se cumplen"),

    CONSTRAINT_VIOLATION("Uno o más campos no cumplen con los requisitos"),
    ALREADY_EXISTS("Uno o más registros ya existen en la base de datos");

    private final String message;
}
