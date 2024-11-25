GET # Obtain company list with response of id, name
URL: GET /api/companies

GET # Obtain a certain specific company with response of id, name
URL: GET /api/companies/{id}

GET # Obtain list of all employees under a certain specific company
URL: GET /api/companies/{id}/employees

GET # Page query, page equals 1, size equals 5, it will return the data in company list from index 0 to index 4
URL: GET /api/companies?page=1&size=5

PUT # Update an employee with company
URL: PUT /api/companies/{companyId}/employees/{employeeId}

POST # Add a company
URL: POST /api/companies

DELETE # Delete a company
URL: DELETE /api/companies/{id}
