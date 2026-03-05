package com.workflow.projet.exception;

/**
 * Exception levée lors d'erreurs liées au token d'authentification
 */
public class TokenException extends RuntimeException {
    
    public enum TokenError {
        TOKEN_ABSENT("Token absent"),
        TOKEN_INVALIDE("Token invalide"),
        TOKEN_REVOQUE("Token révoqué"),
        TOKEN_EXPIRE("Token expiré");
        
        private final String message;
        
        TokenError(String message) {
            this.message = message;
        }
        
        public String getMessage() {
            return message;
        }
    }
    
    private final TokenError errorType;
    private final int httpStatus;
    
    public TokenException(TokenError errorType) {
        super(errorType.getMessage());
        this.errorType = errorType;
        this.httpStatus = (errorType == TokenError.TOKEN_ABSENT) ? 401 : 403;
    }
    
    public TokenError getErrorType() {
        return errorType;
    }
    
    public int getHttpStatus() {
        return httpStatus;
    }
}
