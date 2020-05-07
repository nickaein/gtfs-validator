/*
 * Copyright (c) 2020. MobilityData IO.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.mobilitydata.gtfsvalidator.usecase.port;

import org.mobilitydata.gtfsvalidator.domain.entity.gtfs.Agency;
import org.mobilitydata.gtfsvalidator.domain.entity.gtfs.Attribution;
import org.mobilitydata.gtfsvalidator.domain.entity.gtfs.routes.Route;

public interface GtfsDataRepository {
    Agency addAgency(final Agency newAgency) throws IllegalArgumentException;

    Agency getAgencyById(final String agencyId);

    Route addRoute(final Route newRoute) throws IllegalArgumentException;

    Route getRouteById(final String routeId);

    Attribution addAttribution(final Attribution newAttribution) throws IllegalArgumentException;

    Attribution getAttribution(final String attributionId, final String agencyId, final String routeId,
                               final String tripId, final String organizationName, final Boolean isProducer,
                               final Boolean isOperator, final Boolean isAuthority, final String attributionUrl,
                               final String attributionEmail, final String attributionPhone);
}