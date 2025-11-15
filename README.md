# hl7-hapi-fhir-obs-api

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
java -jar target/hl7-hapi-fhir-obs-api-1.1-SNAPSHOT.jar ie.rcsi.example.Application
```
##### Run Test Script for Automated Batch of API calls
```shell
./demo.sh
```

---

# Manual API Requests

### Care Plan GET
```shell
curl -X GET -H 'Accept: application/json' 'http://localhost:8080/api/care/plan'

{
  "resourceType": "CarePlan",
  "id": "1566160",
  "meta": {
    "versionId": "1",
    "lastUpdated": "2020-10-12T12:38:46.348+00:00",
    "source": "#Kin2Ysu3xbkatyp9"
  },
  "identifier": [ {
    "value": "12345"
  } ],
  "instantiatesCanonical": [ "http://example.org/protocol-for-obesity/PlanDefinition" ],
  "instantiatesUri": [ "http://example.org/protocol-for-obesity" ],
  "basedOn": [ {
    "reference": "CarePlan/113854"
  } ],
  "replaces": [ {
    "reference": "CarePlan/113854"
  } ],
  "partOf": [ {
    "reference": "CarePlan/113854"
  } ],
  "status": "draft",
  "intent": "proposal",
  "category": [ {
    "coding": [ {
      "system": "http://loinc.org",
      "code": "28842-3",
      "display": "Sphere distance Glasses prescription.lens - left"
    } ],
    "text": "sphere, left lens"
  } ],
  "title": "<string>",
  "description": "<string>",
  "subject": {
    "reference": "Patient/6bce000f-73a4-415d-971e-753cc484778d",
    "display": "Roel"
  },
  "encounter": {
    "reference": "Encounter/c95a4972-df9f-4606-944b-a9ac26c6a31e"
  },
  "period": {
    "start": "2013-04-14",
    "end": "2013-04-21"
  },
  "created": "2013-04-14",
  "author": {
    "reference": "Patient/113798"
  },
  "contributor": [ {
    "reference": "Device/113805"
  } ],
  "careTeam": [ {
    "reference": "CareTeam/113839"
  } ],
  "addresses": [ {
    "reference": "Condition/113789",
    "display": "Roel's sepsis"
  } ],
  "supportingInfo": [ {
    "reference": "Encounter/113792"
  } ],
  "goal": [ {
    "reference": "Goal/113853"
  } ],
  "activity": [ {
    "outcomeCodeableConcept": [ {
      "coding": [ {
        "system": "http://loinc.org",
        "code": "28842-3",
        "display": "Sphere distance Glasses prescription.lens - left"
      } ],
      "text": "sphere, left lens"
    } ],
    "outcomeReference": [ {
      "reference": "Encounter/113792"
    } ],
    "progress": [ {
      "text": "Patient indicates they miss the occasional dose"
    } ],
    "reference": {
      "reference": "Appointment/113815"
    },
    "detail": {
      "kind": "Appointment",
      "instantiatesCanonical": [ "http://example.org/protocol-for-obesity/PlanDefinition" ],
      "instantiatesUri": [ "http://example.org/protocol-for-obesity" ],
      "code": {
        "coding": [ {
          "system": "http://loinc.org",
          "code": "28842-3",
          "display": "Sphere distance Glasses prescription.lens - left"
        } ],
        "text": "sphere, left lens"
      },
      "reasonCode": [ {
        "coding": [ {
          "system": "http://loinc.org",
          "code": "28842-3",
          "display": "Sphere distance Glasses prescription.lens - left"
        } ],
        "text": "sphere, left lens"
      } ],
      "reasonReference": [ {
        "reference": "Condition/113789"
      } ],
      "goal": [ {
        "reference": "Goal/113853"
      } ],
      "status": "not-started",
      "statusReason": {
        "coding": [ {
          "system": "http://loinc.org",
          "code": "28842-3",
          "display": "Sphere distance Glasses prescription.lens - left"
        } ],
        "text": "sphere, left lens"
      },
      "doNotPerform": true,
      "scheduledString": "<string>",
      "location": {
        "reference": "Location/113788"
      },
      "performer": [ {
        "reference": "Patient/113798"
      } ],
      "productReference": {
        "reference": "Location/113788"
      },
      "dailyAmount": {
        "value": 500,
        "unit": "mg",
        "system": "http://unitsofmeasure.org",
        "code": "mg"
      },
      "quantity": {
        "value": 500,
        "unit": "mg",
        "system": "http://unitsofmeasure.org",
        "code": "mg"
      },
      "description": "<string>"
    }
  } ],
  "note": [ {
    "text": "Patient indicates they miss the occasional dose"
  } ]
}                                                                                                                                                                                                                                     
```
### GET Observations by Code (Patients Considered Obese)
```shell
curl -X GET -H 'Accept: application/json' 'http://localhost:8080/api/all/observation?code=39156-5'

[{"bmi":19.91,"patientId":"Patient/49006","observationId":"49262"},{"bmi":20.49,"patientId":"Patient/49006","observationId":"49278"},{"bmi":28.0,"patientId":"Patient/53254","observationId":"53248"},{"bmi":28.88,"patientId":null,"observationId":"66247"},{"bmi":28.88,"patientId":null,"observationId":"66248"},{"bmi":28.88,"patientId":null,"observationId":"66249"},{"bmi":28.88,"patientId":null,"observationId":"66250"},{"bmi":16.2,"patientId":"Patient/1200","observationId":"102786"},{"bmi":34.9,"patientId":"Patient/1201","observationId":"102791"},{"bmi":36.2,"patientId":"Patient/1203","observationId":"102803"},{"bmi":11.2,"patientId":"Patient/1208","observationId":"102826"},{"bmi":41.8,"patientId":"Patient/1316","observationId":"102839"},{"bmi":34.7,"patientId":null,"observationId":"106394"},{"bmi":27.7,"patientId":"Patient/597991","observationId":"598191"},{"bmi":27.78,"patientId":"Patient/622898","observationId":"622925"},{"bmi":27.78,"patientId":"Patient/622898","observationId":"622988"},{"bmi":27.78,"patientId":"Patient/622898","observationId":"623047"},{"bmi":27.78,"patientId":"Patient/622898","observationId":"623084"},{"bmi":27.78,"patientId":"Patient/622898","observationId":"623113"},{"bmi":27.78,"patientId":"Patient/622898","observationId":"623158"}]%                                                                                                                      

```
### GET Observations by Code 
```shell
curl -X GET -H 'Accept: application/json' 'http://localhost:8080/api/obese/observation?code=39156-5'

[{"bmi":34.9,"patientId":"Patient/1201","observationId":"102791"},{"bmi":36.2,"patientId":"Patient/1203","observationId":"102803"},{"bmi":41.8,"patientId":"Patient/1316","observationId":"102839"},{"bmi":34.7,"patientId":null,"observationId":"106394"}]%                                                                                                                                                                                                                    
```
### GET Device
```shell
curl -X GET -H 'Accept: application/json' 'http://localhost:8080/api/device'       
                 
{
  "resourceType": "Device",
  "id": "113805",
  "meta": {
    "versionId": "1",
    "lastUpdated": "2025-08-25T23:04:57.902+00:00",
    "source": "#SCXSgT4rD1HsiuU0"
  },
  "text": {
    "status": "generated",
    "div": "<div xmlns=\"http://www.w3.org/1999/xhtml\"><p><b>Generated Narrative with Details</b></p><p><b>id</b>: example</p><p><b>identifier</b>: 345675</p></div>"
  },
  "identifier": [ {
    "system": "http://goodcare.org/devices/id",
    "value": "345675"
  } ]
}
```
### GET Device Request
```shell
curl -X GET -H 'Accept: application/json' 'http://localhost:8080/api/device/request'

{
  "resourceType": "DeviceRequest",
  "id": "206731",
  "meta": {
    "versionId": "1",
    "lastUpdated": "2025-08-25T22:31:32.095+00:00",
    "source": "#w1TDDHY7QWus4q07"
  },
  "text": {
    "status": "generated",
    "div": "<div xmlns=\"http://www.w3.org/1999/xhtml\"><p><b>Generated Narrative with Details</b></p><p><b>id</b>: example</p><p><b>status</b>: completed</p><p><b>intent</b>: original-order</p><p><b>code</b>: <a>Device/example</a></p><p><b>subject</b>: <a>Patient/example</a></p></div>"
  },
  "status": "completed",
  "intent": "original-order",
  "subject": {
    "reference": "Patient/206686"
  }
}                                                                                                                                                                                                                                    
```
### GET Patient
```shell
curl -X GET -H 'Accept: application/json' 'http://localhost:8080/api/patient'   
    
{
  "resourceType": "Patient",
  "id": "113798",
  "meta": {
    "versionId": "4",
    "lastUpdated": "2025-09-03T14:36:24.129+00:00",
    "source": "#eEVorK925S7W4fgP"
  },
  "text": {
    "status": "generated",
    "div": "<div xmlns=\"http://www.w3.org/1999/xhtml\"> <div class=\"hapiHeaderText\">Mrs Katerina <b>NGUYEN </b> </div> <table class=\"hapiPropertyTable\"> <tbody> <tr> <td>Identifier</td> <td>49476534</td> </tr> <tr> <td>Address</td> <td> <span>Unit 7 </span> <br/> <span>76 Clydesdale St </span> <br/> <span>Como </span> <span>WA </span> <span>Australia </span> </td> </tr> <tr> <td>Date of birth</td> <td> <span>18 August 1979</span> </td> </tr> </tbody> </table> </div>"
  },
  "identifier": [ {
    "value": "49476534"
  } ],
  "active": true,
  "name": [ {
    "use": "usual",
    "family": "Nguyen",
    "given": [ "Katerina" ]
  } ],
  "telecom": [ {
    "system": "phone",
    "value": "0403 823 832",
    "use": "mobile",
    "rank": 1
  }, {
    "system": "phone",
    "value": "08 9398 1165",
    "use": "home",
    "rank": 2
  }, {
    "system": "phone",
    "value": "08 9876 5432",
    "use": "work",
    "rank": 3
  }, {
    "system": "email",
    "value": "kate.nguyen79@gmail.com",
    "use": "work",
    "rank": 4
  } ],
  "gender": "female",
  "birthDate": "1979-08-19",
  "deceasedBoolean": false,
  "address": [ {
    "use": "home",
    "line": [ "Unit 7", "76 Clydesdale St" ],
    "city": "Como",
    "state": "WA",
    "postalCode": "6152",
    "country": "Australia"
  } ],
  "contact": [ {
    "relationship": [ {
      "coding": [ {
        "system": "http://hl7.org/fhir/ValueSet/patient-contact-relationship",
        "code": "partner",
        "display": "Partner"
      } ]
    } ],
    "name": {
      "use": "usual",
      "family": "Nguyen",
      "given": [ "Jimmy" ],
      "prefix": [ "Mr" ]
    },
    "telecom": [ {
      "system": "phone",
      "value": "0403 123 456",
      "use": "mobile",
      "rank": 1
    }, {
      "system": "phone",
      "value": "08 4564 3214",
      "use": "home",
      "rank": 2
    }, {
      "system": "phone",
      "value": "08 9994 5547",
      "use": "work",
      "rank": 3
    }, {
      "system": "email",
      "value": "jim.nguyen79@work.com",
      "use": "work",
      "rank": 4
    } ],
    "address": {
      "use": "home",
      "line": [ "Unit 7", "76 Clydesdale St" ],
      "city": "Como",
      "state": "WA",
      "postalCode": "6152",
      "country": "Australia"
    },
    "gender": "male"
  } ]
}
```
