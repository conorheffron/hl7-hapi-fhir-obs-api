# hl7-hapi-fhir-obs-api

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

[![Java CI with Maven](https://github.com/conorheffron/hl7-hapi-fhir-obs-api/actions/workflows/maven.yml/badge.svg)](https://github.com/conorheffron/hl7-hapi-fhir-obs-api/actions/workflows/maven.yml)

### QUICK START: Build & Run API
```shell
./mvnw clean package spring-boot:run
```
#### Step-By-Step Guide
##### Build Project
```shell
./mvnw clean package
```
##### Run API with JDK 25 (LTS)
```shell
java -jar target/hl7-hapi-fhir-obs-api-1.3-SNAPSHOT.jar ie.rcsi.example.Application
```
##### Run Test Script for Automated Batch of API calls
```shell
cd demo
./demo.sh
```

---

# Sample API Requests (Manual Run)

### Care Plan GET
```shell
curl -X GET -H 'Accept: application/json' 'http://localhost:8080/api/care/plan?resourceId=1566160'                                                                                                                                                                                                                            
```
### GET Device
```shell
curl -X GET -H 'Accept: application/json' 'http://localhost:8080/api/device?resourceId=581517'
```
### GET Device Request
```shell
curl -X GET -H 'Accept: application/json' 'http://localhost:8080/api/device/request?resourceId=1723173'                                                                                                                                                                                                                                
```
### GET Patient
```shell
curl -X GET -H 'Accept: application/json' 'http://localhost:8080/api/patient?resourceId=113798'
```

### GET Observations by Code (`WHERE` Patients Considered Obese)
```shell
curl -X GET -H 'Accept: application/json' 'http://localhost:8080/api/obese/observation?code=39156-5'                                                                                                                                      
```
#### Console Output
```
[ {
  "bmi" : 34.9,
  "patientId" : "Patient/1201",
  "observationId" : "102791"
}, {
  "bmi" : 36.2,
  "patientId" : "Patient/1203",
  "observationId" : "102803"
}, {
  "bmi" : 41.8,
  "patientId" : "Patient/1316",
  "observationId" : "102839"
}, {
  "bmi" : 34.7,
  "patientId" : null,
  "observationId" : "106394"
} ]
```
### GET Observations by Code (`WHERE` Patients have BMI Observations Recorded)
 - Note: Some Bad / Test data where patient ID/Reference is missing.
```shell
curl -X GET -H 'Accept: application/json' 'http://localhost:8080/api/all/observation?code=39156-5'
```
#### Console Output
```
[ {
  "bmi" : 19.91,
  "patientId" : "Patient/49006",
  "observationId" : "49262"
}, {
  "bmi" : 20.49,
  "patientId" : "Patient/49006",
  "observationId" : "49278"
}, {
  "bmi" : 28.0,
  "patientId" : "Patient/53254",
  "observationId" : "53248"
}, {
  "bmi" : 28.88,
  "patientId" : null,
  "observationId" : "66247"
}, {
  "bmi" : 28.88,
  "patientId" : null,
  "observationId" : "66248"
}, {
  "bmi" : 28.88,
  "patientId" : null,
  "observationId" : "66249"
}, {
  "bmi" : 28.88,
  "patientId" : null,
  "observationId" : "66250"
}, {
  "bmi" : 16.2,
  "patientId" : "Patient/1200",
  "observationId" : "102786"
}, {
  "bmi" : 34.9,
  "patientId" : "Patient/1201",
  "observationId" : "102791"
}, {
  "bmi" : 36.2,
  "patientId" : "Patient/1203",
  "observationId" : "102803"
}, {
  "bmi" : 11.2,
  "patientId" : "Patient/1208",
  "observationId" : "102826"
}, {
  "bmi" : 41.8,
  "patientId" : "Patient/1316",
  "observationId" : "102839"
}, {
  "bmi" : 34.7,
  "patientId" : null,
  "observationId" : "106394"
}, {
  "bmi" : 27.7,
  "patientId" : "Patient/597991",
  "observationId" : "598191"
}, {
  "bmi" : 27.78,
  "patientId" : "Patient/622898",
  "observationId" : "622925"
}, {
  "bmi" : 27.78,
  "patientId" : "Patient/622898",
  "observationId" : "622988"
}, {
  "bmi" : 27.78,
  "patientId" : "Patient/622898",
  "observationId" : "623047"
}, {
  "bmi" : 27.78,
  "patientId" : "Patient/622898",
  "observationId" : "623084"
}, {
  "bmi" : 27.78,
  "patientId" : "Patient/622898",
  "observationId" : "623113"
}, {
  "bmi" : 27.78,
  "patientId" : "Patient/622898",
  "observationId" : "623158"
} ]
```
