package com.thoughtworks.restful.restful.controller.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "authentication forbidden")
public class ForbiddenException extends Exception {

}