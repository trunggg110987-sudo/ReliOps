package com.smarthomerentalplatform.exception.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.Optional;

@Getter
@AllArgsConstructor
public enum DomainCode implements ErrorCode {
    SUCCESS("SHR-000", "Thành công."),
    REQUEST_TIMEOUT("SHR-408", "Yêu cầu xử lý quá thời gian."),
    EXTERNAL_SERVICE_SERVER_ERROR("SHR-502", "Dịch vụ phụ thuộc đang gặp sự cố."),
    INTERNAL_SERVICE_ERROR("SHR-500", "Đã xảy ra lỗi hệ thống."),

    INVALID_CREDENTIALS("SHR-401", "Email hoặc Password không hợp lệ."),
    ACCOUNT_NOT_ACTIVE("SHR-403", "Tài khoản chưa được kích hoạt."),
    TOKEN_EXPIRED("SHR-402", "Token hết hạn."),
    INVALID_TOKEN("SHR-404", "Token không hợp lệ."),
    EMAIL_ALREADY_EXISTS("SHR-409", "Email đã tồn tại."),
    ACCESS_DENIED("SHR-405", "Bạn không có quyền thực hiện hành động này."),
    UNAUTHORIZED("SHR-406", "Chưa xác thực."),
    USER_NOT_FOUND("SHR-503", "Không tìm thấy thông tin người dùng");

    private final String code;
    private final String message;

    public static Optional<DomainCode> findByCode(String code) {
        if (code == null || code.isBlank()) {
            return Optional.empty();
        }

        return Arrays.stream(values())
                .filter(domainCode -> domainCode.code.equals(code))
                .findFirst();
    }
}
