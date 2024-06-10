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

#### ✏️ Features

-   **File Upload**
    -   **Description**: Upload a text file and store it in the system. This feature handles the ingestion of new content into the application.-   **One Random Line**

-   **One Random Line**
    -   **Description**: Return one random line from a previously uploaded file. The response format can be text/plain, application/json, or application/xml, depending on the `Accept` header of the request. The system must support all three headers.-   **One Random Line Backwards**
  
-  **One Random Line Backwards**
    -   **Description**: Return the requested random line, but with its characters in reverse order. This feature should be capable of choosing from all uploaded files.-  

- **Longest 100 Lines**
    -   **Description**: Return the 100 longest lines from all files that have been uploaded. This feature involves scanning through all stored data to identify and return the lines with the most characters.-   **20 Longest Lines of One File**

- **20 Longest Lines of One File**
    -   **Description**: Return the 20 longest lines from a single file. This file can be selected randomly or can be the most recently uploaded file, depending on the implementation specifics.



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
