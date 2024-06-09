![Welcome](https://raw.githubusercontent.com/ryxandy/RestConsuming/master/rest.png)
#                                                                        Tour into this application 
###                                                             Overview, Technologies, Response Example.


#     💻 Technologies:

#### Java 17
#### Spring boot
#### Spring Data/JPA
#### jUnit 5
#### Mockito
#### Lombok
#### Jackson
#### Maven
#### MySQL


#### 1\. GET `/random-line`

-   **Description**: Returns a random line from a file specified by the file ID.
-   **Parameters**: `fileId` (ID of the file)
-   **`Accept` Header**: Determines the response format (JSON, XML, other `application/*` formats).
-   **Return**:
    -   JSON (`application/json`)
    -   Detailed (`application/*`)
    -   XML (`application/xml`)
    -   Plain text (if not specified)

#### 2\. GET `/file/{fileId}`

-   **Description**: Returns detailed information about a specific file.
-   **Parameters**: `fileId` (ID of the file)
-   **Return**: Details of the file including name, size, creation date, etc.

#### 3\. POST `/file`

-   **Description**: Allows uploading a new file to the system.
-   **Request Body**: File data (in `multipart/form-data`).
-   **Return**: Status of the upload and information of the saved file.

#### 4\. DELETE `/file/{fileId}`

-   **Description**: Removes a file specified by the file ID.
-   **Parameters**: `fileId` (ID of the file)
-   **Return**: Confirmation of deletion.

#### 5\. GET `/files`

-   **Description**: Lists all files available in the system.
-   **Return**: List of files with basic details (name, ID, size).
