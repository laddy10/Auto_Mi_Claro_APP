package utils;

import okhttp3.*;

import java.io.IOException;

public class OllamaClient {

    private static final String OLLAMA_URL = "http://localhost:11434/api/generate";
    private final OkHttpClient client = new OkHttpClient();

    public String ask(String prompt) throws IOException {
        // Construimos el JSON del prompt
        String json = "{ \"model\": \"mistral\", \"prompt\": \"" + prompt + "\" }";

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, json);

        Request request = new Request.Builder()
                .url(OLLAMA_URL)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Error en la llamada a Ollama: " + response);
            }
            return response.body() != null ? response.body().string() : "Sin respuesta del modelo";
        }
    }
}
