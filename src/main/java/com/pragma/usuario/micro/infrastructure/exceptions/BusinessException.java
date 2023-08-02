package com.pragma.usuario.micro.infrastructure.exceptions;

import java.util.function.Supplier;

public class BusinessException extends ApplicationException{

    private static final long serialVersionUID = 1L;

    public enum Type {
        ERROR_DATABASE_DATA_NOT_FOUND("Error in database, data not found"),
        INVALID_PHONE_NUMBER("Invalid phone number"),
        INVALID_DOCUMENT_NUMBER("Document number must be numeric"),
        INVALID_AGE("Must be of legal age"),
        INVALID_EMAIL("Invalid email");

        private final String message;

        public String getMessage() {
            return message;
        }

        public BusinessException build() {
            return new BusinessException(this);
        }

        public Supplier<Throwable> defer() {
            return () -> new BusinessException(this);
        }

        Type(String message) {
            this.message = message;
        }

    }

    private final Type type;

    public BusinessException(Type type){
        super(type.message);
        this.type = type;
    }

    public BusinessException(Type type,String menssage){
        super(menssage);
        this.type = type;
    }

    @Override
    public String getCode(){
        return type.name();
    }


}
