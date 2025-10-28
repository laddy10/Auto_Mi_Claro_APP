package utils;

import okhttp3.*;
import org.json.JSONObject;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class OllamaClient {

    private static final String OLLAMA_URL = "http://127.0.0.1:11434/api/generate";
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(40, TimeUnit.SECONDS)
            .readTimeout(90, TimeUnit.SECONDS)
            .build();

    /**
     * Envía una pregunta o instrucción al modelo Ollama local.
     *
     * @param prompt Texto de entrada
     * @return Respuesta del modelo
     * @throws IOException si hay problema de conexión o respuesta
     */
    public String ask(String prompt) throws IOException {
        if (prompt == null || prompt.isEmpty()) {
            throw new IllegalArgumentException("El prompt no puede ser nulo o vacío");
        }

        String safePrompt = prompt
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "");

        String json = String.format(
                "{\"model\": \"mistral\", \"stream\": false, \"prompt\": \"%s\"}",
                safePrompt
        );

        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder().url(OLLAMA_URL).post(body).build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("❌ Error en la llamada a Ollama: " + response);
            }

            if (response.body() == null) {
                throw new IOException("❌ Respuesta vacía del modelo.");
            }

            String bodyString = response.body().string();

            // ✅ Intenta parsear el JSON, si no es JSON devuelve la cadena tal cual
            try {
                JSONObject jsonResponse = new JSONObject(bodyString);
                return jsonResponse.optString("response", bodyString);
            } catch (Exception e) {
                return bodyString;
            }
        }
    }

    // 🔹 Método de prueba local (opcional)
    public static void main(String[] args) {
        try {
            OllamaClient client = new OllamaClient();
            String respuesta = client.ask("Dime algo breve para probar conexión con Ollama.");
            System.out.println("🧠 Respuesta de Ollama:\n" + respuesta);
        } catch (IOException e) {
            System.err.println("🚫 Error al conectar con Ollama: " + e.getMessage());
        }
    }
}
