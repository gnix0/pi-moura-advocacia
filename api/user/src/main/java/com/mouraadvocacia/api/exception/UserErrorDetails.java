package com.mouraadvocacia.api.exception;

import java.util.Date;

public record UserErrorDetails(Date timestamp, String message, String details) {
}
