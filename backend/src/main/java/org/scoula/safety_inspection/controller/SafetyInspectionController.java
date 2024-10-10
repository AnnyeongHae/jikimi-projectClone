package org.scoula.safety_inspection.controller;

import lombok.RequiredArgsConstructor;
import org.scoula.report.domain.ReportDTO;
import org.scoula.report.service.ReportService;
import org.scoula.safety_inspection.infra.analysis.service.AnalysisService;
import org.scoula.safety_inspection.infra.bml.service.BuildingManagementLedgerGeneralService;
import org.scoula.safety_inspection.infra.bml.service.BuildingManagementLedgerMultiService;
import org.scoula.safety_inspection.infra.cors.service.CopyOfRegisterGeneralService;
import org.scoula.safety_inspection.infra.cors.service.CopyOfRegisterMultiService;
import org.scoula.safety_inspection.service.ExtractUnicodeService;
import org.scoula.safety_inspection.service.SafetyInspectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/safety-inspection")
@RequiredArgsConstructor
public class SafetyInspectionController {
    final private ExtractUnicodeService extractUnicodeService;
    final private SafetyInspectionService safetyInspectionService;

    // 유니크 코드 관련
    @PostMapping("/address")
    public ResponseEntity<List<Map<String, String>>> handleAccess(@RequestBody Map<String, Object> payload) {
        try {

            for (Map.Entry<String, Object> entry : payload.entrySet()) {
                String key = entry.getKey(); // 키
                Object value = entry.getValue(); // 값
                System.out.println("key = " + key);
                System.out.println("value = " + value);
                System.out.println("type= " + getType(value));
            }

            List<Map<String,String>> response = extractUnicodeService.getUniqueCode(payload);
            System.out.println("response = " + response);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(List.of(Map.of("error", "An error occurred while processing the request.")), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    // 등기부 등본 관련
    @PostMapping("/cors")
    public ResponseEntity<String> handleUniqueCode(@RequestBody Map<String, Object> payload) {
        try {
            String reportNo = safetyInspectionService.processSafetyInspection(payload);
            return ResponseEntity.ok(reportNo);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("실패: " + e.getMessage());
        }
    }

    public static String getType(Object obj) {
        if (obj == null) {
            return "null";
        }
        return obj.getClass().getSimpleName(); // 클래스 이름 반환
    }
}