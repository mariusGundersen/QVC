Validation
==========

Request:
--------

**Method:** GET

**Path:** ${qvc.config.baseUrl}/constraints/${executableName}

Response:
---------

    {
    	parameters: [
    		{
    			name: ${path to field},
    			constraints: [
    				{
    					name: ${name of constraint}
    					attributes: ${an object describing the constraint}
    				}
    			]
    		}
    	]
    }

Commands & Queries
==================

Request:
--------

**Method:** POST

**Path:** ${baseUrl}/command/${executableName}?cacheKey=${randomNumber}
**Path:** ${baseUrl}/query/${executableName}?cacheKey=${randomNumber}

**Content:**

* csrfToken: ${qvc.config.csrf}
* parameters: ${json of the parameters}

Response:
---------

The response depends on what happens on the server. 
success and valid are always present
result is only present for queries
violations and exception are only present if something went wrong,
violations when a parameter violates a constraint,
exception if the server threw an exception

    {
    	success: ${true|false},
    	valid: ${true|false},
    	result: ${object containing the result of a query},
    	violations: [
    		{
    			fieldName: ${path to field which violated a constraint},
    			message: ${message describing how it violated the constraint}
    		}
    	],
    	exception: ${object containing the message, cause and stacktrace}
    }


