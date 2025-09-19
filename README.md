# TMS Config Service

A microservice for managing theatre partner configurations and field mappings in the TMS (Theatre Management System) platform.

## Quick Start

### Prerequisites
- Java 21
- Maven 3.6+

### Running the Application
```bash
mvn spring-boot:run
```

The service will start at: `http://localhost:8086`

### Health Check
```bash
curl http://localhost:8086/api/config/v1/actuator/health
```

## API Endpoints

Base URL: `http://localhost:8086/api/config/v1`

### Partner Management

#### 1. Create Partner
**POST** `/partners`

```bash
curl -X POST http://localhost:8086/api/config/v1/partners \
  -H "Content-Type: application/json" \
  -d '{
    "partnerId": "INOX_001",
    "partnerName": "INOX Leisure Limited",
    "integrationType": "PARTNER_WITH_IT",
    "integrationProtocol": "REST_API",
    "contactEmail": "api@inox.com"
  }'
```

**Response (201 Created):**
```json
{
  "partnerId": "INOX_001",
  "partnerName": "INOX Leisure Limited",
  "integrationType": "PARTNER_WITH_IT",
  "integrationProtocol": "REST_API",
  "contactEmail": "api@inox.com",
  "status": "PENDING",
  "isActive": true,
  "createdAt": "2025-01-17T10:30:00"
}
```

#### 2. Get Partner
**GET** `/partners/{partnerId}`

```bash
curl http://localhost:8086/api/config/v1/partners/INOX_001
```

**Response (200 OK):**
```json
{
  "partnerId": "INOX_001",
  "partnerName": "INOX Leisure Limited",
  "status": "APPROVED",
  "integrationProtocol": "REST_API",
  "contactEmail": "api@inox.com"
}
```

#### 3. Get All Partners
**GET** `/partners`

```bash
curl http://localhost:8086/api/config/v1/partners
```

**Response (200 OK):**
```json
[
  {
    "partnerId": "INOX_001",
    "partnerName": "INOX Leisure Limited",
    "status": "APPROVED"
  },
  {
    "partnerId": "PVR_001", 
    "partnerName": "PVR Cinemas Limited",
    "status": "PENDING"
  }
]
```

#### 4. Update Partner Status
**PUT** `/partners/{partnerId}/status`

```bash
curl -X PUT http://localhost:8086/api/config/v1/partners/INOX_001/status \
  -H "Content-Type: application/json" \
  -d '{"status": "APPROVED"}'
```

**Response (200 OK):**
```json
{
  "partnerId": "INOX_001",
  "partnerName": "INOX Leisure Limited",
  "status": "APPROVED",
  "updatedAt": "2025-01-17T10:35:00"
}
```

#### 5. Update Partner Config
**PUT** `/partners/{partnerId}/config`

```bash
curl -X PUT http://localhost:8086/api/config/v1/partners/INOX_001/config \
  -H "Content-Type: application/json" \
  -d '{
    "baseUrl": "https://api.inox.com",
    "authType": "API_KEY",
    "apiKey": "secret-key-123",
    "endpoints": {
      "theatres": "/v1/theatres",
      "halls": "/v1/halls",
      "shows": "/v1/shows",
      "prices": "/v1/prices"
    }
  }'
```

**Response (200 OK):**
```json
{
  "partnerId": "INOX_001",
  "partnerName": "INOX Leisure Limited",
  "integrationConfig": "{\"baseUrl\":\"https://api.inox.com\",\"authType\":\"API_KEY\",\"endpoints\":{\"theatres\":\"/v1/theatres\",\"halls\":\"/v1/halls\",\"shows\":\"/v1/shows\",\"prices\":\"/v1/prices\"}}"
}
```

### Field Mappings

#### 6. Create Field Mapping
**POST** `/mappings?partnerId={partnerId}`

```bash
curl -X POST "http://localhost:8086/api/config/v1/mappings?partnerId=INOX_001" \
  -H "Content-Type: application/json" \
  -d '{
    "entityType": "THEATRE",
    "sourceField": "theater_id",
    "targetField": "externalId",
    "isRequired": true
  }'
```

**Response (201 Created):**
```json
{
  "id": 1,
  "partnerId": "INOX_001",
  "entityType": "THEATRE",
  "sourceField": "theater_id",
  "targetField": "externalId",
  "isRequired": true,
  "version": 1,
  "isActive": true
}
```

#### 7. Get Field Mappings by Entity
**GET** `/mappings?partnerId={partnerId}&entityType={entityType}`

```bash
curl "http://localhost:8086/api/config/v1/mappings?partnerId=INOX_001&entityType=THEATRE"
```

**Response (200 OK):**
```json
[
  {
    "id": 1,
    "partnerId": "INOX_001",
    "entityType": "THEATRE",
    "sourceField": "theater_id",
    "targetField": "externalId",
    "isRequired": true,
    "version": 1
  },
  {
    "id": 2,
    "partnerId": "INOX_001",
    "entityType": "THEATRE", 
    "sourceField": "theater_name",
    "targetField": "name",
    "isRequired": true,
    "version": 1
  }
]
```

#### 8. Get All Mappings for Partner
**GET** `/mappings/{partnerId}`

```bash
curl http://localhost:8086/api/config/v1/mappings/INOX_001
```

**Response (200 OK):**
```json
[
  {
    "id": 1,
    "partnerId": "INOX_001",
    "entityType": "THEATRE",
    "sourceField": "theater_id",
    "targetField": "externalId"
  },
  {
    "id": 3,
    "partnerId": "INOX_001",
    "entityType": "HALL",
    "sourceField": "hall_id", 
    "targetField": "externalId"
  }
]
```

### Integration Support

#### 9. Get Partners for Polling
**GET** `/integration/partners`

```bash
curl http://localhost:8086/api/config/v1/integration/partners
```

**Response (200 OK):**
```json
[
  {
    "partnerId": "INOX_001",
    "partnerName": "INOX Leisure Limited",
    "integrationProtocol": "REST_API",
    "status": "APPROVED"
  }
]
```

#### 10. Get Partner Integration Config
**GET** `/integration/partners/{partnerId}/config`

```bash
curl http://localhost:8086/api/config/v1/integration/partners/INOX_001/config
```

**Response (200 OK):**
```json
{
  "partnerId": "INOX_001",
  "partnerName": "INOX Leisure Limited",
  "integrationProtocol": "REST_API",
  "integrationConfig": {
    "baseUrl": "https://api.inox.com",
    "authType": "API_KEY",
    "apiKey": "secret-key-123",
    "endpoints": {
      "theatres": "/v1/theatres",
      "halls": "/v1/halls",
      "shows": "/v1/shows",
      "prices": "/v1/prices"
    }
  }
}
```

#### 11. Get Field Mappings for Transformation
**GET** `/integration/partners/{partnerId}/mappings/{entityType}`

```bash
curl http://localhost:8086/api/config/v1/integration/partners/INOX_001/mappings/THEATRE
```

**Response (200 OK):**
```json
[
  {
    "partnerId": "INOX_001",
    "entityType": "THEATRE",
    "sourceField": "theater_id",
    "targetField": "externalId",
    "isRequired": true
  },
  {
    "partnerId": "INOX_001", 
    "entityType": "THEATRE",
    "sourceField": "theater_name",
    "targetField": "name",
    "isRequired": true
  }
]
```


## Common Workflows

### Partner Onboarding Flow
```bash
# 1. Create partner
POST /partners

# 2. Add integration configuration  
PUT /partners/{id}/config

# 3. Create field mappings
POST /mappings?partnerId={id}

# 4. Approve partner
PUT /partners/{id}/status {"status": "APPROVED"}
```

### Integration Service Flow
```bash
# 1. Get approved partners
GET /integration/partners

# 2. Get partner configuration
GET /integration/partners/{id}/config

# 3. Get field mappings for transformation
GET /integration/partners/{id}/mappings/THEATRE
```

## Error Responses

All errors return standardized format:

```json
{
  "errorCode": "BUSINESS_RULE_VIOLATION",
  "message": "Partner already exists: INOX_001",
  "status": 400,
  "timestamp": "2025-01-17T10:30:00"
}
```

## Entity Types
- `THEATRE` - Theatre/Cinema information
- `HALL` - Hall/Screen information
- `SHOW` - Show/Showtime information
- `PRICE` - Pricing information

## Integration Types
- `PARTNER_WITH_IT` - Partners with API integration
- `PARTNER_WITHOUT_IT` - Manual data entry partners

## Integration Protocols
- `REST_API` - REST API integration
- `SOAP_API` - SOAP API integration
- `FTP_FILE` - FTP file transfer

## Partner Status
- `PENDING` - Awaiting approval
- `APPROVED` - Ready for integration
- `REJECTED` - Rejected during approval

## Database
- **Development**: H2 in-memory database
- **Console**: http://localhost:8086/h2-console
