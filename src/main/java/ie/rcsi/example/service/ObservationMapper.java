package ie.rcsi.example.service;

import ie.rcsi.example.dto.ObservationDetails;
import org.hl7.fhir.r5.model.Bundle;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface ObservationMapper {
    @NotNull List<ObservationDetails> mapBundleToObservationDetails(Bundle bundle, boolean isAll);
}
