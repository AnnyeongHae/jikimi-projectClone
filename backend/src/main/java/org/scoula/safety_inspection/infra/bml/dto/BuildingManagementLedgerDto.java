package org.scoula.safety_inspection.infra.bml.dto;

import lombok.Getter;

@Getter
public class BuildingManagementLedgerDto {

    private Integer analysisNo;
    private final String resViolationStatus; // 위반건축물 여부
    private final String resContents; // 주용도

    public BuildingManagementLedgerDto(Integer analysisNo, String resViolationStatus, String resContents) {
        this.analysisNo = analysisNo;
        this.resViolationStatus = resViolationStatus;
        this.resContents = resContents;
    }

}
