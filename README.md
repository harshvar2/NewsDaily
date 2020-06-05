# NewsDaily

NewsDaily -
App contains a main screen which displays multiple news stories


Main Screen Updates -
Stories shown on the main screen update properly whenever new news data is fetched from the API.

API Query -
App queries the content.guardianapis.com api to fetch news stories related to the topic chosen by the student, using either the ‘test’ api key or the own’s key.

JSON Parsing -
The JSON response is parsed correctly, and relevant information is stored in the app.

Response Validation -
The app checks whether the device is connected to the internet and responds appropriately. The result of the request is validated to account for a bad server response or lack of server response.

Use of Loaders -
Networking operations are done using a Loader rather than an AsyncTask.

List Item Contents -
Each list item on the main screen displays relevant text and information about the story.
The title of the article and the name of the section that it belongs to are required field.
If available, author name and date published should be included. Please note not all responses will contain these pieces of data, but it is required to include them if they are present.
