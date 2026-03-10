package com.audio.transcribe;

import io.netty.handler.codec.http.HttpResponse;
import org.springframework.ai.audio.transcription.AudioTranscriptionPrompt;
import org.springframework.ai.audio.transcription.AudioTranscriptionResponse;
import org.springframework.ai.openai.OpenAiAudioTranscriptionModel;
import org.springframework.ai.openai.OpenAiAudioTranscriptionOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.ai.openai.api.OpenAiAudioApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

import static io.netty.handler.codec.http.multipart.DiskFileUpload.prefix;

@RestController
@RequestMapping("/api/transcribe")
public class TranscriptionController {

    private final OpenAiAudioTranscriptionModel transcriptionModel;

  /*  public TranscriptionController(@Value("${spring.ai.openai.api-key}") String apiKey) {
        OpenAiAudioApi openAiAudioApi = new OpenAiAudioApi(apiKey);

        this.transcriptionModel = new OpenAiAudioTranscriptionModel(openAiAudioApi);
    }*/

    public TranscriptionController(OpenAiAudioTranscriptionModel transcriptionModel) {
        this.transcriptionModel = transcriptionModel;
    }

    @PostMapping
    public ResponseEntity<String> transcribeAudio(@RequestParam("file") MultipartFile file) throws IOException {
        File tempFile = null;
        try {
            // Save uploaded file temporarily
            tempFile = File.createTempFile("audio", ".wav");
            file.transferTo(tempFile);

            OpenAiAudioTranscriptionOptions transcriptionOptions = OpenAiAudioTranscriptionOptions.builder()
                    .responseFormat(OpenAiAudioApi.TranscriptResponseFormat.TEXT)
                    .language("en")
                    .temperature(0f)
                    .build();

            FileSystemResource audioFile = new FileSystemResource(tempFile);
            AudioTranscriptionPrompt transcriptionRequest = new AudioTranscriptionPrompt(audioFile, transcriptionOptions);

            // Call transcription model
            AudioTranscriptionResponse response = transcriptionModel.call(transcriptionRequest);

            return new ResponseEntity<>(response.getResult().getOutput(), HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace(); // logs the full error
            String message = e.getMessage();
            if (message != null && message.contains("insufficient_quota")) {
                return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                        .body("OpenAI quota exceeded. Please check your API plan.");
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing audio file: " + e.getMessage());
        } finally {
            // Delete temp file if it exists
            if (tempFile != null && tempFile.exists()) {
                tempFile.delete();
            }


        }
    }
}
