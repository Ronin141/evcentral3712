package de.rwth.idsg.steve.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import de.rwth.idsg.steve.repository.dto.ChargePoint;
import de.rwth.idsg.steve.repository.dto.ConnectorStatus;

@Service
public class ChargeBoxDescriptionService {
	 public Optional<String> getDescriptionByChargeBoxId(
	            String chargeBoxId, 
	            List<ChargePoint.Overview> chargePointOverviews, 
	            List<ConnectorStatus> connectorStatuses) {

	        // Find a match in ChargePoint.Overview by chargeBoxId
	        Optional<ChargePoint.Overview> overview = chargePointOverviews.stream()
	            .filter(cp -> cp.getChargeBoxId().equals(chargeBoxId))
	            .findFirst();

	        // If found in ChargePoint.Overview, return description
	        if (overview.isPresent()) {
	            return Optional.of(overview.get().getDescription());
	        }

	        // Find a match in ConnectorStatus by chargeBoxId
	        Optional<ConnectorStatus> connectorStatus = connectorStatuses.stream()
	            .filter(cs -> cs.getChargeBoxId().equals(chargeBoxId))
	            .findFirst();

	        // If found in ConnectorStatus, return description
	        return connectorStatus.map(ConnectorStatus::getDescription);
	    }
}
