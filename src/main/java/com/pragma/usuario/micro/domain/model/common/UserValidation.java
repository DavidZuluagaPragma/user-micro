package com.pragma.usuario.micro.domain.model.common;

import com.pragma.usuario.micro.domain.model.usuario.User;
import com.pragma.usuario.micro.infrastructure.exceptions.BusinessException;
import reactor.core.publisher.Mono;

public final class UserValidation {

    public static Mono<User> userValidation(User user){
        if ( user.getEmail() == null || !Utils.isEmail(user.getEmail())){
            return Mono.error(new BusinessException(BusinessException.Type.INVALID_EMAIL));
        }
        if ( user.getPhoneNumber() == null || !Utils.isValidPhoneNumer(user.getPhoneNumber())){
            return Mono.error(new BusinessException(BusinessException.Type.INVALID_PHONE_NUMBER));
        }
        if ( user.getNumberDocument() == null || !Utils.isValidDocument(user.getNumberDocument())){
            return Mono.error(new BusinessException(BusinessException.Type.INVALID_DOCUMENT_NUMBER));
        }
        if ( user.getDateOfBirth() == null || !Utils.isValidYear(user.getDateOfBirth())){
            return Mono.error(new BusinessException(BusinessException.Type.INVALID_AGE));
        }
        return Mono.just(user);
    }

}
