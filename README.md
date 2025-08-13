# Restriction Hours API

A Spring Boot REST API for managing store restriction hours with support for different categories and time restrictions.

## Overview

This API allows you to manage restriction hours for different stores across various days of the week and product categories. Each restriction hour entry is uniquely identified by a composite key consisting of:
- **Store ID** - Unique identifier for the store
- **Day of Week** - Day name (Monday, Tuesday, etc.)
- **Category** - Product category (e.g., "ALCOHOL")
- **Category Code** - Numeric code for the category (101, 102, etc.)

## Quick Start

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- Spring Boot 3.2.0

### Running the Application
```bash
mvn spring-boot:run
```

The API will be available at: `http://localhost:8888`

### Database Console
H2 Console is available at: `http://localhost:8888/h2-console`
- **JDBC URL**: `jdbc:h2:mem:restrictionhours`
- **Username**: `sa`
- **Password**: `password`

## API Endpoints

### Base URL
```
http://localhost:8888/api/restriction-hours
```

---

## 📖 READ Operations (GET)

### 1. Get All Restriction Hours
```http
GET /api/restriction-hours/getAllRestrictionHours
```

**Description**: Retrieves all restriction hours from the database.

**Response Example**:
```json
[
  {
    "id": 1,
    "storeId": 1,
    "dayOfWeek": "Monday",
    "category": "ALCOHOL",
    "categoryCode": 101,
    "categoryDesc": "BEER",
    "hasRestrictionHour": true,
    "startHour": 6,
    "endHour": 12
  }
]
```

### 2. Get Restriction Hour by ID
```http
GET /api/restriction-hours/{id}
```

**Parameters**:
- `id` (path) - The unique ID of the restriction hour

### 3. Get by Store ID
```http
GET /api/restriction-hours/store/{storeId}
```

**Parameters**:
- `storeId` (path) - Store identifier

**Example**: `GET /api/restriction-hours/store/1`

### 4. Get by Store ID and Day
```http
GET /api/restriction-hours/store/{storeId}/day/{dayOfWeek}
```

**Parameters**:
- `storeId` (path) - Store identifier
- `dayOfWeek` (path) - Day name (Monday, Tuesday, etc.)

**Example**: `GET /api/restriction-hours/store/1/day/Monday`

### 5. Get by Day of Week
```http
GET /api/restriction-hours/day/{dayOfWeek}
```

**Parameters**:
- `dayOfWeek` (path) - Day name

### 6. Get by Category Code
```http
GET /api/restriction-hours/category/{categoryCode}
```

**Parameters**:
- `categoryCode` (path) - Category code (101, 102, etc.)

### 7. Get Entries with Restrictions
```http
GET /api/restriction-hours/with-restrictions
```

**Description**: Returns only entries where `hasRestrictionHour` is `true`.

### 8. Get by Multiple Store IDs
```http
GET /api/restriction-hours/stores?ids=1,2,3
```

**Parameters**:
- `ids` (query) - Comma-separated list of store IDs

---

## ➕ CREATE Operations (POST)

### 1. Create Single Restriction Hour
```http
POST /api/restriction-hours
```

**Request Body**:
```json
{
  "storeId": 1,
  "dayOfWeek": "Monday",
  "category": "ALCOHOL",
  "categoryCode": 101,
  "categoryDesc": "BEER",
  "hasRestrictionHour": true,
  "startHour": 6,
  "endHour": 12
}
```

**Response**: Created restriction hour object with assigned ID.

### 2. Create Multiple Restriction Hours
```http
POST /api/restriction-hours/batch
```

**Request Body**:
```json
[
  {
    "storeId": 1,
    "dayOfWeek": "Monday",
    "category": "ALCOHOL",
    "categoryCode": 101,
    "categoryDesc": "BEER",
    "hasRestrictionHour": true,
    "startHour": 6,
    "endHour": 12
  },
  {
    "storeId": 1,
    "dayOfWeek": "Tuesday",
    "category": "ALCOHOL",
    "categoryCode": 101,
    "categoryDesc": "BEER",
    "hasRestrictionHour": true,
    "startHour": 8,
    "endHour": 14
  }
]
```

---

## ✏️ UPDATE Operations (PUT)

### 1. Update by Path Variables
```http
PUT /api/restriction-hours/store/{storeId}/day/{dayOfWeek}/category/{category}/code/{categoryCode}
```

**Parameters**:
- `storeId` (path) - Store identifier
- `dayOfWeek` (path) - Day name
- `category` (path) - Category name
- `categoryCode` (path) - Category code

**Request Body**:
```json
{
  "hasRestrictionHour": true,
  "startHour": 8,
  "endHour": 14,
  "categoryDesc": "BEER"
}
```

**Example**: 
```http
PUT /api/restriction-hours/store/1/day/Monday/category/ALCOHOL/code/101
```

### 2. Update by Request Body
```http
PUT /api/restriction-hours/update
```

**Request Body**:
```json
{
  "storeId": 1,
  "dayOfWeek": "Monday",
  "category": "ALCOHOL",
  "categoryCode": 101,
  "hasRestrictionHour": true,
  "startHour": 8,
  "endHour": 14,
  "categoryDesc": "BEER"
}
```

### 3. Batch Update
```http
PUT /api/restriction-hours/batch-update
```

**Request Body**: Array of restriction hour objects with composite keys.

---

## 🗑️ DELETE Operations

### 1. Delete by Path Variables
```http
DELETE /api/restriction-hours/store/{storeId}/day/{dayOfWeek}/category/{category}/code/{categoryCode}
```

**Example**: 
```http
DELETE /api/restriction-hours/store/1/day/Monday/category/ALCOHOL/code/101
```

**Response**:
```json
{
  "message": "Restriction hour deleted successfully",
  "timestamp": 1703123456789
}
```

### 2. Delete by Request Body
```http
DELETE /api/restriction-hours/delete
```

**Request Body**:
```json
{
  "storeId": 1,
  "dayOfWeek": "Monday",
  "category": "ALCOHOL",
  "categoryCode": 101
}
```

### 3. Batch Delete
```http
DELETE /api/restriction-hours/batch-delete
```

**Request Body**: Array of restriction hour objects with composite keys.

**Response**:
```json
{
  "message": "Batch delete completed",
  "deletedCount": 2,
  "totalRequested": 3,
  "timestamp": 1703123456789
}
```

---

## 📋 Data Model

### RestrictionHour Object
```json
{
  "id": 1,                          // Auto-generated ID
  "storeId": 1,                     // Required: Store identifier
  "dayOfWeek": "Monday",            // Required: Day name
  "category": "ALCOHOL",            // Required: Product category
  "categoryCode": 101,              // Required: Category code
  "categoryDesc": "BEER",           // Required: Category description
  "hasRestrictionHour": true,       // Required: Whether restrictions apply
  "startHour": 6,                   // Required if hasRestrictionHour is true (0-23)
  "endHour": 12                     // Required if hasRestrictionHour is true (0-23)
}
```

### Valid Values

**Days of Week**: Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday

**Category Codes**:
- 101 - BEER
- 102 - LIQUOR  
- 103 - Single Serve Beer
- 104 - Wine

**Hours**: 0-23 (24-hour format)

---

## 🚨 Error Handling

### Error Response Format
```json
{
  "message": "Error description",
  "timestamp": 1703123456789
}
```

### Common Error Codes
- **400 Bad Request**: Invalid data or missing required fields
- **404 Not Found**: Restriction hour not found for given composite key
- **500 Internal Server Error**: Server-side error

### Validation Rules
1. **Store ID**: Must be positive integer
2. **Day of Week**: Must be valid day name (case-insensitive)
3. **Category**: Cannot be empty
4. **Category Code**: Cannot be null
5. **Hours**: Must be 0-23 if hasRestrictionHour is true
6. **Composite Key**: Must be unique for create operations

---

## 🔧 Frontend Integration Examples

### JavaScript/React Examples

#### Fetch All Restriction Hours
```javascript
const fetchRestrictionHours = async () => {
  try {
    const response = await fetch('http://localhost:8888/api/restriction-hours/getAllRestrictionHours');
    const data = await response.json();
    console.log('Restriction Hours:', data);
  } catch (error) {
    console.error('Error fetching data:', error);
  }
};
```

#### Create New Restriction Hour
```javascript
const createRestrictionHour = async (restrictionData) => {
  try {
    const response = await fetch('http://localhost:8888/api/restriction-hours', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(restrictionData)
    });
    
    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }
    
    const result = await response.json();
    console.log('Created:', result);
  } catch (error) {
    console.error('Error creating restriction hour:', error);
  }
};
```

#### Update Restriction Hour
```javascript
const updateRestrictionHour = async (restrictionData) => {
  try {
    const response = await fetch('http://localhost:8888/api/restriction-hours/update', {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(restrictionData)
    });
    
    const result = await response.json();
    console.log('Updated:', result);
  } catch (error) {
    console.error('Error updating restriction hour:', error);
  }
};
```

#### Delete Restriction Hour
```javascript
const deleteRestrictionHour = async (storeId, dayOfWeek, category, categoryCode) => {
  try {
    const url = `http://localhost:8888/api/restriction-hours/store/${storeId}/day/${dayOfWeek}/category/${category}/code/${categoryCode}`;
    const response = await fetch(url, {
      method: 'DELETE'
    });
    
    const result = await response.json();
    console.log('Deleted:', result);
  } catch (error) {
    console.error('Error deleting restriction hour:', error);
  }
};
```

---

## 🔍 CORS Configuration

The API is configured to accept requests from:
- `http://localhost:3000` (React default)
- `http://localhost:5173` (Vite default)

If you need to add other origins, update the `@CrossOrigin` annotation in the controller.

---

## 📝 Testing the API

### Using cURL

#### Get all restriction hours:
```bash
curl -X GET http://localhost:8888/api/restriction-hours/getAllRestrictionHours
```

#### Create a new restriction hour:
```bash
curl -X POST http://localhost:8888/api/restriction-hours \
  -H "Content-Type: application/json" \
  -d '{
    "storeId": 1,
    "dayOfWeek": "Monday",
    "category": "ALCOHOL",
    "categoryCode": 101,
    "categoryDesc": "BEER",
    "hasRestrictionHour": true,
    "startHour": 6,
    "endHour": 12
  }'
```

#### Update a restriction hour:
```bash
curl -X PUT http://localhost:8888/api/restriction-hours/store/1/day/Monday/category/ALCOHOL/code/101 \
  -H "Content-Type: application/json" \
  -d '{
    "hasRestrictionHour": true,
    "startHour": 8,
    "endHour": 14,
    "categoryDesc": "BEER"
  }'
```

#### Delete a restriction hour:
```bash
curl -X DELETE http://localhost:8888/api/restriction-hours/store/1/day/Monday/category/ALCOHOL/code/101
```

---

## 📞 Support

For questions or issues, please contact the backend development team or create an issue in the project repository.

---

## 📄 License

This project is part of the Vibe-Coding restriction hours management system. 