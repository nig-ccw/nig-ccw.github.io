package com.vcc.cem.api.tool.dto.geocode;

import com.vcc.cem.api.tool.dto.Result;
import lombok.Data;

import java.util.List;

/**
 * @author czh
 * @date 2019-12-13
 */
@Data
public class GaodeGeocodeResult extends Result {
    private List<GaodeGeocode> geocodes;//地理编码信息
}
