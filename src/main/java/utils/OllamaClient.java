package utils;

import okhttp3.*;
import org.json.JSONObject; // <-- Asegúrate de tener org.json en tu proyecto
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class OllamaClient {
    private static final String OLLAMA_URL = "http://127.0.0.1:11434/api/generate";

    private final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build();

    public String ask(String prompt) throws IOException {
        String safePrompt = prompt
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "");

        String json = "{"
                + "\"model\": \"mistral\","
                + "\"stream\": false,"
                + "\"prompt\": \"" + safePrompt + "\""
                + "}";

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

            if (response.body() == null) {
                throw new IOException("Respuesta vacía del modelo");
            }

            String bodyString = response.body().string();

            // ✅ Extraer solo el campo “response” del JSON
            try {
                JSONObject jsonResponse = new JSONObject(bodyString);
                return jsonResponse.optString("response", bodyString);
            } catch (Exception e) {
                return bodyString; // Si no es JSON, devolver tal cual
            }
        }
    }

    // 🔹 Prueba rápida local
    public static void main(String[] args) {
        try {
            OllamaClient client = new OllamaClient();
            String respuesta = client.ask("Hola, ¿puedes responder brevemente?");
            System.out.println("🧠 Respuesta de Ollama:\n" + respuesta);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
