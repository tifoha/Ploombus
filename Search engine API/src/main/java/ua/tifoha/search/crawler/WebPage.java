package ua.tifoha.search.crawler;


/**
 * Created by Vitaly on 12.09.2016.
 */
public interface WebPage {
    Link getLink();

    boolean isRedirect();

    String getRedirectedToUrl();

    Status getStatus();

    byte[] getContentData();

    String getContentType();

    String getContentEncoding();

    String getContentCharset();

    String getLanguage();

    public enum Status {
      OK(200, "OK"),
      CREATED(201, "Created"),
      ACCEPTED(202, "Accepted"),
      NO_CONTENT(204, "No Content"),
      RESET_CONTENT(205, "Reset Content"),
      PARTIAL_CONTENT(206, "Partial Content"),
      MOVED_PERMANENTLY(301, "Moved Permanently"),
      FOUND(302, "Found"),
      SEE_OTHER(303, "See Other"),
      NOT_MODIFIED(304, "Not Modified"),
      USE_PROXY(305, "Use Proxy"),
      TEMPORARY_REDIRECT(307, "Temporary Redirect"),
      BAD_REQUEST(400, "Bad Request"),
      UNAUTHORIZED(401, "Unauthorized"),
      PAYMENT_REQUIRED(402, "Payment Required"),
      FORBIDDEN(403, "Forbidden"),
      NOT_FOUND(404, "Not Found"),
      METHOD_NOT_ALLOWED(405, "Method Not Allowed"),
      NOT_ACCEPTABLE(406, "Not Acceptable"),
      PROXY_AUTHENTICATION_REQUIRED(407, "Proxy Authentication Required"),
      REQUEST_TIMEOUT(408, "Request Timeout"),
      CONFLICT(409, "Conflict"),
      GONE(410, "Gone"),
      LENGTH_REQUIRED(411, "Length Required"),
      PRECONDITION_FAILED(412, "Precondition Failed"),
      REQUEST_ENTITY_TOO_LARGE(413, "Request Entity Too Large"),
      REQUEST_URI_TOO_LONG(414, "Request-URI Too Long"),
      UNSUPPORTED_MEDIA_TYPE(415, "Unsupported Media Type"),
      REQUESTED_RANGE_NOT_SATISFIABLE(416, "Requested Range Not Satisfiable"),
      EXPECTATION_FAILED(417, "Expectation Failed"),
      INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
      NOT_IMPLEMENTED(501, "Not Implemented"),
      BAD_GATEWAY(502, "Bad Gateway"),
      SERVICE_UNAVAILABLE(503, "Service Unavailable"),
      GATEWAY_TIMEOUT(504, "Gateway Timeout"),
      HTTP_VERSION_NOT_SUPPORTED(505, "HTTP Version Not Supported");

      private final int code;
      private final String reason;
      private final Family family;

      private Status(int statusCode, String reasonPhrase) {
        this.code = statusCode;
        this.reason = reasonPhrase;
        this.family = Status.Family.familyOf(statusCode);
      }

      public Family getFamily() {
        return this.family;
      }

      public int getStatusCode() {
        return this.code;
      }

      public String getReasonPhrase() {
        return this.toString();
      }

      public String toString() {
        return this.reason;
      }

      public static Status fromStatusCode(int statusCode) {
        Status[] arr$ = values();
        int len$ = arr$.length;

        for(int i$ = 0; i$ < len$; ++i$) {
          Status s = arr$[i$];
          if(s.code == statusCode) {
            return s;
          }
        }

        return null;
      }

      public enum Family {
        INFORMATIONAL,
        SUCCESSFUL,
        REDIRECTION,
        CLIENT_ERROR,
        SERVER_ERROR,
        OTHER;

        public static Family familyOf(int statusCode) {
          switch(statusCode / 100) {
            case 1:
              return INFORMATIONAL;
            case 2:
              return SUCCESSFUL;
            case 3:
              return REDIRECTION;
            case 4:
              return CLIENT_ERROR;
            case 5:
              return SERVER_ERROR;
            default:
              return OTHER;
          }
        }
      }
    }
}
