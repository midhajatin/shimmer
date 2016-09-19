package org.openmhealth.shim.microsoft.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import org.openmhealth.schema.domain.omh.CaloriesBurned;
import org.openmhealth.schema.domain.omh.DataPoint;
import org.openmhealth.schema.domain.omh.KcalUnitValue;

import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.openmhealth.schema.domain.omh.KcalUnit.KILOCALORIE;
import static org.openmhealth.shim.common.mapper.JsonNodeMappingSupport.asOptionalInteger;
import static org.openmhealth.shim.common.mapper.JsonNodeMappingSupport.asRequiredNode;
/*
 * Copyright 2015 Open mHealth
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
 *
 */


/* @author jjcampa */
public class MicrosoftCaloriesBurnedDataPointMapper extends MicrosoftDataPointMapper<CaloriesBurned> {
    protected String getListNodeName() {
        return "summaries";
    }

    @Override
    protected Optional<DataPoint<CaloriesBurned>> asDataPoint(JsonNode summaryNode) {
        checkNotNull(summaryNode);

        JsonNode stepCount = asRequiredNode(summaryNode, "caloriesBurnedSummary");
        Optional<Integer> calories = asOptionalInteger(stepCount, "totalCalories");

        CaloriesBurned.Builder caloriesBurnedBuilder = new CaloriesBurned.Builder(
                new KcalUnitValue(KILOCALORIE, calories.orElseGet(() -> 0)));

        caloriesBurnedBuilder.setEffectiveTimeFrame(getStartTime(summaryNode));


        return Optional.of(newDataPoint(caloriesBurnedBuilder.build(), RESOURCE_API_SOURCE_NAME, null, null));
    }
}