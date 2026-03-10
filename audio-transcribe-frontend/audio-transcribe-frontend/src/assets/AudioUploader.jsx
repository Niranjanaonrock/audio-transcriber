import { useState } from "react";
import axios from "axios";



const AudioUploader = () =>{
const[file, setFile] = useState(null);
const[transcription, setTranscription] = useState("");
const [error, setError] = useState("");

const handleFileChange = (event) => {
    setFile(event.target.files[0]);
     setError(""); // clear previous errors
    setTranscription(""); // clear previous transcription
}

const handleUploader = async () => {
    if (!file) {
      setError("Please select a file to upload.");
      return;
    }
   const formData = new FormData();
   formData.append("file", file);

   try {
    const response = await axios.post("http://localhost:8080/api/transcribe", formData, {
        headers: {
            "Content-Type": "multipart/form-data"
        }
    });
    setTranscription(response.data);
    setError(""); // clear error if successful
   } catch (error) {
        console.error("Error uploading file:", error);
    

    if (error.response) {
        // backend returned a response
        setError(`Error: ${error.response.data}`);
      } else if (error.request) {
        // request was made but no response
        setError("No response from server. Please try again later.");
      } else {
        // something else
        setError("Unexpected error occurred: " + error.message);
      }
      setTranscription(""); // clear transcription on error

}}
    return (
        <div className="container">
            <h1>Audio to Text Transcriber</h1>
            <div className="file-input">
                <input type="file" id="audioFile" accept="audio/*" onChange={handleFileChange}/>
            </div>

            <button className="upload-button" onClick={handleUploader}>Upload and Transcribe</button>
             {/* Display transcription */}
      {transcription && (
        <div className="transcription-result">
          <h2>Transcription Result</h2>
          <p>{transcription}</p>
        </div>
      )}

      {/* Display error message */}
      {error && (
        <div className="error-message">
          <p>{error}</p>
        </div>
      )}
        </div>
    )
}

export default AudioUploader;