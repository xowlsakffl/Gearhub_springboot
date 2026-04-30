package com.ecommerce.project.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {

    private Long addressId;
    private String title;
    private String recipient;
    private String recipientNumber;
    private String addressName; // 전체 주소
    private String roadNameAddress; // 도로명 주소
    private String jibunAddress; // 지번 주소
    private String postalCode; // 우편번호
    private String buildingName; // 건물명
    private String region1DepthName; // 시/도
    private String region2DepthName; // 시/군/구
    private String region3DepthName; // 읍/면/동
}
