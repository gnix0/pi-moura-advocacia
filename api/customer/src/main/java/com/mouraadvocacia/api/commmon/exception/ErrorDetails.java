package com.mouraadvocacia.api.commmon.exception;

import java.util.Date;

public record ErrorDetails(Date timestamp, String message, String details) {
}
