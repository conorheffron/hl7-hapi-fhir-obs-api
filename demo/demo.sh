#!/bin/bash

echo "Running HAPI FHIR API calls..."
ls -ltr

echo "Running Care Plan API call----------------------------------------------"
curl -X GET -H 'Accept: application/json' 'http://localhost:8080/api/care/plan?resourceId=1566160' | tee output/carePlanSample.txt | (head -n 5; tail -n 5)

sleep 1

echo "Running Observations By Code API calls----------------------------------------------"
while read -r id; do
  echo "Running Observation for code $id"
  sleep 2
  curl -X GET -H 'Accept: application/json' "http://localhost:8080/api/observation?code=$id" | tee "output/observationsByCode_$id.txt" | (head -n 5; tail -n 5) &
done < input/observationCodes.txt
wait

sleep 1

echo "Running Observations API call----------------------------------------------"
curl -X GET -H 'Accept: application/json' 'http://localhost:8080/api/all/observation?code=39156-5' | tee output/observationAllByCodeSample.txt | (head -n 5; tail -n 5)

sleep 1

echo "Running Observations where BMI >= 30% API call----------------------------------------------"
curl -X GET -H 'Accept: application/json' 'http://localhost:8080/api/obese/observation?code=39156-5' | tee output/observationIsObeseSample.txt | (head -n 5; tail -n 5)

sleep 1

echo "Running Device API call----------------------------------------------"
curl -X GET -H 'Accept: application/json' 'http://localhost:8080/api/device?resourceId=581517' | tee output/deviceSample.txt | (head -n 5; tail -n 5)

sleep 1

echo "Running Device Request API call----------------------------------------------"
curl -X GET -H 'Accept: application/json' 'http://localhost:8080/api/device/request?resourceId=1723173' | tee output/deviceRequestSample.txt | (head -n 5; tail -n 5)

sleep 1

echo "Running Patient API call----------------------------------------------"
curl -X GET -H 'Accept: application/json' 'http://localhost:8080/api/patient?resourceId=113798' | tee output/patientSample.txt | (head -n 5; tail -n 5)

echo "Running Nutrition Order API call----------------------------------------------"
while read -r id; do
  echo "Running Diagnostic Report for resource ID $id"
  sleep 2
  curl -X GET -H 'Accept: application/json' "http://localhost:8080/api/nutrition/order?resourceId=$id" | tee "output/nutritionOrderById_$id.txt" | (head -n 5; tail -n 5) &
done < input/nutritionOrderResourceIds.txt
wait

echo "Running Diagnostic Report API calls----------------------------------------------"
while read -r id; do
  echo "Running Diagnostic Report for resource ID $id"
  sleep 2
  curl -X GET -H 'Accept: application/json' "http://localhost:8080/api/diagnostic/report?resourceId=$id" | tee "output/diagnosticReportById_$id.txt" | (head -n 5; tail -n 5) &
done < input/diagnosticResourceIds.txt
wait

ls -ltr output
echo "Exiting Script"
exit
